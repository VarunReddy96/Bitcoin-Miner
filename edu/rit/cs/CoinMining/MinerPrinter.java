package edu.rit.cs.CoinMining;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class MinerPrinter implements  MinerListenerInterface{
    private Future[] vals;
    private boolean foundval = false;


    @Override
    public void nonceFound() {
        for(Future val: vals){
            if(val.isDone() && !this.foundval){
                try {
                    System.out.println("Nonce Found:"+val.get());
                    this.foundval = true;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setFutureArray(Future[] vals){
        this.vals = vals;
    }
}
