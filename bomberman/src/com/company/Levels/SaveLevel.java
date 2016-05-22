package com.company.Levels;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 * Created by Karol on 2016-04-26.
 */
public class SaveLevel extends Level {
    private File outputFile;
    private BufferedWriter outwriter;
    public SaveLevel(Level level) {
        try {
            outputFile = new File("C:\\Users\\Karol\\IdeaProjects\\bomberman\\src\\com\\company\\Levels\\save.txt");
            outwriter = new BufferedWriter(new FileWriter(outputFile));
            outwriter.write(Integer.toString(level.width));
            outwriter.newLine();
            outwriter.write(Integer.toString(level.height));
            outwriter.newLine();
            for(int i=0;i<level.height;i++){
                for (int j=0;j<level.width;j++){
                    outwriter.write(Integer.toString(level.tiles[i*level.width+j])+" ");
                }
                outwriter.newLine();
            }
            outwriter.newLine();
            outwriter.write(Integer.toString(level.bombs.size()));
            outwriter.newLine();
            for(int i=0;i<level.bombs.size();i++){
                outwriter.write(Integer.toString(level.bombs.get(i).x)+" "+Integer.toString(level.bombs.get(i).y));
                outwriter.newLine();
            }
            outwriter.newLine();
            outwriter.write(Integer.toString(level.players.size()));
            outwriter.newLine();
            for(int i=0;i<level.players.size();i++){
                outwriter.write(Integer.toString(level.players.get(i).x)+" "+Integer.toString(level.players.get(i).y));
                outwriter.newLine();
            }
            outwriter.newLine();
            outwriter.write(Integer.toString(level.monsters.size()));
            outwriter.newLine();
            for(int i=0;i<level.monsters.size();i++){
                outwriter.write(Integer.toString(level.monsters.get(i).x)+" "+Integer.toString(level.monsters.get(i).y));
                outwriter.newLine();
            }


            outwriter.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
