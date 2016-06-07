package com.company.Levels;

import java.util.Random;

/**
 * Created by Karol on 2016-03-20.
 */
public class RandLevel extends Level {

    private static final Random rand=new Random();
    public RandLevel(int width, int height){
        super(width,height);
    }

    protected void createLevel(){
        for(int y=0;y<height;y++) {
            for (int x = 0; x < width; x++) {
                tilesint[x+y*width]=rand.nextInt(2);
            }
        }
    }
}
