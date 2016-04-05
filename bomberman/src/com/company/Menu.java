package com.company;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Created by Karol on 2016-03-15.
 */
public class Menu extends JFrame{

    private int x=50,y=0;
    private JLabel lTlo;
    private JLabel lTlo2;
    private JFrame frame = new JFrame();
    private JButton button_start=new JButton();
    private JButton button_settings=new JButton();
    private JButton button_koniec=new JButton();
    private Timer tm;
    private boolean reversex=false,reversey=false;

    public Menu() {


        frame.setResizable(false);
        frame.setTitle("Bomberman");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);


        button_start.setText("GRAJ");
        button_start.setBounds(140, 80, 120, 30);
        button_start.setBackground(Color.lightGray);
        button_start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Logowanie log = new Logowanie();
                //Bomberman game=new Bomberman();
                frame.dispose();
                tm.stop();
            }
        });

        button_settings.setText("USTAWIENIA");
        button_settings.setBackground(Color.lightGray);
        button_settings.setBounds(140, 185, 120, 30);


        button_koniec.setText("WYJDZ");
        button_koniec.setBounds(140, 290, 120, 30);
        button_koniec.setBackground(Color.lightGray);
        button_koniec.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                frame.dispose();
                tm.stop();
            }
        });
        //panel.setSize(400,400);
        //panel.setLayout(null);
        frame.add(button_start);
        frame.add(button_settings);
        frame.add(button_koniec);

        lTlo = new JLabel();
        lTlo2= new JLabel();
        lTlo.setIcon(new javax.swing.ImageIcon("C:\\Users\\Karol\\IdeaProjects\\bomberman\\src\\com\\company\\Textures\\szary.jpg"));
        lTlo.setBounds(0,0,400,400);

        tm = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lTlo2.setIcon(new javax.swing.ImageIcon("C:\\Users\\Karol\\IdeaProjects\\bomberman\\src\\com\\company\\Textures\\chopek.png"));
                lTlo2.setBounds(x, y, 400, 400);
                if (x == 100 && !reversex){
                    reversex=true;
                }
                else if(x == -100 && reversex){
                    reversex=false;
                }
                if(!reversex) x++;
                else x--;

                if (y == 30 && !reversey){
                    reversey=true;
                }
                else if(y == -36 && reversey){
                    reversey=false;
                }
                if(!reversey) y++;
                else y--;
                System.out.println(x + " " + y);
            }
        });
        frame.add(lTlo2);
        tm.start();
        frame.add(lTlo);

        //frame.add(panel);
        frame.setVisible(true);
        frame.validate();


    }

}
