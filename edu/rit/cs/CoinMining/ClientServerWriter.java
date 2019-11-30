package edu.rit.cs.CoinMining;

/*
 * ClientServerWriter.java
 *
 * Version:
 *     $Id$
 *
 * Revisions:
 *     $Log$
 */

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;
import java.io.IOException;

/**
 * This class is used to send packets back to the Master.
 *
 */

public class ClientServerWriter {

    private InetAddress master;
    private DatagramSocket socket;
    private int listenport;

    public ClientServerWriter(InetAddress main, DatagramSocket socket,int port){
        this.master = main;
        this.socket = socket;
        this.listenport = port;
    }

    /**
     * This methods sends the nonce value back to the Master once it is found by the worker threads.
     *
     * @param nonce: The found nonce value
     */

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

    /**
     * This method is used to ping the Master during the initial setup phase.
     *
     */

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
