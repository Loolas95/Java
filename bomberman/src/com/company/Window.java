package com.company;

import com.company.Net.Packet01Disconnect;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Created by Karol on 2016-05-22.
 */
public class Window implements WindowListener{
    private final Bomberman game;

    public Window(Bomberman game) {
        this.game = game;
        this.game.frame.addWindowListener(this);
    }


    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        Packet01Disconnect packet=new Packet01Disconnect(this.game.player.getUsername());
        packet.writeData(this.game.client);
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }
}
