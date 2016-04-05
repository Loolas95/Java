package com.company.Levels;

import com.company.Display;
import com.company.Levels.Tiles.FloorTile;
import com.company.Levels.Tiles.Tile;

/**
 * Created by Karol on 2016-03-20.
 */
public class Level {
    protected int width, height;
    protected int[] tiles;

    public Level(int width, int height){
        this.width=width;
        this.height=height;
        tiles=new int[width*height];
        createLevel();
    }
    public Level(String path){
        loadLevel(path);
    }

    private void loadLevel(String path) {

    }

    protected void createLevel() {

    }
    public void tick(){

    }
    private void time(){

    }
    public void render(Display display){

        /*int x0=xs>>5;
        int x1=(xs+display.width)>>5;
        int y0=ys>>5;
        int y1=(ys+display.height)>>5;*/


        for(int y=0;y<height;y++)
            for(int x=0;x<width;x++){
                getTile(x,y).render(x,y,display);
            }

    }
    public Tile getTile(int x, int y){
        if(x<0 || y<0 || x>=width || y>=height) return Tile.bump;
        if(tiles[x+y*width]==0) return Tile.floor;
        return Tile.bump;

    }
}
