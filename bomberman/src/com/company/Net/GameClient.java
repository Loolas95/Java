package com.company.Net;

import com.company.Bomberman;
import com.company.Entity.Mob.MultiPlayer;
import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;

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
    private void parsePacket(byte[] data, InetAddress address, int port) {
        String msg=new String(data).trim();
        Packet.Ptypes type=Packet.lookupPacket(msg.substring(0,2));
        Packet packet=null;
        switch (type){
            case INVALID:
                break;
            case LOGIN:
                packet = new Packet00Login(data);
                /*System.out.println("[" + address.getHostAddress() + ":" + port + "] "
                        + ((Packet00Login) packet).getUsername() + " wszedl do gry...");
                MultiPlayer player  = new MultiPlayer(64, 64 ,game.level, ((Packet00Login) packet).getUsername(), address, port);
                game.level.add(player);*/
                handleLogin((Packet00Login) packet, address, port);
                break;
            case DISCONNECT:
                packet = new Packet01Disconnect(data);
                System.out.println("[" + address.getHostAddress() + ":" + port + "] "
                        + ((Packet01Disconnect) packet).getUsername() + " opuscil gre...");
                game.level.removePlayer(((Packet01Disconnect)packet).getUsername());
                break;
            case MOVE:
                packet=new Packet02Move(data);
                handleMove(((Packet02Move)packet));
                break;
            case BOMB:
                packet=new Packet03Bomb(data);
                handleBomb(((Packet03Bomb)packet));
            default:
        }
    }

    private void handleBomb(Packet03Bomb packet) {
        this.game.level.bombMP(packet.getUsername(),packet.getX(),packet.getY());
    }

    private void handleMove(Packet02Move packet) {
        this.game.level.movePlayer(packet.getUsername(),packet.getX(),packet.getY(),packet.isMoving(),packet.getDir(),packet.getAnim());

    }

    public void sendData(byte[] data){
        DatagramPacket packet=new DatagramPacket(data,data.length,ipadress,5555);
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void handleLogin(Packet00Login packet, InetAddress address, int port) {
        System.out.println("[" + address.getHostAddress() + ":" + port + "] " + packet.getUsername()
                + " wszedl do gry...");
        MultiPlayer player = new MultiPlayer(32,32,game.level, packet.getUsername(), address, port);
        game.level.add(player);
    }

}
