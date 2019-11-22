package edu.rit.cs.CoinMining;

import java.lang.management.ManagementFactory;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class MasterListner extends Thread{
    private int port;
    private MasterWriter writer;

    private MasterManager manager;


    public MasterListner(MasterWriter writer, int port,MasterManager manager) {
        this.writer = writer;
        this.port = port;
        this.manager = manager;
    }



    public MasterManager returnmanager(){
        return this.manager;
    }



    public void run() {
        while (!this.manager.getstop()) {
            System.out.println("LISTENING" + port);
            try {
                DatagramSocket socket = new DatagramSocket(this.port);
                byte buff[] = new byte[256];
                DatagramPacket packet = new DatagramPacket(buff,buff.length);
                socket.receive(packet);
                new packetanalyzer(packet,this.writer,this).start();
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
