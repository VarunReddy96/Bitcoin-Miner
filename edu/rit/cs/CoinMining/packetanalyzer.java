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
        received = received.trim();
        System.out.println("Received packet from client in packetanalyzer: "+ received);
        System.out.println(received.length());
        if(received.equals("p")){
            this.writer.add(this.packet.getAddress(),this.packet.getPort());
        }
        else if(received.length()>1 && !this.listner.returnmanager().getstop()){
            //this.listner.stop();
            System.out.println("Nonce found");
            this.listner.returnmanager().nonceFound();
            //this.writer.closeconnection();
            System.out.println("Nonce Found"+Integer.parseInt(received));
        }
    }

}
