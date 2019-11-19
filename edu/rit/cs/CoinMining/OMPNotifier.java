package edu.rit.cs.CoinMining;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;

public class OMPNotifier {
    public void nonceFound(int nonceval, InetAddress address, int Port ) {
        try {
            DatagramSocket socket = new DatagramSocket();
            ByteBuffer buff = ByteBuffer.allocate(4);
            buff.putInt(nonceval);
            DatagramPacket packet = new DatagramPacket(buff.array(),buff.array().length,address,Port);
            socket.send(packet);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
