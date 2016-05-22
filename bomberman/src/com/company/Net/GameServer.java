package com.company.Net;

import com.company.Bomberman;
import com.company.Entity.Mob.MultiPlayer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karol on 2016-05-15.
 */
public class GameServer extends Thread{
    private DatagramSocket socket;
    private Bomberman game;
    private List<MultiPlayer> conplayers =new ArrayList<MultiPlayer>();

    public GameServer(Bomberman game) throws SocketException {
        this.game=game;
        try{
            this.socket=new DatagramSocket(5555);
        }catch (SocketException e) {
            e.printStackTrace();

        }
    }
    public void run(){
        while(true) {
            byte[] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data, data.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.parsePacket(packet.getData(),packet.getAddress(),packet.getPort());
           /* String msg = new String(packet.getData());
            System.out.println("client "+msg);
            if (msg.trim().equalsIgnoreCase("ping")) {
                sendData("pong".getBytes(),packet.getAddress(), packet.getPort());
            }*/
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
                System.out.println("["+address.getHostAddress()+":"+port+"]"+((Packet00Login)packet).getUsername()+" polaczony... ");
                MultiPlayer player=new MultiPlayer(1,1,game.level,((Packet00Login)packet).getUsername(),address,port);
                this.addConnection(player,(Packet00Login)packet);
                break;
            case DISCONNECT:
                break;
            default:
        }
    }

    public void addConnection(MultiPlayer player, Packet00Login packet) {
        boolean alreadyConnected=false;
        for(int i = 0; i<this.conplayers.size(); i++){
            if(this.conplayers.get(i).getUsername().equalsIgnoreCase(player.getUsername())) {
                if (conplayers.get(i).ipadress == null) {
                    conplayers.get(i).ipadress = player.ipadress;
                }
                if (conplayers.get(i).port == -1) {
                    conplayers.get(i).port = player.port;
                }
                alreadyConnected = true;
            }else{
                sendData(packet.getData(), conplayers.get(i).ipadress, conplayers.get(i).port);
            }
        }
        if(alreadyConnected){
            this.conplayers.add(player);
        }
    }

    public void sendData(byte[] data, InetAddress ipadress, int port){
        DatagramPacket packet=new DatagramPacket(data,data.length,ipadress,port);
        try {
           this.socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sendDataToAllClients(byte[] data){
        for(int i = 0; i< conplayers.size(); i++){
            sendData(data, conplayers.get(i).ipadress, conplayers.get(i).port);
        }
    }

}
