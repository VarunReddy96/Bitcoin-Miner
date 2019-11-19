package edu.rit.cs.CoinMining;
import java.util.concurrent.Future;


public class ThreadPoolManager implements MinerListenerInterface {
    private MinerThreadPoolExecutor tp;
    private WorkerWriter writer;
    private Future<Integer>[] futures;
    private boolean sentNonce;

    public ThreadPoolManager(){
     
    }

    public void setWriter(WorkerWriter writer){
       this.writer = writer; 
    }

    public void setThreadPool(MinerThreadPoolExecutor tp){
        this.tp = tp;
        futures = new Future[tp.getMaximumPoolSize()];
    }

    public synchronized void startPOW(String blockData, String target, int start, int end){
        int temp = Integer.MIN_VALUE;
        sentNonce = false;
        if(futures.length == 1){
            futures[0] = tp.submit(new MinerCallable(blockData, target, start, end));
        } else {
            int split = (Integer.MAX_VALUE - Integer.MIN_VALUE) / futures.length;
            for(int cntr = 0; cntr < futures.length  - 1; cntr ++){
                futures[cntr] = tp.submit(new MinerCalleable(blockData, target, start, end));
                temp = temp + split + 1;
            }
        } 
        futures[futures.length - 1] = tp.submit(new MinerCallable(blockData, target, start, end));
    }

    public synchronized void nonceFound(){
        if(sentNonce){
            return;
        }
        Integer nonce = null;
        for(int cntr = 0; cntr < futures.length; cntr ++){
            if(futures[cntr].isDone()){
                nonce = futures[cntr].get();
            } else {
                futures[cntr].cancel();
            }
        }

        writer.sendNonce(nonce);
        sentNonce = true;
    }

    public void shutdown(){
        tp.shutdown();
    }
}
