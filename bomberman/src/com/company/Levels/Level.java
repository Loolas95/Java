package com.company.Levels;

import com.company.Display;
import com.company.Entity.Entity;
import com.company.Entity.Mob.Bomb;
import com.company.Entity.Mob.Monster;
import com.company.Entity.Mob.Player;
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
    public List<Entity> entities=new ArrayList<Entity>();
    public List<Player> players=new ArrayList<Player>();
    public List<Monster> monsters=new ArrayList<Monster>();
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
    public void tick(Display display){

        for(int i=0;i<monsters.size();i++){
            monsters.get(i).tick(display);
            if(attack(monsters.get(i).x,monsters.get(i).y)){
                monsters.remove(i);
            }
        }
        for(int i=0;i<players.size();i++){
            players.get(i).tick(display);
        }
        for(int i=0;i<bombs.size();i++){
            bombs.get(i).tick();
            //System.out.println(bombs.get(i).x+" "+bombs.get(i).y);
        }
        for(int i=0;i<bombs.size();i++){
            if(bombs.get(i).boom){
                explosion(bombs.get(i).x,bombs.get(i).y);
            }
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

        for (int i = 0; i < monsters.size(); i++) {
            monsters.get(i).render(display);
        }
        for (int i = 0; i < bombs.size(); i++) {
            bombs.get(i).render(display);
        }
        for (int i = 0; i < players.size(); i++) {
            players.get(i).render(display);
        }
    }
    public void add(Bomb b){

        bombs.add(b);
    }
    public void add(Monster m){

        monsters.add(m);
    }public void add(Player p){

        players.add(p);
    }
    public void explosion(int xb, int yb) {

    //System.out.println(xb+" "+yb);
        xb=xb+16;
        yb=yb+16;

        for(int i=0;i<2;i++) {

            if (getTile((int) ((xb - 40+i*80) / 32), (int) ((yb) / 32)).breakable()) {
                tiles[((int) ((xb - 40+i*80) / 32)) + ((int) ((yb) / 32)) * width] = 0;
            }
        }
        for(int i=0;i<2;i++) {

            if(getTile((int)((xb) / 32),(int)((yb-40+i*80) / 32)).breakable()) {
                tiles[((int) ((xb) / 32))+ ((int) ((yb-40+i*80) / 32))*width]=0;
            }
        }
         //int x=(int)((xb+40)/32);
            //int y=(int)(yb/32)*width;
           // System.out.println("dziala"+" "+x+" "+y);


    }
    public boolean attack(int xm, int ym) {
        boolean flag=false;
        for (int i = 0; i < players.size(); i++) {
            for(int c=0;c<4;c++) {
                if (!flag && (xm+c%2*22+5 > players.get(i).x+12 && xm+c%2*22-5 < players.get(i).x + 20) && (ym+c/2*22+5 > players.get(i).y+12 && ym+c/2*22-5 < players.get(i).y + 20)) {
                    players.get(i).minuslife();
                    flag = true;
                }
            }
        }
        return flag;



    }

    public Tile getTile(int x, int y){
        if(x<0 || y<0 || x>=width || y>=height) return Tile.bump;
        if(tiles[x+y*width]==0) return Tile.floor;
        if(tiles[x+y*width]==1) return Tile.bump;
        if(tiles[x+y*width]==2) return Tile.bound;
        return Tile.bump;

    }


}
