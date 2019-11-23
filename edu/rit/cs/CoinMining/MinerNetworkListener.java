package edu.rit.cs.CoinMining;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class MinerNetworkListener implements MinerNotifierInterface {
    ArrayList<MinerListenerInterface> listeners;


    @Override
    public void addListener(MinerListenerInterface listener) {
        listeners.add(listener);
    }

    @Override
    public void foundNonce() {
        // Listen to the network until we've found our listener.
        //while (!istopped) {
            try {
                DatagramSocket socket = new DatagramSocket(80);
                byte buff[] = new byte[256];
                DatagramPacket packet = new DatagramPacket(buff,buff.length);
                socket.receive(packet);
                ByteBuffer byteBuff = ByteBuffer.allocate(256);
                byteBuff.put(buff);
                byte signal = byteBuff.get(0);
                if(signal == 'S'){
                    // We're stopping.
                    for(MinerListenerInterface listener : listeners){
                        listener.nonceFound("");
                    }
                } else {
                    // We're reading chunks and have to spawn up our thread pool
                    int offset = 0;
                    byte[] input = new byte[64];
                    byteBuff.get(input, 0, input.length);
                    String block = new String(input);

                    offset = offset + input.length + 1;

                    byteBuff.get(input, offset, input.length);
                    String target = new String(input);
                    offset = offset + input.length + 1;

                    int startIndex = byteBuff.getInt(offset);

                    // Magic number, this may cause an error.
                    offset = offset + 4;

                    int endIndex = byteBuff.getInt(offset);

                    // Now I have the bounds, I need to setup the local parallel miner with its bounds and listeners

                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        //}
    }
}
