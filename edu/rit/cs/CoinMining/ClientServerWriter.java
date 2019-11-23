package edu.rit.cs.CoinMining;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;
import java.io.IOException;

public class ClientServerWriter {

    private InetAddress master;
    private DatagramSocket socket;
    private int listenport;

    public ClientServerWriter(InetAddress main, DatagramSocket socket,int port){
        this.master = main;
        this.socket = socket;
        System.out.println("Writer port: " + port);
        this.listenport = port;
    }

    public void nonceFound(String nonce){
        System.out.println("Sending the nonce");
        byte[] toSend = nonce.getBytes();
        DatagramPacket sendPack = new DatagramPacket(toSend, toSend.length, master, 
                this.listenport);
        try{

        
        socket.send(sendPack);
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    public void pingMaster(){
        String temp = "p";
        temp = temp + Runtime.getRuntime().availableProcessors();
        byte[] ping = temp.getBytes();
        DatagramPacket sendPack = new DatagramPacket(ping, ping.length, master,
                this.listenport);
        try {
            System.out.println("Pinging");
            socket.send(sendPack);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
