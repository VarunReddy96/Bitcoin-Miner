
package edu.rit.cs.CoinMining;
import java.util.concurrent.CancellationException;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutionException;

public class ThreadPoolManager implements MinerListenerInterface {
    private MinerThreadPoolExecutor tp;
    private ClientServerWriter writer;
    private Future<Integer>[] futures;
    private boolean sentNonce,test = true;

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
        System.out.println("client: network told me to start a new POW");
        System.out.println("block data: " + blockData);
        System.out.println("target: " + target);
        int temp = 0;
        sentNonce = false;
        tp.setNotifiyFalse();
        try{
            for(int cntr = 0; cntr < futures.length; cntr ++){
                Future<Integer> tempFuture = futures[cntr];
                tempFuture.cancel(true);
            }
        } catch (NullPointerException e){

        }
        tp.purge();
        if(futures.length == 1){
            futures[0] = tp.submit(new MinerCallable(blockData, target, start, end));
        } else {
            int split =(end-start) / futures.length;
            temp = start;
            int count = 0;
            for(int cntr = 0; cntr < futures.length  - 1; cntr ++){
                futures[cntr] = tp.submit(new MinerCallable(blockData, target, temp, temp+split));
                temp = temp + split + 1;
            }
            futures[futures.length - 1] = tp.submit(new MinerCallable(blockData, target, temp, end));
        } 

    }

    public synchronized void nonceFound(String s){
        test = true;
       // System.out.println("client: network said nonce found!");
        if(sentNonce){
            return;
        }
        Integer nonce = null;
        for(int cntr = 0; cntr < futures.length; cntr ++){
            if(futures[cntr].isDone() && this.test){
                try {
                    test= false;
                    nonce = futures[cntr].get();
                    System.out.println(futures[cntr] + " " + nonce);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }catch (CancellationException e){
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
