package com.company.Levels;

/**
 * Created by Karol on 2016-04-03.
 */
public class Info {
    private int x,y;
    private final int tsize=32;

    public Info(int x, int y){
        this.x=x*tsize;
        this.y=y*tsize;
    }

    public int x(){
        return x;
    }
    public int y(){
        return y;
    }
    public int[] xy(){
        int [] t=new int[2];
        t[0]=x;
        t[1]=y;
        return t;
    }
}
