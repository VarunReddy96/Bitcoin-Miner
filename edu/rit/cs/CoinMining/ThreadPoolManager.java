
package edu.rit.cs.CoinMining;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutionException;

public class ThreadPoolManager implements MinerListenerInterface {
    private MinerThreadPoolExecutor tp;
    private ClientServerWriter writer;
    private Future<Integer>[] futures;
    private boolean sentNonce;

    public boolean isIstopped() {
        return istopped;
    }

    public void setIstopped(boolean istopped) {
        this.istopped = istopped;
    }

    private boolean istopped=false;

    public ThreadPoolManager(){
     
    }

    public void setWriter(ClientServerWriter writer){
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
                futures[cntr] = tp.submit(new MinerCallable(blockData, target, start, end));
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
                try {
                    nonce = futures[cntr].get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

            } else {
                Future<Integer> temp = futures[cntr];
                temp.cancel(true);
            }
        }

        writer.nonceFound(nonce.toString());
        sentNonce = true;
    }

    public void shutdown(){
        this.istopped = true;
        tp.shutdown();
        System.exit(0);
    }
}
