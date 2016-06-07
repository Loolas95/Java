package com.company;

import com.company.Entity.Mob.Bonus;
import com.company.Entity.Mob.Monster;
import com.company.Entity.Mob.MultiPlayer;
import com.company.Entity.Mob.Player;
import com.company.Levels.FileLevel;
import com.company.Levels.Info;
import com.company.Levels.Level;
import com.company.Levels.RandLevel;
import com.company.Net.GameClient;
import com.company.Net.GameServer;
import com.company.Net.Packet00Login;
import javafx.stage.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Bomberman extends Canvas implements Runnable {
    public static int width= 1280;
    public static int height=704;
    public static String title="Bomberman";
    private boolean running=false;
    public Keyboard key;
    public Level level;
    public Player player;
    private Monster monster;
    private boolean updated=false;
    public static Bomberman game;

    public JFrame frame;
    private Display display;
    private BufferedImage img=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
    private int[] pxl=((DataBufferInt)img.getRaster().getDataBuffer()).getData();

    public GameClient client;
    public GameServer server;
    public Window window;
    private Thread thread;
    private String name;
    public Bomberman(String name){
        game=this;
        Dimension size=new Dimension(width,height);
        setPreferredSize(size);
        display=new Display(width,height);
        this.name=name;
        frame=new JFrame();
        frame.setResizable(false);
        frame.setTitle(title);
        frame.add(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        key=new Keyboard();
        window=new Window(this);

        // level=new RandLevel(40,22);
        level=new FileLevel("bomberman\\src\\com\\company\\Levels\\level1.txt");
        //Info playerinfo=new Info(1,1);
        //Info monsterinfo=new Info(30,17);
        player=new MultiPlayer(32,32,key,level,JOptionPane.showInputDialog(this,"podaj imie"),null,-1);
        level.add(player);
        for(int i=0;i<2;i++) {
            level.add(new Monster(10, 5, level));
            // level.add(new Monster(30, 17, level));
        }
        for(int i=0;i<7;i++) {
            level.add(new Bonus(level));
        }
        this.start();

        addKeyListener(key);

        Packet00Login login=new Packet00Login(player.getUsername());




        // monster=new Monster(monsterinfo.x(),monsterinfo.y());
        // monster.init(level);


        if(server!=null){
            server.addConnection((MultiPlayer)player,login);
        }
        login.writeData(client);
        updated=false;

        //client.sendData("ping".getBytes());
    }

    public synchronized void start()
    {
        running=true;
        thread=new Thread(this,"Bomberman");
        thread.start();
        if(JOptionPane.showConfirmDialog(this,"Uruchomic serwer?")==0){
            try {
                server=new GameServer(this);
            } catch (SocketException e) {
                e.printStackTrace();
            }
            server.start();
       }

        try {
            client=new GameClient(this,"localhost");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        client.start();
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

        if(level.players.size()==0){
            if(!updated) {
                level.saveScore(name, player.getScore());
                updated=true;
            }
        }
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
        if(level.players.size()!=0) graph.drawString("Life: "+level.players.get(0).getLife()+" Monsters: "+level.monsters.size()+" Score: "+level.players.get(0).getScore(),40,20);
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
