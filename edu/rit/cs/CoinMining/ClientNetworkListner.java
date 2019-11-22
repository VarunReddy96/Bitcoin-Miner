package edu.rit.cs.CoinMining;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class ClientNetworkListner extends Thread{
    private ThreadPoolManager manager;

    private int port = 0;
    public ClientNetworkListner(ThreadPoolManager manager, int port){
        this.manager = manager;
        this.port = port;
    }

    public ThreadPoolManager returnmanager(){
        return this.manager;
    }

    public void run(){
        while(!this.manager.isIstopped()){
            try {
                DatagramSocket socket = new DatagramSocket();
                byte[] buff = new byte[256];
                DatagramPacket packet = new DatagramPacket(buff,buff.length);
                socket.receive(packet);
                new clientpacketanalyzer(packet,this).start();

            } catch (SocketException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
