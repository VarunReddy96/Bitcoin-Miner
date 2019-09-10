import java.util.concurrent.Executors;
import java.util.concurrent.AbstractExecutorService;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class Test{

    public static void main(String[] args){
        TestThreadPoolExecutor threadPool = new TestThreadPoolExecutor();
        ArrayList<Future<Integer>> futures = new ArrayList<Future<Integer>>();
        ArrayList<Callable<Integer>> callables = new ArrayList<Callable<Integer>>();
        for(int cntr = 0; cntr < 4; cntr ++){
            Callable<Integer> task = () -> {
                Thread.sleep(3000);
                return 1;
            };
            callables.add(task);
        }

        for(int cntr = 0; cntr < 4; cntr ++){
            threadPool.submit(callables.get(cntr));
            try {
                Thread.sleep(500);
            } catch (Exception e){
                System.err.println(e.toString());
            }
        
        }
    }
}
