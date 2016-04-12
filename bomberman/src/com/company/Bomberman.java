package com.company;

import com.company.Entity.Mob.Monster;
import com.company.Entity.Mob.Player;
import com.company.Levels.FileLevel;
import com.company.Levels.Level;
import com.company.Levels.Info;

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
    private Player player;
    private Monster monster;

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
       // level=new RandLevel(40,22);
        level=new FileLevel("C:\\Users\\Karol\\IdeaProjects\\bomberman\\src\\com\\company\\Levels\\level1.txt");
        Info playerinfo=new Info(1,1);
        //Info monsterinfo=new Info(30,17);
        player=new Player(playerinfo.x(),playerinfo.y(),key,level);
        level.add(player);
        for(int i=0;i<4;i++) {
            level.add(new Monster(10, 5, level));
            level.add(new Monster(30, 17, level));
        }
       // monster=new Monster(monsterinfo.x(),monsterinfo.y());
       // monster.init(level);
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
        //player.tick(display);
//        monster.tick(display);
        level.tick(display);

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
        //player.render(display);
        //monster.render(display);

        for(int i=0;i<pxl.length;i++)
            pxl[i]=display.pxl[i];
        Graphics graph=bs.getDrawGraphics();
        graph.drawImage(img,0,0,width,height,null);
        graph.setFont(new Font("Verdana", 0,20));
        if(level.players.size()!=0) graph.drawString("Life: "+level.players.get(0).getLife()+" Monsters: "+level.monsters.size(),40,20);
        graph.dispose();
        bs.show();
    }
    public void run(){
        requestFocus();
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
