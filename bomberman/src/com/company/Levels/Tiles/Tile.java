package com.company.Levels.Tiles;

import com.company.Display;
import com.company.Sprite;

/**
 * Created by Karol on 2016-03-20.
 */
public class Tile {
    public int x,y;
    public Sprite sprite;

    public static Tile floor=new FloorTile(Sprite.floor);
    public static Tile voidTile=new VoidTile(Sprite.voidSprite);
    public static Tile bump=new BumpTile(Sprite.bump);
    public static Tile bound=new BoundTile(Sprite.bound);

    public Tile(Sprite sprite){
        this.sprite=sprite;
    }

    public void render(int x, int y, Display display){

    }
    public boolean solid(){
        return false;
    }
    public boolean breakable(){return false;}
}
