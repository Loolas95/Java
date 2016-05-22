package com.company.Entity.Mob;

import com.company.Display;
import com.company.Entity.Entity;
import com.company.Sprite;

/**
 * Created by Karol on 2016-04-03.
 */
public class Bomb extends Entity {

    protected final int xb,yb;
    protected Sprite sprite;
    private long timer;
    public boolean expl=false,boom=false;


    public Bomb(int x, int y){
        xb=x;
        yb=y;
        this.x=x;
        this.y=y;
        sprite=Sprite.bomb1;
        timer=System.currentTimeMillis();

       /* Timer timer=new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                remove();

            }
        }, 5*1000);*/

    }

    public void tick(){
        bombsprite();
       /* if(expl) {
            level.explosion(xb,yb);
        }*/

    }
    public void render(Display display){
        if(expl){
            int xe=x-32;
            int ye=y-32;
            display.spriterender(xe,ye,sprite);
        }else {
            display.spriterender(x, y, sprite);
        }
    }
    public void bombsprite(){
        long actualtime=System.currentTimeMillis();
        if(actualtime-timer>1000) sprite=Sprite.bomb2;
        if(actualtime-timer>2000) sprite=Sprite.bomb3;
        if(actualtime-timer>3000) {

            sprite=Sprite.expl1;
            expl=true;
        }

        if(actualtime-timer>3100) sprite=Sprite.expl2;
        if(actualtime-timer>3200) sprite=Sprite.expl3;
        if(actualtime-timer>3300) sprite=Sprite.expl4;
        if(actualtime-timer>3400) sprite=Sprite.expl5;
        if(actualtime-timer>3500) {
            boom=true;
            remove();
        }


    }

}
