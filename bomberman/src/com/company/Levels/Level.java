package com.company.Levels;

import com.company.Display;
import com.company.Entity.Entity;
import com.company.Entity.Mob.Bomb;
import com.company.Levels.Tiles.Tile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karol on 2016-03-20.
 */
public class Level {
    protected int width, height;
    protected int[] tilesint;
    protected int[] tiles;

    public List<Bomb> bombs=new ArrayList<Bomb>();

    public Level(int width, int height){
        this.width=width;
        this.height=height;
        tilesint =new int[width*height];
        createLevel();
    }
    public Level(String path){

        loadLevel(path);
    }

    protected void loadLevel(String path) {

    }

    protected void createLevel() {

    }
    public void tick(){
        for(int i=0;i<bombs.size();i++){
            bombs.get(i).tick();
        }

    }
    private void time(){

    }
    public void render(Display display) {

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                getTile(x, y).render(x, y, display);

            }

        }
        for (int i = 0; i < bombs.size(); i++) {
            bombs.get(i).render(display);
        }
    }
    public void add(Bomb b){

        bombs.add(b);
    }
    public void explosion(int xb, int yb) {
        if(getTile((xb+33),yb).breakable()){
            // tiles[(int)((xb+32)/32)+(int)(yb/32)*width]=0;
            System.out.println("dziala");
        }

    }

    public Tile getTile(int x, int y){
        if(x<0 || y<0 || x>=width || y>=height) return Tile.bump;
        if(tiles[x+y*width]==0) return Tile.floor;
        if(tiles[x+y*width]==1) return Tile.bump;
        if(tiles[x+y*width]==2) return Tile.bound;
        return Tile.bump;

    }


}
