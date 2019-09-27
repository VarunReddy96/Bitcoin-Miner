package edu.rit.cs.CoinMining;
import java.util.concurrent.Future;


public class ParallelMiner {

    public static void main(String[] args){
        int coreCount = Runtime.getRuntime().availableProcessors();

        MinerListener listener = new MinerListener();
        MinerNotifierInterface notifier = new MinerNotifier();
        notifier.addListener(listener);

        MinerThreadPoolExecutor threadPool = new MinerThreadPoolExecutor(notifier);
        listener.addShutdown(threadPool);

        Future[] futures = new Future[coreCount];
        
        int intMax = Integer.MAX_VALUE;
        int intMin = Integer.MIN_VALUE;
        int temp = intMin;
        if(coreCount==1){
            long split = (intMax - intMin);
        }else {
            int split = (intMax - intMin) / coreCount;
        }
        for(int cntr = 0; cntr < coreCount-1; cntr++){
            //int loc = (int)cntr/split;
            futures[loc] = threadPool.submit(new MinerCallable(args[0], args[1], temp, temp+split ));
            temp = temp + split + 1;
        }

        futures[coreCount-1] = threadPool.submit(new MinerCallable(args[0], args[1], temp, intMax ));
    }
}
