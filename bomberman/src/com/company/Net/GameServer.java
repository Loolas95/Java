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
        String message = new String(data).trim();
        Packet.Ptypes type = Packet.lookupPacket(message.substring(0, 2));
        Packet packet = null;
        switch (type) {
            default:
            case INVALID:
                break;
            case LOGIN:
                packet = new Packet00Login(data);
                System.out.println("[" + address.getHostAddress() + ":" + port + "] "
                        + ((Packet00Login) packet).getUsername() + " wszedl do gry...");
                MultiPlayer player = new MultiPlayer(2,2,game.level,((Packet00Login) packet).getUsername(), address, port);
                this.addConnection(player, (Packet00Login) packet);
                break;
            case DISCONNECT:
                packet = new Packet01Disconnect(data);
                System.out.println("[" + address.getHostAddress() + ":" + port + "] "
                        +   ((Packet01Disconnect) packet).getUsername() + " opuszcza gre...");
                this.removeConnection((Packet01Disconnect) packet);
                break;
        }
    }

    public void removeConnection(Packet01Disconnect packet) {
        this.conplayers.remove(getindex(packet.getUsername()));
        packet.writeData(this);
    }
    public MultiPlayer getPlayer(String username){
        for(MultiPlayer p:this.conplayers){
            if(p.getUsername().equals(username))
                return p;
        }
        return null;
    }
    public int getindex(String username){
        int index=0;
        for(MultiPlayer p:this.conplayers){
            if(p.getUsername().equals(username)){
                break;
            }
            index++;

        }
        return index;
    }

    public void addConnection(MultiPlayer player, Packet00Login packet) {
        boolean alreadyConnected = false;
        for (MultiPlayer p : this.conplayers) {
            if (player.getUsername().equalsIgnoreCase(p.getUsername())) {
                if (p.ipadress == null) {
                    p.ipadress = player.ipadress;
                }
                if (p.port == -1) {
                    p.port = player.port;
                }
                alreadyConnected = true;
            } else {
                // relay to the current connected player that there is a new
                // player
                sendData(packet.getData(), p.ipadress, p.port);

                // relay to the new player that the currently connect player
                // exists
                packet = new Packet00Login(p.getUsername());
                sendData(packet.getData(), player.ipadress, player.port);
            }
        }
        if (!alreadyConnected) {
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
