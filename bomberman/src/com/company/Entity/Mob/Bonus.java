package com.company.Entity.Mob;

import com.company.Display;
import com.company.Entity.Entity;
import com.company.Levels.Level;
import com.company.Levels.Tiles.Tile;
import com.company.Sprite;

import java.util.Random;

/**
 * Created by Karol on 2016-05-22.
 */
public class Bonus extends Entity {
    private Level level;
    protected Sprite sprite;



    public Bonus(Level level){
        init(level);
        Random rand = new Random();
        int a,b,result;
        do{
            a=rand.nextInt((level.getWidth()-1));
            b=rand.nextInt((level.getHeight()-1));
            result=level.getTileInt(a,b);
        }while(result!=0);
        this.x=a*32;
        this.y=b*32;
        sprite=Sprite.bonus;
        level.add(this);

    }

    public void tick(){

    }
    public void render(Display display){
            display.spriterender(x, y, sprite);
    }


}
