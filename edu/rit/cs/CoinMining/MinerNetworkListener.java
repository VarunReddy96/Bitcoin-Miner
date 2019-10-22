package edu.rit.cs.CoinMining;

import java.util.ArrayList;

public class MinerNetworkListener implements MinerNotifierInterface {
    ArrayList<MinerListenerInterface> listeners;


    @Override
    public void addListener(MinerListenerInterface listener) {

    }

    @Override
    public void foundNonce() {
        // Listen to the network until we've found our listener.

    }
}
