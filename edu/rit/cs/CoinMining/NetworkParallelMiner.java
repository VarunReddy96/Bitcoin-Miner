package edu.rit.cs.CoinMining;
import java.util.concurrent.Future;
import java.net.InetAddress;
import java.net.DatagramSocket;


public class NetworkParallelMiner {
    private int start,end;

    public NetworkParallelMiner(int start, int end){
        this.start = start;
        this.end = end;
    }


    private static String usage = "NetworkParallelMiner <master address> <master_port>";

    public static void main(String[] args){
        if(args.length != 3){
            System.out.println(usage);
            System.exit(1);
        }
        
        InetAddress masterAddress = InetAddress.getByName(args[1]);
        DatagramSocket masterSock = new DatagramSocket(Integer.parseInt(args[2]));

        int coreCount = Runtime.getRuntime().availableProcessors();

        MinerNotifierInterface notifier = new MinerNotifier();

        MinerThreadPoolExecutor threadPool = new MinerThreadPoolExecutor(notifier);

        ClientServerWriter writer = new ClientServerWriter(masterAddress, masterSock);

        ThreadPoolManager tpm = new ThreadPoolManager();
        tpm.setThreadPool(threadPool);
        tpm.setWriter(writer);

        MinerNotifier.addListener(tpm);
    }
}


