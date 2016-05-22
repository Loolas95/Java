package com.company.Entity.Mob;

import com.company.Display;
import com.company.Keyboard;
import com.company.Levels.Level;
import com.company.Levels.SaveLevel;
import com.company.Sprite;


/**
 * Created by Karol on 2016-03-31.
 */
public class Player extends Mob {
    private Keyboard input;
    private Sprite sprite;
    private int anim=0;
    private int life=10;
    public boolean ismonster=false;
    private boolean moving;
    private String username;

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
        if(input.up) ya--;
        if(input.down) ya++;
        if(input.left) xa--;
        if(input.right) xa++;
        if(input.bomb){
            putbomb(x,y);
        }
        if(input.save){
            new SaveLevel(this.level);
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
    public void minuslife(){
        life--;
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
