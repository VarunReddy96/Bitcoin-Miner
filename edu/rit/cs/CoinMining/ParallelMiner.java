package rit.edu.cs;
import java.util.concurrent.Future;


public class ParallelMiner {

    public static void main(String[] args){
        int coreCount = Runtime.getRuntime().availableProcessors();
        MinerThreadPoolExecutor threadPool = new MinerThreadPoolExecutor();
        Future[] futures = new Future[coreCount];
        
        long intMax = Integer.MAX_VALUE;
        long intMin = Integer.MIN_VALUE;
        long temp = intMin;

        double split = (intMax - intMin)/coreCount;
        for(int cntr = Integer.MIN_VALUE; cntr <= split; cntr = cntr + split + 1){
            int loc = (int)cntr/split;
            futures[loc] = threadPool.submit(new MinerCallable(args[0], args[1], cntr, cntr*split ));
        }
    }

}
