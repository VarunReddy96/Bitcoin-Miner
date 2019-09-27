package edu.rit.cs.CoinMining;

import java.util.ArrayList;

public class MinerNotifier implements MinerNotifierInterface{
    private ArrayList<MinerListenerInterface> listeners = new ArrayList<>();

    public MinerNotifier(){}

    public void addListener(MinerListenerInterface newListener){
        listeners.add(newListener);
    }
    
    public void foundNonce(){
        for(MinerListenerInterface listener : listeners){
            listener.nonceFound();
        }
    }
}
