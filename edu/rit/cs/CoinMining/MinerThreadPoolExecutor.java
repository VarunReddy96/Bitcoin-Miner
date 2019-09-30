package edu.rit.cs.CoinMining;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

public class MinerThreadPoolExecutor extends ThreadPoolExecutor{

    private MinerNotifierInterface notify;
    private boolean notificationDone = false;
    public MinerThreadPoolExecutor(MinerNotifierInterface notify){
        super(Runtime.getRuntime().availableProcessors(), Runtime.getRuntime().availableProcessors(), 
                100, TimeUnit.HOURS, new SynchronousQueue());
        this.notify = notify;
    }

    @Override
    public void afterExecute(Runnable r, Throwable t){
        super.afterExecute(r, t);
        if(t == null && r instanceof Future<?> && !notificationDone){
            try {

                    notify.foundNonce();
                    this.notificationDone = true;

            } catch (CancellationException ce){
                t = ce;
            
            }
        }
    }
}
