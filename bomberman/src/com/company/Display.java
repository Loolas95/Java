package com.company;

import com.company.Levels.Tiles.Tile;

import javax.swing.*;
import java.awt.*;
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
    public void spriterender(int a, int b, Sprite sprite){
        for(int y=0; y<sprite.fsize; y++){
            int yb=y+b;
            for(int x = 0; x<sprite.fsize; x++){
                int xa=x+a;
                if ((xa < 0) || (xa >= width) || (yb < 0) || (yb >= height)) break;
                int colour=sprite.pxl[x+y*sprite.fsize];
                if(colour!=0xffff00f0) {
                    pxl[xa + yb * width] = sprite.pxl[x + y * sprite.fsize];
                }
            }
        }
    }
    public void playerrender(int a, int b, Sprite sprite){
        for(int y=0; y<32; y++){
            int yb=y+b;
            for(int x = 0; x<32; x++){
                int xa=x+a;
                if ((xa < -32) || (xa >= width) || (yb < 0) || (yb >= height)) break;
                if(xa<0) xa=0;
                int colour=sprite.pxl[x+y*32];
                if(colour!=0xfff2bd00){
                    pxl[xa+yb*width]=colour;
                }
            }
        }

    }
    public void monsterrender(int a, int b, Sprite sprite){
        for(int y=0; y<32; y++){
            int yb=y+b;
            for(int x = 0; x<32; x++){
                int xa=x+a;
                if ((xa < -32) || (xa >= width) || (yb < 0) || (yb >= height)) break;
                if(xa<0) xa=0;
                int colour=sprite.pxl[x+y*32];
                if(colour!=0xfff2bd00){
                    if(colour==0xff000000) colour=0xff0d2485;
                    pxl[xa+yb*width]=colour;
                }
            }
        }
    }
    public void endGame(){
        JFrame frame3=new JFrame();
        frame3.setVisible(true);
        frame3.setSize(300,400);
        frame3.setLocationRelativeTo(null);
        frame3.setBackground(Color.lightGray);
        frame3.setLayout(null);
        frame3.setBackground(Color.gray);
    }
}