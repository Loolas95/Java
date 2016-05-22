package com.company.Net;

/**
 * Created by Karol on 2016-05-16.
 */
public abstract class Packet {
    public static enum Ptypes{
        INVALID(-1),LOGIN(00), DISCONNECT(01);
        private int packetid;
        private Ptypes(int packetid){
            this.packetid=packetid;
        }

        public int getPacketid() {
            return packetid;
        }
    }
    public byte packetid;
    public Packet(int packetid){
        this.packetid=(byte) packetid;
    }
    public abstract void writeData(GameClient client);
    public abstract void writeData(GameServer server);
    public String readData(byte[] data){
        String msg=new String(data).trim();
        return msg.substring(2);
    }
    public abstract byte[] getData();
    public static Ptypes lookupPacket(int id){
        for(Ptypes p: Ptypes.values()){
            if(p.getPacketid()==id){
                return p;
            }
        }
        return Ptypes.INVALID;
    }
    public static Ptypes lookupPacket(String id) {
        try{
            return lookupPacket(Integer.parseInt(id));
        }catch (NumberFormatException e){
            return Ptypes.INVALID;
        }
    }
}
