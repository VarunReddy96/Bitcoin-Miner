package edu.rit.cs.CoinMining;

/*
 * MasterWriter.java
 *
 * Version:
 *     $Id$
 *
 * Revisions:
 *     $Log$
 */


import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class is used to send packets containing the range of numbers that the workers had to mine on.
 *
 */


public class MasterWriter {
    private ConcurrentHashMap<InetAddress, Integer> nodes = new ConcurrentHashMap<>();
    private boolean check = true;
    private int totalCores = 0;
    private Object lock = new Object();

    /**
     * This method is used to send initial chunksize to all the workers to start the mining operations.
     *
     * @param input: the input blockhash
     * @param target: the target hash
     *
     */


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
                //System.out.println("Going through,size of the map is: "+ nodes.size()+ " totoal cores: "+totalCores+" chunksize"+chunksize+" temp is: "+ temp+" temp+chunk is:" + (temp+chunksize * nodes.get(address))+" corecount = "+nodes.get(address));
                previous = address;
                if (nodes.size()!=1) {
                    buff = (input + " " + target + " " + temp + " " + 
                            (temp + chunksize * nodes.get(address))).getBytes();
                    //System.out.println("Enterd here Hello---------------------------------------------------------------------------------");
                    packet = new DatagramPacket(buff, buff.length, previous, 6400);
                    socket = new DatagramSocket();
                    socket.send(packet);
                    temp = temp + chunksize * nodes.get(address) + 1;
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

    /**
     * This method is used to send closeconnection message to all the workers for them to shut their threadpoolexecutors...
     *
     */


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

    /**
     * This methods adds all the worker nodes that ping the master into a map.
     *
     * @param address:The ipaddress of the worker node
     * @param cores: The number of cores present on them.
     */


    public void add(InetAddress address, int cores) {
        this.nodes.put(address, cores);
        synchronized(lock){
            totalCores = totalCores + cores;
        }
    }
}
