package com.company.Levels;

import com.company.Display;
import com.company.Entity.Entity;
import com.company.Entity.Mob.*;
import com.company.Levels.Tiles.Tile;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;

import static java.sql.DriverManager.getConnection;

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
    public List<Bonus> bonuses=new ArrayList<Bonus>();
    public Level(int width, int height){
        this.width=width;
        this.height=height;
        tilesint =new int[width*height];
        createLevel();
    }
    public Level(String path){

        loadLevel(path);
    }
    public Level(){

    }

    protected void loadLevel(String path) {

    }

    protected void createLevel() {

    }
    public void tick(Display display){

        for(int i=0;i<monsters.size();i++) {
            monsters.get(i).tick(display);
        }
        for(int i=0;i<monsters.size();i++) {
            if(attack(monsters.get(i).x,monsters.get(i).y)){
                monsters.remove(i);
            }
        }
        for(int i=0;i<bonuses.size();i++) {
            if(bonus(bonuses.get(i).x,bonuses.get(i).y)){
                bonuses.remove(i);
            }
        }
        for(int i=0;i<players.size();i++){
            players.get(i).tick(display);
        }
        for(int i=0;i<bombs.size();i++){
            bombs.get(i).tick();
            //System.out.println(bombs.get(i).x+" "+bombs.get(i).y);
        }
        int destroyedtiles=0;
        for(int i=0;i<bombs.size();i++){
            if(bombs.get(i).boom){
                if((destroyedtiles=explosion(bombs.get(i).x,bombs.get(i).y))!=0){
                    players.get(0).addScore(destroyedtiles);
                }
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
        for (int i = 0; i < bonuses.size(); i++) {
            bonuses.get(i).render(display);
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
    public void add(Bonus b){

        bonuses.add(b);
    }
    public int explosion(int xb, int yb) {
        int flag=0;
    //System.out.println(xb+" "+yb);
        xb=xb+16;
        yb=yb+16;

        for(int i=0;i<2;i++) {

            if (getTile((int) ((xb - 40+i*80) / 32), (int) ((yb) / 32)).breakable()) {
                tiles[((int) ((xb - 40+i*80) / 32)) + ((int) ((yb) / 32)) * width] = 0;
                flag++;
            }
        }
        for(int i=0;i<2;i++) {

            if(getTile((int)((xb) / 32),(int)((yb-40+i*80) / 32)).breakable()) {
                tiles[((int) ((xb) / 32))+ ((int) ((yb-40+i*80) / 32))*width]=0;
                flag++;
            }
        }
         //int x=(int)((xb+40)/32);
            //int y=(int)(yb/32)*width;
           // System.out.println("dziala"+" "+x+" "+y);

        return flag;
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
        if(flag){
            players.get(0).addScore(-5);
        }

        return flag;



    }
    public boolean bonus(int xm, int ym) {
        boolean flag=false;
        for (int i = 0; i < players.size(); i++) {
            for(int c=0;c<4;c++) {
                if (!flag && (xm+c%2*22+5 > players.get(i).x+12 && xm+c%2*22-5 < players.get(i).x + 20) && (ym+c/2*22+5 > players.get(i).y+12 && ym+c/2*22-5 < players.get(i).y + 20)) {
                    players.get(i).setAccelerate();
                    flag = true;
                }
            }
        }
        if(flag){
            players.get(0).addScore(10);
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


    public void removePlayer(String username) {
        int index=0;
        for(Player p:players){
            if(p instanceof MultiPlayer && ((MultiPlayer)p).getUsername().equals(username)){
                break;
            }
            index++;
        }
        this.players.remove(index);
    }

    public void saveScore(String name, int score) {
        try {
            Connection myConn = getConnection("jdbc:mysql://localhost:3306/1project?useSSL=false", "root", "admin");
            String sql = " UPDATE students SET score=? WHERE login=?";
            PreparedStatement myStatement =  myConn.prepareStatement(sql);
            myStatement.setInt(1,score);
            myStatement.setString(2,name);
            myStatement.executeUpdate();
            } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public int getWidth(){return width;}
    public int getHeight(){return height;}
    public int getTileInt(int x,int y){return tiles[x+y*width];}
}
