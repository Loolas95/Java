package com.company.Levels.Tiles;

import com.company.Display;
import com.company.Sprite;

/**
 * Created by Karol on 2016-04-04.
 */
public class BoundTile extends Tile{
    public BoundTile(Sprite sprite){
        super(sprite);
    }
    public void render(int x, int y, Display display){
        display.tilerender(x*32,y*32,this);
    }
    public boolean obstacle(){
        return true;
    }
    public boolean breakable(){
        return false;
    }
}
