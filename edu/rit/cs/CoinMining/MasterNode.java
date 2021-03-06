package edu.rit.cs.CoinMining;

/*
 * MasterNode.java
 *
 * Version:
 *     $Id$
 *
 * Revisions:
 *     $Log$
 */


import static java.lang.Thread.sleep;
import java.util.Scanner;

/**
 * This class starts the Master/server and also starts MasterManager which manages the mining operations.
 *
 */

public class MasterNode {
    public static void main(String[] args) {
        try {
            MasterWriter writer = new MasterWriter();
            MasterManager manager = new MasterManager(args[1],args[2],writer);
            MasterListner listener = new MasterListner(writer, Integer.parseInt(args[0]),manager);
            listener.start();

            System.out.println("Enter 's' to start");

            Scanner scan = new Scanner(System.in);
            while(scan.hasNext()){
                if(scan.next().equals("s")){
                    System.out.println("Starting...");
                    break;
                } else {
                    System.out.println("Press 's' to start");
                }
            }

            manager.manage();
        } catch (Exception E) {
            E.printStackTrace();
        }
    }
}
