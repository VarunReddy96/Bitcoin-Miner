package edu.rit.cs.CoinMining;

import static java.lang.Thread.sleep;

public class MasterNode {
    public static void main(String[] args) {
        try {
            MasterWriter writer = new MasterWriter();
            MasterManager manager = new MasterManager(args[1],args[2],writer);
            MasterListner listener = new MasterListner(writer, Integer.parseInt(args[0]),manager);
            listener.start();
            sleep(5000);
            manager.manage();
            //writer.sendchunks(args[1],args[2]);
        } catch (Exception E) {
            E.printStackTrace();
        }
    }
}
