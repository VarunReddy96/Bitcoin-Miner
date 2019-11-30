package edu.rit.cs.CoinMining;

/*
 * clientpacketanalyzer.java
 *
 * Version:
 *     $Id$
 *
 * Revisions:
 *     $Log$
 */

import java.net.DatagramPacket;

/**
 * This class is used to analyze the packets received from the clientNetworkListner.
 *
 */

public class clientpacketanalyzer extends Thread {
    private DatagramPacket packet;
    private ClientNetworkListner listner;
    public clientpacketanalyzer(DatagramPacket packet, ClientNetworkListner listner){
        this.packet = packet;
        this.listner = listner;
    }

    public void run(){
        String received = new String(this.packet.getData(), 0, this.packet.getLength());
        if(received.length()==1){
            this.listner.returnmanager().shutdown();
            System.exit(0);
        }else{
            String split[] = received.split(" ", 0);
            this.listner.returnmanager().startPOW(split[0], split[1], Integer.parseInt(split[2]), Integer.parseInt(split[3]));
        }
    }
}
