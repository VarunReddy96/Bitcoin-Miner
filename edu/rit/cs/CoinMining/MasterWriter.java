package edu.rit.cs.CoinMining;

import javax.xml.crypto.Data;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.ConcurrentHashMap;

public class MasterWriter {
    private ConcurrentHashMap<InetAddress, Integer> nodes = new ConcurrentHashMap<>();
    private boolean check = true;
    private int totalCores = 0;
    private Object lock = new Object();


    public void sendchunks(String input, String target) {
        try {
            int size = totalCores;
            double t1 = Integer.MAX_VALUE;
            double t2 = Integer.MIN_VALUE;
            int chunksize =(int) (t1 - t2) / size;
            int temp = Integer.MIN_VALUE, count = 0;
            InetAddress previous = InetAddress.getLocalHost();
            DatagramPacket packet;
            DatagramSocket socket;
            byte buff[];
            for (InetAddress address : nodes.keySet()) {
                previous = address;
                if (count != size - 1) {
                    buff = (input + " " + target + " " + temp + " " + 
                            (temp + chunksize * nodes.get(address))).getBytes();
                    System.out.println();
                    packet = new DatagramPacket(buff, buff.length, previous, 6400);
                    socket = new DatagramSocket();
                    socket.send(packet);
                    temp = temp + chunksize + 1;
                } else {
                    break;
                }
            }
            buff = (input + " " + target + " " + temp + " " + Integer.MAX_VALUE).getBytes();
            packet = new DatagramPacket(buff, buff.length, previous, 6400);
            socket = new DatagramSocket();
            socket.send(packet);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void closeconnection() {
        if (check) {
            try {
                DatagramPacket packet;
                DatagramSocket socket;
                byte buff[];
                for (InetAddress address : nodes.keySet()) {
                    buff = "S".getBytes();
                    packet = new DatagramPacket(buff, buff.length, address, 6400);
                    socket = new DatagramSocket();
                    socket.send(packet);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            check = false;
        }
    }

    public void add(InetAddress address, int cores) {
        this.nodes.put(address, cores);
        synchronized(lock){
            totalCores = totalCores + cores;
        }
    }
}
