package edu.rit.cs.CoinMining;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class MinerPrinter implements  MinerListenerInterface{
    private Future[] vals;
    private boolean foundval = false;
    private long start;

    public MinerPrinter(long start){
        this.start = start;
    }


    @Override
    public void nonceFound(String s) {
        for(Future val: vals){
            if(val.isDone() && !this.foundval){
                try {
                    long end = System.currentTimeMillis();
                    System.out.println("Nonce Found:"+val.get()+" time taken(ms): "+(end-this.start));
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
