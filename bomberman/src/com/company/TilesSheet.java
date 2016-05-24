package com.company;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by Karol on 2016-03-20.
 */
public class TilesSheet {
    private String path;
    public final int fsize;
    public int[]pxl;

    public static TilesSheet tilesSheet =new TilesSheet("C:\\Users\\Karol\\Desktop\\Java_lato_2015-2016_Karol_Rodak\\bomberman\\src\\com\\company\\Textures\\tiles8.png",512);

    public TilesSheet(String path, int size){
        this.path=path;
        this.fsize=size;
        pxl=new int[fsize*fsize];
        loadImage();
    }
    private void loadImage() {
       try{
           BufferedImage image= ImageIO.read(new FileInputStream(path));
           int w=image.getWidth();
           int h=image.getHeight();
           image.getRGB(0,0,w,h,pxl,0,w);
       }catch (IOException e){
           e.printStackTrace();
       }

    }
}
