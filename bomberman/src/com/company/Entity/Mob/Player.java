package com.company.Entity.Mob;

import com.company.Display;
import com.company.Keyboard;
import com.company.Sprite;


/**
 * Created by Karol on 2016-03-31.
 */
public class Player extends Mob{
    private Keyboard input;
    private Sprite sprite;
    private int anim=0;
    private boolean moving;

    public Player(Keyboard input){
        this.input=input;
        sprite=Sprite.player1_down;
    }
    public Player(int x,int y,Keyboard input){
        this.x=x;
        this.y=y;
        this.input=input;
        sprite=Sprite.player1_down;
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
        if(anim<2000)anim++;
        else anim=0;
        if(xa!=0||ya!=0) {
            move(xa,ya,display);
            moving=true;
        }else {
            moving = false;
        }
        clear();
    }
    private void clear(){
        for(int i=0;i<level.bombs.size();i++) {
            Bomb b1 = level.bombs.get(i);
            if(b1.isRemoved()){
                level.bombs.remove(i);

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
        display.playerrender(x,y,sprite);
   }

}
