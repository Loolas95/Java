package com.company.Entity.Mob;

import com.company.Display;
import com.company.Keyboard;
import com.company.Levels.Level;
import com.company.Levels.SaveLevel;
import com.company.Sprite;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Karol on 2016-03-31.
 */
public class Player extends Mob {
    private Keyboard input;
    private Sprite sprite;
    private int anim=0;
    private int life=3;
    public boolean ismonster=false;
    private boolean moving;
    private String username;
    private int accelerate=1;
    private long timer=0;
    private int score=0;

    public Player(Keyboard input){
        this.input=input;
        sprite=Sprite.player1_down;
    }
    public Player(int x,int y,Keyboard input,Level level, String username){
        init(level);
        this.x=x*32;
        this.y=y*32;
        this.input=input;
        sprite=Sprite.player1_down;
        this.username=username;
    }
    public void tick(Display display){
        int xa=0,ya=0;

        if(input!=null) {
            if (input.up) ya-=accelerate;
            if (input.down) ya+=accelerate;
            if (input.left) xa-=accelerate;
            if (input.right) xa+=accelerate;
            if (input.bomb) {
                putbomb(x, y);

            }
            if (input.save) {
                new SaveLevel(this.level);
                //level.saveScore();
            }
        }
        if(anim<2000)anim++;
        else anim=0;
        if(xa!=0||ya!=0) {
            move(xa,ya,display, this.ismonster);
            moving=true;
        }else {
            moving = false;
        }
        bombexpl();
        clear();
        if(life<=0)death();
        slow();
      }

    private void slow() {
        if(System.currentTimeMillis()-timer>5000)
            accelerate=1;
    }

    private void clear(){
        for(int i=0;i<level.bombs.size();i++) {
            Bomb b1 = level.bombs.get(i);
            if(b1.isRemoved()){
                level.bombs.remove(i);

            }
        }
    }
    private void death() {
        for (int i = 0; i < level.players.size(); i++) {
            if (level.players.get(i).getLife() <= 0) {
                level.players.remove(i);

            }
        }
    }
    public int getLife(){
        return life;
    }
    public String getUsername(){
        return username;
    }
    public int getScore(){ return score;}
    public void addScore(int counter){score+=5*counter;}
    public void minuslife(){ life--; }
    public void setAccelerate(){
        timer=0;
        accelerate=2;
        timer=System.currentTimeMillis();
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
        if(life<0){}
        display.playerrender(x,y,sprite);
   }

}
