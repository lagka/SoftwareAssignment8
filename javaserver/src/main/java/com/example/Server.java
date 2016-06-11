package com.example;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

/**
 * Created by Gary on 16/5/28.
 */
public class Server extends JFrame implements Runnable{
    private Thread thread;
    private ServerSocket servSock;
    private JTextArea textArea= new JTextArea();
    public Server(){
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(300, 200);
        this.setResizable(false);
        this.setVisible(true);
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
        this.textArea.setEditable(false);
        this.setPreferredSize(new Dimension(1000, 800));
        JScrollPane scrollPane = new JScrollPane(this.textArea);
        this.add(scrollPane);

        this.pack();
        try {
            // Detect server ip
            InetAddress IP = InetAddress.getLocalHost();
            System.out.println("IP of my system is := "+IP.getHostAddress());
            System.out.println("Waitting to connect......");

            // Create server socket
            servSock = new ServerSocket(2000);

            // Create socket thread
            thread = new Thread(this);
            thread.start();
        } catch (java.io.IOException e) {
            System.out.println("Socket啟動有問題 !");
            System.out.println("IOException :" + e.toString());
        } finally{

        }
    }

    @Override
    public void run(){
        BufferedReader reader;
        // Running for waitting multiple client
        while(true){
            try{
                // After client connected, create client socket connect with client
                Socket clntSock = servSock.accept();
                InputStream in = clntSock.getInputStream();
                reader = new BufferedReader(new InputStreamReader(in));

                System.out.println("Connected!!");

                // Transfer data
               // byte[] b = new byte[1024];
              //  int length;
                String line = reader.readLine();
                //length = in.read(b);
              //  String s = new String(b);
               // System.out.println("[Server Said]" + s);
                textArea.append("The result from APP: "+ line + "\n");
                System.out.println(line);
                System.out.println(":)");
            }
            catch(Exception e){
                //System.out.println("Error: "+e.getMessage());
                break;
            }
        }
    }
}
