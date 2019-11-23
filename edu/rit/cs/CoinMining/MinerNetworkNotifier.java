package edu.rit.cs.CoinMining;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutionException;

public class MinerNetworkNotifier implements MinerListenerInterface{
    private InetAddress masterAddress;
    private Future<Integer>[] vals;
    private boolean foundVal = false;

    public MinerNetworkNotifier(InetAddress masterAddress){
        this.masterAddress = masterAddress;
    }

    public void setFutureArray(Future<Integer> vals[]){
        this.vals = vals;
    }

    @Override
    public void nonceFound(String s) {
        // Write to the network that we've found a nonce
        Integer nonceValue = 0;
        for(Future<Integer> val: vals){
            if(val.isDone() && !this.foundVal){
                try {
                    nonceValue = val.get();
                    this.foundVal = true;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }

        try {
            DatagramPacket pack;
            DatagramSocket sock;
            ByteBuffer sendBytes = ByteBuffer.allocate(4);
            sendBytes.putInt(nonceValue);
            pack = new DatagramPacket(sendBytes.array(), sendBytes.limit(), masterAddress, 8080);
            sock = new DatagramSocket();
            sock.send(pack);
        } catch (Exception e){
            e.printStackTrace(System.err);
        }
    }

    public void pingMaster(){
        try{
            DatagramPacket pack;
            DatagramSocket sock;
            byte[] ping = {'P'};
            pack = new DatagramPacket(ping, ping.length, masterAddress, 8000);
            sock = new DatagramSocket();
            sock.send(pack);
        } catch (Exception e){
            e.printStackTrace(System.err);
        }
    }
}
