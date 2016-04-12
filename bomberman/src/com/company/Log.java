package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


/**
 * Created by Karol on 2016-03-17.
 */
public class Log extends JFrame {
    private JLabel lTlo;
    private JFrame frame = new JFrame();
    private JButton button_log = new JButton();
    private JTextField txtlogin = new JTextField();
    private JTextField txtpasword = new JTextField();
    private JCheckBox register = new JCheckBox("REJESTRACJA");

    public Log() {


        frame.setResizable(false);
        frame.setTitle("Log");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        txtlogin.setBounds(150, 100, 100, 30);
        txtlogin.setBackground(Color.lightGray);
        txtlogin.setText("LOGIN");
        txtlogin.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                txtlogin.setText("");
            }
        });
        txtpasword.setBounds(150, 200, 100, 30);
        txtpasword.setBackground(Color.lightGray);
        txtpasword.setText("PASSWORD");
        txtpasword.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                txtpasword.setText("");
            }
        });
        register.setBounds(280, 200, 120, 30);
        register.setBackground(Color.lightGray);
        button_log.setBounds(150, 300, 100, 30);
        button_log.setBackground(Color.lightGray);
        button_log.setText("GRAJ");
        button_log.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Bomberman game=new Bomberman();
                frame.dispose();
            }
        });

        frame.add(register);
        frame.add(txtlogin);
        frame.add(txtpasword);
        frame.add(button_log);

        lTlo = new JLabel();
        lTlo.setIcon(new javax.swing.ImageIcon("C:\\Users\\Karol\\IdeaProjects\\bomberman\\src\\com\\company\\Textures\\tlo.jpg"));
        lTlo.setBounds(0, 0, 400, 400);
        frame.add(lTlo);

        //frame.add(panel);
        frame.setVisible(true);
        frame.validate();
    }

}
