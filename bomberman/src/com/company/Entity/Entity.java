package com.company.Entity;

import com.company.Display;
import com.company.Levels.Level;

import java.util.Random;

/**
 * Created by Karol on 2016-03-31.
 */
public abstract class Entity {
    public int x,y;
    public boolean removed=false;
    protected Level level;
    protected final Random randomLevel = new Random();

    public void tick(){

    }
    public void mainrender(Display display){

    }

}
