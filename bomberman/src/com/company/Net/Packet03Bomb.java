package com.company.Net;

/**
 * Created by Karol on 2016-05-16.
 */
public class Packet03Bomb extends Packet {
    private String username;
    private int x,y;



    public Packet03Bomb(byte[] data){
        super(03);
        String[] dataarray=readData(data).split(",");
        this.username=dataarray[0];
        this.x=Integer.parseInt(dataarray[1]);
        this.y=Integer.parseInt(dataarray[2]);

    }
    public Packet03Bomb(String username, int x, int y){
        super(02);
        this.username=username;
        this.x=x;
        this.y=y;
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
        return ("03"+this.username+","+this.x+","+this.y).getBytes();
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

}