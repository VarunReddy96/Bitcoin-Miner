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
        if(received.charAt(0) == 'p'){
            System.out.println("New connection from: " + packet.getAddress());
            int coreCount = Integer.parseInt(received.substring(1));
            this.writer.add(
                    this.packet.getAddress(),
                    coreCount);
        }
        else if(received.length()>1 && !this.listner.returnmanager().getstop()){
            //this.listner.stop();
            //System.out.println("Nonce found");dd
            this.listner.returnmanager().nonceFound();

            //this.writer.closeconnection();
            //System.out.println("Nonce Found"+Integer.parseInt(received));
        }
    }

}
