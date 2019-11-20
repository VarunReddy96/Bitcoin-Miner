package edu.rit.cs.CoinMining;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;
import java.io.IOException;

public class ClientServerWriter {

    private InetAddress master;
    private DatagramSocket socket;

    public ClientServerWriter(InetAddress main, DatagramSocket socket){
        this.master = main;
        this.socket = socket;
    }

    public void nonceFound(String nonce){
        byte[] toSend = nonce.getBytes();
        DatagramPacket sendPack = new DatagramPacket(toSend, toSend.length, master, 
                socket.getPort());
        try{

        
        socket.send(sendPack);
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    public void pingMaster(){
        byte[] ping = "p".getBytes();
        DatagramPacket sendPack = new DatagramPacket(ping, ping.length, master,
                socket.getPort());
        try {
            socket.send(sendPack);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
