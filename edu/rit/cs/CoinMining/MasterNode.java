package edu.rit.cs.CoinMining;

import static java.lang.Thread.sleep;

public class MasterNode {
    public static void main(String[] args) {
        try {
            MasterWriter writer = new MasterWriter();
            MasterListner listener = new MasterListner(writer, Integer.parseInt(args[0]));
            sleep(5000);
            writer.sendchunks(args[1],args[2]);
        } catch (Exception E) {

        }
    }
}
