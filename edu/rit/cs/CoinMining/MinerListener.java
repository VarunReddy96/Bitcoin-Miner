package edu.rit.cs.CoinMining;


public class MinerListener implements MinerListenerInterface {
    private MinerThreadPoolExecutor tp;

    public void nonceFound(){
        tp.shutdown();
        System.out.println("Shutting down tp");
    }

    public void addShutdown(MinerThreadPoolExecutor tp){
        this.tp = tp;
    }
}
