package com.company.Net;

/**
 * Created by Karol on 2016-05-16.
 */
public class Packet02Move extends Packet {
    private String username;
    private boolean moving;
    private int dir;
    private int anim;
    private int x,y;



    public Packet02Move(byte[] data){
        super(02);
        String[] dataarray=readData(data).split(",");
        this.username=dataarray[0];
        this.x=Integer.parseInt(dataarray[1]);
        this.y=Integer.parseInt(dataarray[2]);
        this.moving=Integer.parseInt(dataarray[3])==1;
        this.dir=Integer.parseInt(dataarray[4]);
        this.anim=Integer.parseInt(dataarray[5]);

    }
    public Packet02Move(String username, int x, int y,boolean moving ,int dir, int anim){
        super(02);
        this.username=username;
        this.x=x;
        this.y=y;
        this.moving=moving;
        this.dir=dir;
        this.anim=anim;
    }


    @Override
    public void writeData(GameClient client) {
        client.sendData(getData());
    }

    @Override
    public void writeData(GameServer server) {
        server.sendDataToAllClients(getData());
    }

    @Override
    public byte[] getData() {
        return ("02"+this.username+","+this.x+","+this.y+","+(moving ? 1 : 0)+","+this.dir+","+this.anim).getBytes();
    }
    public String getUsername(){
        return username;
    }
    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }
    public boolean isMoving() {
        return moving;
    }
    public int getDir() {
        return dir;
    }
    public int getAnim() {
        return anim;
    }
}
