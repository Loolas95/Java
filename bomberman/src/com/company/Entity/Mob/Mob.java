package com.company.Entity.Mob;

import com.company.Display;
import com.company.Entity.Entity;
import com.company.Sprite;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karol on 2016-03-31.
 */
public abstract class Mob extends Entity{
    protected Sprite sprite;
    protected int dir=-1;
    protected boolean moving=false;


    public void move(int xa, int ya,Display display, boolean ismonster) {
        if (xa != 0 && ya != 0) {
            move(xa, 0, display, ismonster);
            move(0, ya, display, ismonster);
            return;
        }
        if (xa > 0) dir = 1;
        if (xa < 0) dir = 3;
        if (ya > 0) dir = 2;
        if (ya < 0) dir = 0;
        if (!collision(xa, ya)) {
            if (((x + xa >= 0) && (x + xa <= display.width - 32) && (y + ya >= 0) && (y + ya <= display.height - 32))) {

                                x += xa;
                                y += ya;

            }
        }
    }
    public void monstermove(int xa, int ya,Display display, boolean ismonster) {
        if (xa != 0 && ya != 0) {
            monstermove(xa, 0, display, ismonster);
            monstermove(0, ya, display, ismonster);
            return;
        }
        if (xa > 0) dir = 1;
        if (xa < 0) dir = 3;
        if (ya > 0) dir = 2;
        if (ya < 0) dir = 0;
        if (!collision(xa, ya)) {
            if (((x + xa >= 0) && (x + xa <= display.width - 32) && (y + ya >= 0) && (y + ya <= display.height - 32))) {
                if (level.bombs.isEmpty()) {
                    x += xa;
                    y += ya;
                } else {
                    for (int i = 0; i < level.bombs.size(); i++) {
                        for(int c=0;c<4;c++) {
                            if (x+c%2*32 + xa <= level.bombs.get(i).xb + 32 && x+c%2*32 + xa >= level.bombs.get(i).xb && y+c/2*32 + ya <= level.bombs.get(i).yb + 32 && y+c/2*32 + ya >= level.bombs.get(i).yb) {
                                    x-=xa;
                                    y-=ya;
                                    return;
                            }
                            }
                        }
                        x += xa;
                        y += ya;
                        return;
                    }
                }
            }
        }
    public void tick(){

    }
    protected void putbomb(int x, int y){
        if(!isbomb(x,y)) {
            Bomb b = new Bomb(x,y);
            level.add(b);
        }

    }

    private boolean isbomb(int x, int y) {
        for(int i=-31;i<31;i++){
            for(int j=-31;j<31;j++){
                for(int k=0;k<level.bombs.size();k++){
                    if(x==level.bombs.get(k).xb+i){
                        if(y==level.bombs.get(k).yb+j){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean collision(int xa, int ya){
        boolean solid=false;
        for(int c=0;c<4;c++){
            int xt=((x+xa)+c%2*22+4)/32;
            int yt=((y+ya)+c/2*26+2)/32;
            if(level.getTile(xt,yt).obstacle()) solid=true;
        }

        return solid;
    }
    public void render(){

    }
}
