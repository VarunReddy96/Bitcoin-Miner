package edu.rit.cs.CoinMining;

import java.util.ArrayList;

public class MinerNotifier implements MinerNotifierInterface{
    private ArrayList<MinerListenerInterface> listeners = new ArrayList<>();
    private Object loc= new Object();
    private boolean notificationdone = false;
    public MinerNotifier(){}

    public void addListener(MinerListenerInterface newListener){
        listeners.add(newListener);
    }
    
    public void foundNonce(){
        synchronized (loc){
            if(!notificationdone) {
                for (MinerListenerInterface listener : listeners) {
                    listener.nonceFound();
                }
                notificationdone = true;

            }
        }
    }
}
