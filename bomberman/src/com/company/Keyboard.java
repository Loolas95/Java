package com.company;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Karol on 2016-03-19.
 */
public class Keyboard implements KeyListener {

    private boolean[] keys=new boolean[120];
    public boolean up,down,left,right,bomb,save;

    public void update(){
        up=keys[KeyEvent.VK_UP];
        down=keys[KeyEvent.VK_DOWN];
        left=keys[KeyEvent.VK_LEFT];
        right=keys[KeyEvent.VK_RIGHT];
        save=keys[KeyEvent.VK_S];
        bomb=keys[KeyEvent.VK_B];
        for(int i=0;i<keys.length;i++){
            if (keys[i])
                System.out.println("key: "+i);
        }

    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()]=true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()]=false;
    }
}
