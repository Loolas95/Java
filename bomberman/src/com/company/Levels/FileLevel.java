package com.company.Levels;

import com.company.Entity.Mob.Monster;
import com.company.Levels.Tiles.Tile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Karol on 2016-04-01.
 */
public class FileLevel extends Level{

    public FileLevel(String path){
        super(path);

    }
    protected void loadLevel(String path){
        try (BufferedReader br = new BufferedReader(new FileReader(path)))
        {

            String sCurrentLine;
            int w=Integer.parseInt(br.readLine());
            width=w;
            int h=Integer.parseInt(br.readLine());
            height=h;
            tiles =new int[w*h];
            int i=0;

            while ((sCurrentLine = br.readLine()) != null) {
                if(sCurrentLine.isEmpty())continue;
                String[] values=sCurrentLine.trim().split(" ");
                for (String string :values) {
                    if(!string.isEmpty()){
                        int id=Integer.parseInt(string);
                        tiles[i++]=id;
                    }
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Nie mozna odczytac lvl z pliku");
        }


    }
    protected void createLevel(){
        /*for(int i = 0; i< lvlval.length; i++){
            if(lvlval[i]==0) tiles[i]=Tile.bump;
            if(lvlval[i]==1) tiles[i]=Tile.floor;*/

        }
    }

