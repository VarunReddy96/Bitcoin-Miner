package edu.rit.cs.CoinMining;

import java.net.DatagramPacket;

public class packetanalyzer extends Thread{
    private DatagramPacket packet;
    private MasterWriter writer;
    private MasterListner listner;
    public packetanalyzer(DatagramPacket packet,MasterWriter writer,MasterListner listner){
        this.packet = packet;
        this.writer = writer;
        this.listner = listner;
    }

    public void run(){
        String received = new String(this.packet.getData());
        if(received.equals("P")){
            this.writer.add(this.packet.getAddress(),this.packet.getPort());
        }
        if(received.length()>1 && this.listner.getstop()){
            this.listner.stop();
            this.writer.closeconnection();
            System.out.println("Nonce Found"+Integer.parseInt(received));
        }
    }

}
