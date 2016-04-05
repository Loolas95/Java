package com.company;

import com.company.Levels.Tiles.Tile;

import java.util.Random;

/**
 * Created by Karol on 2016-03-15.
 */
public class Display {
    public int width,height;
    public int[] pxl;

    public int[] tiles=new int[32*32];

    private Random rand=new Random();

    public Display(int width, int height){
        this.width=width;
        this.height=height;
        pxl=new int[width*height];
        for (int i=0;i<32*32;i++)
        {
            tiles[i]=rand.nextInt(0xffffff);
        }
    }
    public void clear(){
        for(int i =0; i<pxl.length;i++)
            pxl[i]=0;
    }

    public void render(){
        for(int y=0;y<height;y++){
            for(int x=0;x<width;x++){
                pxl[x+y*width]= Sprite.floor.pxl[(x&31)+(y&31)* Sprite.floor.fsize];
            }
        }
    }


    public void tilerender(int a, int b, Tile tile){
        for(int y=0; y<tile.sprite.fsize; y++){
            int yb=y+b;
            for(int x = 0; x<tile.sprite.fsize; x++){
                int xa=x+a;
                if ((xa < 0) || (xa >= width) || (yb < 0) || (yb >= height)) break;
                pxl[xa+yb*width]=tile.sprite.pxl[x+y*tile.sprite.fsize];
            }
        }
    }
}