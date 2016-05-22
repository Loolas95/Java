package com.company.Entity.Mob;

import com.company.Keyboard;
import com.company.Levels.Level;

import java.net.InetAddress;

/**
 * Created by Karol on 2016-05-16.
 */
public class MultiPlayer extends Player {
    public InetAddress ipadress;
    public int port;
    public MultiPlayer(int x, int y, Keyboard input, Level level, String username, InetAddress ipadress, int port) {
        super(x, y, input, level, username);
        this.ipadress=ipadress;
        this.port=port;
    }
    public MultiPlayer(int x, int y, Level level, String username, InetAddress ipadress, int port) {
        super(x, y, null, level, username);
        this.ipadress=ipadress;
        this.port=port;
    }
    @Override
    public void tick(){
        super.tick();
    }
}
