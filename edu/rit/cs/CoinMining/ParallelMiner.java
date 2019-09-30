package edu.rit.cs.CoinMining;
import java.util.concurrent.Future;


public class ParallelMiner {

    public static void main(String[] args){
        int coreCount = Runtime.getRuntime().availableProcessors();

        MinerListener listener = new MinerListener();
        MinerNotifierInterface notifier = new MinerNotifier();
        notifier.addListener(listener);
        MinerPrinter print = new MinerPrinter();
        MinerThreadPoolExecutor threadPool = new MinerThreadPoolExecutor(notifier);
        notifier.addListener(print);
        listener.addShutdown(threadPool);
        System.out.println(coreCount);
        Future[] futures = new Future[coreCount];
        print.setFutureArray(futures);
        int i = futures.length;
        System.out.println("length:" +i);
        
        int intMax = Integer.MAX_VALUE;
        int intMin = Integer.MIN_VALUE;
        int temp = intMin;
        if(coreCount==1){
            futures[coreCount-1] = threadPool.submit(new MinerCallable(Integer.MIN_VALUE,Integer.MAX_VALUE  ));
        }else {
            int split = (intMax - intMin) / coreCount;
            for (int cntr = 0; cntr < coreCount - 1; cntr++) {
                System.out.println("cntr: "+cntr);
                futures[cntr] = threadPool.submit(new MinerCallable(temp, temp+split ));
                temp = temp + split + 1;
            }
            System.out.println("corecoun:"+coreCount);
            futures[coreCount - 1] = threadPool.submit(new MinerCallable(temp, intMax));

        }
    }
}
