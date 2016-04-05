package com.company;

/**
 * Created by Karol on 2016-03-20.
 */
public class Sprite {
    public final int fsize;
    private int x,y;
    public int[] pxl;
    private TilesSheet sheet;

    public static Sprite floor=new Sprite(32,0,0, TilesSheet.tilesSheet);
    public static Sprite bump=new Sprite(32,1,0,TilesSheet.tilesSheet);
    public static Sprite voidSprite=new Sprite(32,0x1B87E0);
    private int colour;

    public Sprite(int size, int x, int y, TilesSheet sheet){
        fsize=size;
        pxl=new int[size*size];
        this.x=x*size;
        this.y=y*size;
        this.sheet=sheet;
        load();
    }

    public Sprite(int size, int colour) {
        fsize = size;
        pxl = new int[fsize * fsize];
        setColour(colour);
    }
    private void load(){
        for(int y=0;y<fsize;y++)
            for (int x=0;x<fsize;x++){
                pxl[x+y*fsize]=sheet.pxl[(x+this.x)+(y+this.y)*sheet.fsize];
        }
    }

    public void setColour(int colour) {
        for(int i =0;i<fsize;i++)
            pxl[i]=colour;

    }
}
