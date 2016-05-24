package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static java.sql.DriverManager.getConnection;


/**
 * Created by Karol on 2016-03-17.
 */
public class Log extends JFrame {
    private JLabel lTlo;
    private JFrame frame = new JFrame();
    private JButton button_log = new JButton();
    private JTextField txtlogin = new JTextField();
    private JPasswordField txtpasword = new JPasswordField();
    private JCheckBox register = new JCheckBox("REJESTRACJA");
    private String url="jdbc:mysql://localhost:3306/1project?useSSL=false";
    private String user="root";
    private String pass="admin";
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
        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        button_log.setBounds(150, 300, 100, 30);
        button_log.setBackground(Color.lightGray);
        button_log.setText("GRAJ");
        button_log.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String login=txtlogin.getText();
                String password=txtpasword.getText();

                    try {
                        Connection myConn = getConnection(url, user, pass);
                        Statement myStatement = myConn.createStatement();
                        ResultSet rs;
                        String sql1="select * from students where login='"+login+"'";
                        rs=myStatement.executeQuery(sql1);
                        if(!rs.next()){
                            if(register.isSelected()) {
                                JFrame frame3=new JFrame();
                                frame3.setVisible(true);
                                frame3.setSize(300,400);
                                frame3.setLocationRelativeTo(null);
                                frame3.setBackground(Color.lightGray);
                                frame3.setLayout(null);
                                frame3.setBackground(Color.gray);

                                JTextField jimie=new JTextField("imie");
                                jimie.setBounds(100,30,100,30);
                                jimie.setBackground(Color.lightGray);
                                jimie.addMouseListener(new MouseAdapter() {
                                    @Override
                                    public void mouseClicked(MouseEvent e) {
                                        jimie.setText("");
                                    }
                                });
                                JTextField jnazwisko=new JTextField("nazwisko");
                                jnazwisko.setBounds(100,90,100,30);
                                jnazwisko.setBackground(Color.lightGray);
                                jnazwisko.addMouseListener(new MouseAdapter() {
                                    @Override
                                    public void mouseClicked(MouseEvent e) {
                                        jnazwisko.setText("");
                                    }
                                });
                                JTextField jemail=new JTextField("email");
                                jemail.setBounds(50,150,200,30);
                                jemail.setBackground(Color.lightGray);
                                jemail.addMouseListener(new MouseAdapter() {
                                    @Override
                                    public void mouseClicked(MouseEvent e) {
                                        jemail.setText("");
                                    }
                                });
                                JLabel error=new JLabel();
                                error.setBounds(100,200,200,40);
                                error.setForeground(Color.red);
                                JButton ok=new JButton();
                                ok.setText("OK");
                                ok.setBackground(Color.lightGray);
                                ok.setBounds(110,300,80,30);
                                ok.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        String imie,nazwisko,email;
                                        imie=jimie.getText();
                                        nazwisko=jnazwisko.getText();
                                        email=jemail.getText();
                                        if(!emailValidate(email)){
                                            error.setText("email niepoprawny");
                                        }
                                        if(!nazwiskoValidate(email)){
                                            error.setText("nazwisko niepoprawne");
                                        }
                                        if(!imieValidate(email)){
                                            error.setText("imie niepoprawne");
                                        }
                                        if(emailValidate(email)&&imieValidate(imie)&&nazwiskoValidate(nazwisko)) {

                                            error.setBounds(50,200,250,40);
                                            error.setForeground(Color.GREEN);
                                            error.setText("dane poprawne,prosze znow kliknac");
                                            ok.addActionListener(new ActionListener() {
                                                @Override
                                                public void actionPerformed(ActionEvent e) {
                                                    String sql = "insert into students values('" + login + "', '" + password + "','" + imie + "','" + nazwisko + "','" + email + "')";
                                                    try {
                                                        myStatement.executeUpdate(sql);
                                                    } catch (SQLException e1) {
                                                        e1.printStackTrace();
                                                    }
                                                    System.out.println("Baza zaaktualizowana");
                                                    frame3.dispose();
                                                    Bomberman game = new Bomberman(login);
                                                    frame.dispose();
                                                }
                                            });

                                        }
                                    }
                                });
                                frame3.add(ok);
                                frame3.add(jimie);
                                frame3.add(jnazwisko);
                                frame3.add(jemail);
                                frame3.add(error);
                                frame3.validate();

                            }else {
                                JFrame frame2=new JFrame();
                                frame2.setVisible(true);
                                frame2.setSize(200,100);
                                frame2.setLocationRelativeTo(null);
                                JLabel lj2=new JLabel("Taki uzytkownik nie istnieje",SwingConstants.CENTER);
                                frame2.add(lj2);
                            }
                        }
                        else {
                            if(register.isSelected()) {
                                JFrame frame2=new JFrame();
                                frame2.setVisible(true);
                                frame2.setSize(200,100);
                                frame2.setLocationRelativeTo(null);
                                JLabel lj2=new JLabel("Taki uzytkownik juz istnieje",SwingConstants.CENTER);
                                frame2.add(lj2);

                            }else {
                                Bomberman game = new Bomberman(login);
                                frame.dispose();
                            }
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }


            }
        });

        frame.add(register);
        frame.add(txtlogin);
        frame.add(txtpasword);
        frame.add(button_log);

        lTlo = new JLabel();
        lTlo.setIcon(new ImageIcon("C:\\Users\\Karol\\Desktop\\Java_lato_2015-2016_Karol_Rodak\\bomberman\\src\\com\\company\\Textures\\tlo.jpg"));
        lTlo.setBounds(0, 0, 400, 400);
        frame.add(lTlo);

        //frame.add(panel);
        frame.setVisible(true);
        frame.validate();

        try {
            Connection myConn = getConnection(url,user,pass);
            System.out.println("Connected database successfully...");
            Statement myStatement=myConn.createStatement();
            ResultSet myRs=myStatement.executeQuery("select * from students");
            while (myRs.next()){
                System.out.println(myRs.getString("login")+" "+myRs.getString("imie")+" "+myRs.getString("nazwisko"));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean emailValidate(String email){

        String pattern="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        return email.matches(pattern);

    }
    public boolean imieValidate(String imie){

        String pattern="[a-zA-Z\\-'\\s]+";
        return imie.matches(pattern);

    }
    public boolean nazwiskoValidate(String nazwisko){

        String pattern="[a-zA-Z\\-'\\s]+";
        return nazwisko.matches(pattern);

    }


}
