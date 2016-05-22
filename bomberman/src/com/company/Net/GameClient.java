package com.company.Net;

import com.company.Bomberman;
import com.company.Entity.Mob.MultiPlayer;

import java.io.IOException;
import java.net.*;

/**
 * Created by Karol on 2016-05-15.
 */
public class GameClient extends Thread {
    private InetAddress ipadress;
    private DatagramSocket socket;
    private Bomberman game;

    public GameClient(Bomberman game, String ipadress) throws SocketException, UnknownHostException {
        this.game=game;
        try{
            this.socket=new DatagramSocket();
            this.ipadress=InetAddress.getByName(ipadress);
        }catch (SocketException e){
            e.printStackTrace();
        }catch (UnknownHostException e1){
            e1.printStackTrace();
        }
    }

    private void parsePacket(byte[] data, InetAddress address, int port) {
        String msg=new String(data).trim();
        Packet.Ptypes type=Packet.lookupPacket(msg.substring(0,2));
        Packet packet=null;
        switch (type){
            case INVALID:
                break;
            case LOGIN:
                packet=new Packet00Login(data);
                System.out.println("["+address.getHostAddress()+":"+port+"]"+((Packet00Login)packet).getUsername()+" dolaczyl do gry... ");
                MultiPlayer player=new MultiPlayer(1,1,game.level,((Packet00Login)packet).getUsername(),address,port);
                game.level.add(player);
                break;
            case DISCONNECT:
                break;
            default:
        }
    }
    public void run(){
        while(true){
            byte[] data=new byte[1024];
            DatagramPacket packet=new DatagramPacket(data,data.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //System.out.println("serwer "+new String(packet.getData()));
            this.parsePacket(packet.getData(),packet.getAddress(),packet.getPort());
        }
    }
    public void sendData(byte[] data){
        DatagramPacket packet=new DatagramPacket(data,data.length,ipadress,5555);
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
