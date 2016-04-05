package com.company.Levels.Tiles;

import com.company.Display;
import com.company.Sprite;

/**
 * Created by Karol on 2016-03-21.
 */
public class VoidTile extends Tile {
    public VoidTile(Sprite sprite) {
        super(sprite);
    }
    public void render(int x, int y, Display display){
        display.tilerender(x*32,y*32,this);
    }
}
