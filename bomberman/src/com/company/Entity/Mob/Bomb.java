package com.company.Entity.Mob;

import com.company.Display;
import com.company.Entity.Entity;
import com.company.Levels.Tiles.Tile;
import com.company.Sprite;

import javax.swing.*;
import java.util.TimerTask;
import java.util.Timer;

/**
 * Created by Karol on 2016-04-03.
 */
public class Bomb extends Entity {

    protected final int xb,yb;
    protected Sprite sprite;


    public Bomb(int x, int y){
        xb=x;
        yb=y;
        this.x=x;
        this.y=y;
        sprite=Sprite.bomb;
        Timer timer=new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                remove();

            }
        }, 5*1000);

    }
    public void tick(){
        //level.explosion(xb,yb);


    }
    public void render(Display display){
        display.tilerender(x,y,sprite);
    }
}
