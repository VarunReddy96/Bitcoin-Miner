import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

public class TestThreadPoolExecutor extends ThreadPoolExecutor{

    public TestThreadPoolExecutor(){
        super(Runtime.getRuntime().availableProcessors(), Runtime.getRuntime().availableProcessors(), 
                100, TimeUnit.HOURS, new SynchronousQueue());
    }

    @Override
    public void afterExecute(Runnable r, Throwable t){
        super.afterExecute(r, t);
        if(t == null && r instanceof Future<?>){
            try {
                Object result = ((Future<?>)r).get();
                System.out.println("Thread pool: " + result);
                shutdownNow();
            } catch (CancellationException ce){
                t = ce;
            
            } catch (ExecutionException ee){
                t = ee;
            }
            catch (InterruptedException ie){
                t = ie;
            }
        }
    }
}
