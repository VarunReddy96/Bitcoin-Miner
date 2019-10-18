package edu.rit.cs.CoinMining;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class MasterListner {
    private int port;
    private MasterWriter writer;
    private boolean istopped = false;

    public MasterListner(MasterWriter writer, int port) {
        this.writer = writer;
        this.port = port;
    }

    public void stop() {
        this.istopped = true;
    }

    public boolean getstop(){
        return istopped;
    }

    public void listen() {
        while (!istopped) {
            try {
                DatagramSocket socket = new DatagramSocket(this.port);
                byte buff[] = new byte[256];
                DatagramPacket packet = new DatagramPacket(buff,buff.length);
                socket.receive(packet);
                new packetanalyzer(packet,this.writer,this).start();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
