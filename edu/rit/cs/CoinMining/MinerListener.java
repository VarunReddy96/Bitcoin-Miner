package edu.rit.cs.CoinMining;


public class MinerListener implements MinerListenerInterface {
    private MinerThreadPoolExecutor tp;
    private int counter = 0;

    public void nonceFound(String xyz) {
        counter++;
        if (counter == 10) {
            tp.shutdownNow();
        }
        //System.out.println("Shutting down tp");
    }

    public void addShutdown(MinerThreadPoolExecutor tp) {
        this.tp = tp;
    }
}
