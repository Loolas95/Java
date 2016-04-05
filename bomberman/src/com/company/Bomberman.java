package com.company;

import com.company.Levels.Level;
import com.company.Levels.RandLevel;
import javafx.stage.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Bomberman extends Canvas implements Runnable {
    public static int width= 1280;
    public static int height=704;
    public static String title="Bomberman";
    private boolean running=false;
    private Keyboard key;
    private Level level;

    private JFrame frame;
    private Display display;
    private BufferedImage img=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
    private int[] pxl=((DataBufferInt)img.getRaster().getDataBuffer()).getData();

    private Thread thread;
    public Bomberman(){
        Dimension size=new Dimension(width,height);
        setPreferredSize(size);
        display=new Display(width,height);

        frame=new JFrame();
        frame.setResizable(false);
        frame.setTitle(title);
        frame.add(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        key=new Keyboard();
        level=new RandLevel(40,22);
        addKeyListener(key);

        this.start();

    }

    public synchronized void start()
    {
        running=true;
        thread=new Thread(this,"Bomberman");
        thread.start();
    }
    public synchronized void stop()
    {
        running=false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void tick(){
        key.update();
        if(key.up);
        if(key.down);
        if(key.left);
        if(key.right);


    }
    public void mainrender(){
        BufferStrategy bs=getBufferStrategy();
        if(bs==null) {
            createBufferStrategy(3);
            return;
        }
        display.clear();
       // display.render();
        level.render(display);

        for(int i=0;i<pxl.length;i++)
            pxl[i]=display.pxl[i];
        Graphics graph=bs.getDrawGraphics();
        graph.drawImage(img,0,0,width,height,null);
        graph.dispose();
        bs.show();
    }
    public void run(){
        long prevTime=System.nanoTime();
        final double ns=1000000000.0/60.0;
        double delta=0;
        int frames=0;
        int ticks=0;
        long timer=System.currentTimeMillis();
        while(running){
            long now =System.nanoTime();
            delta+=(now-prevTime)/ns;
            prevTime=now;
            while (delta>=1){
                tick();
                ticks++;
                delta--;
            }
            mainrender();
            frames++;
            if(System.currentTimeMillis()-timer>1000){
                timer+=1000;
                frame.setTitle(title+", "+frames+"fps");
                ticks=0;
                frames=0;
            }
        }
        stop();
    }
    public  static void main(String[] args){
//        Menu menu=new Menu();
       //Bomberman game=new Bomberman();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Menu menu=new Menu();
            }
        });

    }
}
