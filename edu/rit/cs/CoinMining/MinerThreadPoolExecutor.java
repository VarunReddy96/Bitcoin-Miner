package edu.rit.cs.CoinMining;

/*
 * MinerThreadPoolExecutor.java
 *
 * Version:
 *     $Id$
 *
 * Revisions:
 *     $Log$
 */

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.Future;
import java.util.concurrent.CancellationException;
import java.util.ArrayList;

/**
 * This class is used to notify the notifier once the nonce has been found by a thread..
 *
 */

public class MinerThreadPoolExecutor extends ThreadPoolExecutor {
    private ArrayList<MinerNotifierInterface> notifiers = new ArrayList<MinerNotifierInterface>();
    private boolean notificationDone = false;

    public MinerThreadPoolExecutor(MinerNotifierInterface notify) {
        super(Runtime.getRuntime().availableProcessors(), Runtime.getRuntime().availableProcessors() * 2,
                100, TimeUnit.HOURS, new SynchronousQueue());
        notifiers.add(notify);
    }

    public void addNotifier(MinerNotifierInterface notify){
        notifiers.add(notify);
    }

    public void setNotifiyFalse(){
        notificationDone = false;
    }

    @Override
    public synchronized void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        if (t == null && r instanceof Future<?> && !notificationDone) {
            try {
                for(MinerNotifierInterface notifer : notifiers){
                    notifer.foundNonce();
                }
                this.notificationDone = true;
            } catch (CancellationException ce) {
                t = ce;
            }
        }
    }
}
