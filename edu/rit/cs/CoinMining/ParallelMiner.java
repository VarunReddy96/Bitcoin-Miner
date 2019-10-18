package edu.rit.cs.CoinMining;
import java.util.concurrent.Future;


public class ParallelMiner {
    private int start,end;

    public ParallelMiner(int start, int end){
        this.start = start;
        this.end = end;
    }
    public static void main(String[] args){
        int coreCount = Runtime.getRuntime().availableProcessors();

        MinerListener listener = new MinerListener();
        MinerNotifierInterface notifier = new MinerNotifier();
        notifier.addListener(listener);
        MinerPrinter print = new MinerPrinter();
        MinerThreadPoolExecutor threadPool = new MinerThreadPoolExecutor(notifier);
        notifier.addListener(print);
        listener.addShutdown(threadPool);
        Future[] futures = new Future[coreCount];
        print.setFutureArray(futures);
        //System.out.println("length:" +i);
        
        int intMax = Integer.MAX_VALUE; // This would be the start of the chunk in multinode.
        int intMin = Integer.MIN_VALUE; // This would be the end of the chunk in multinode.
        int temp = intMin;
        if(coreCount==1){
            futures[coreCount-1] = threadPool.submit(new MinerCallable(intMin,intMax));
        }else {
            int split = (intMax - intMin) / coreCount;
            for (int cntr = 0; cntr < coreCount - 1; cntr++) {
                //System.out.println("cntr: "+cntr);
                futures[cntr] = threadPool.submit(new MinerCallable(temp, temp+split ));
                temp = temp + split + 1;
            }
            //System.out.println("corecoun:"+coreCount);
            futures[coreCount - 1] = threadPool.submit(new MinerCallable(temp, intMax));

        }
    }
}


