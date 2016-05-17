package com.company.Entity.Mob;


import com.company.Display;
import com.company.Keyboard;
import com.company.Levels.Level;
import com.company.Sprite;

import java.util.Random;

/**
 * Created by Karol on 2016-04-09.
 */
public class Monster extends Mob {
    private Sprite sprite;
    private int anim=0;
    private int counter=0;
    private int life=1;
    private int xa=0,ya=0;
    private boolean moving;
    public boolean ismonster;
    private Random rand=new Random();

    public Monster(){
        sprite=Sprite.player1_down;
    }
    public Monster(int x, int y,Level level){
        init(level);
        this.x=x*32;
        this.y=y*32;
        sprite=Sprite.player1_down;
        ismonster=true;
    }
    public void tick(Display display){

        counter++;
        if(counter%(rand.nextInt(40)+20)==0){
            xa= rand.nextInt(3)-1;
            ya= rand.nextInt(3)-1;
            if(rand.nextInt(5)==0){
                xa=0;
                ya=0;
            }
        }
        if(ya<0) ya=-1;
        if(ya>0) ya=1;
        if(xa<0) xa=-1;
        if(xa>0) xa=1;
        if(anim<2000) {

            anim++;
        }
        else anim=0;
        if(xa!=0||ya!=0) {
            monstermove(xa,ya,display,this.ismonster);
            moving=true;
        }else {
            moving = false;
        }
        bombexpl();
        if(life<=0)death();
    }
    public int lives(){
        return life;
    }
    private void death(){
        for(int i=0;i<level.monsters.size();i++) {
            if(level.monsters.get(i).lives()<=0){
                level.monsters.remove(i);

            }
        }
    }
    public void bombexpl(){
        for(int i=0;i<level.bombs.size();i++) {
            if (level.bombs.get(i).boom) {
                if((x>=level.bombs.get(i).xb-45 && level.bombs.get(i).xb+45>=x)&&(y>=level.bombs.get(i).yb-10)&& y<level.bombs.get(i).yb+22){
                    life--;
                    System.out.println(life);
                }
                else if((x>=level.bombs.get(i).xb-10 && level.bombs.get(i).xb+22>=x)&&(y>=level.bombs.get(i).yb-45)&& y<level.bombs.get(i).yb+45){
                    life--;
                    System.out.println(life);
                }
            }
        }
    }
    public void render(Display display){
        if(dir==0){
            sprite= Sprite.player1_up;
            if(moving){
                if(anim%20>10) sprite=Sprite.player1_up1;
                else sprite=Sprite.player1_up2;
            }
        }
        if(dir==1){
            sprite= Sprite.player1_right;
            if(moving){
                if(anim%30>15) sprite=Sprite.player1_right1;
                else sprite=Sprite.player1_right2;
            }
        }
        if(dir==2){
            sprite= Sprite.player1_down;
            if(moving){
                if(anim%20>10) sprite=Sprite.player1_down1;
                else sprite=Sprite.player1_down2;
            }
        }
        if(dir==3){
            sprite= Sprite.player1_left;
            if(moving){
                if(anim%30>15) sprite=Sprite.player1_left1;
                else sprite=Sprite.player1_left2;
            }
        }
        display.monsterrender(x,y,sprite);
    }

}