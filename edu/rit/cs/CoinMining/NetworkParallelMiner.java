package edu.rit.cs.CoinMining;
import java.util.concurrent.Future;
import java.net.InetAddress;
import java.net.DatagramSocket;
import java.net.UnknownHostException;
import java.net.SocketException;


public class NetworkParallelMiner {
    private int start,end;

    public NetworkParallelMiner(int start, int end){
        this.start = start;
        this.end = end;
    }


    private static String usage = "NetworkParallelMiner <master address> <master_port>";

    public static void main(String[] args){
        if(args.length != 2){
            System.out.println(usage);
            System.exit(1);
        }
        
        InetAddress masterAddress = null;
        try {
            masterAddress = InetAddress.getByName(args[0]);
        } catch (UnknownHostException e){
            e.printStackTrace();
            System.exit(1);
        }

        DatagramSocket masterSock = null;
        try {
            masterSock = new DatagramSocket(Integer.parseInt(args[1]));
        } catch (SocketException e ){
            e.printStackTrace();
            System.exit(1);
        }

        int coreCount = Runtime.getRuntime().availableProcessors();

        MinerNotifierInterface notifier = new MinerNotifier();

        MinerThreadPoolExecutor threadPool = new MinerThreadPoolExecutor(notifier);

        ClientServerWriter writer = new ClientServerWriter(masterAddress, masterSock);

        ThreadPoolManager tpm = new ThreadPoolManager();
        tpm.setThreadPool(threadPool);
        tpm.setWriter(writer);

        ClientNetworkListner listener = new ClientNetworkListner(tpm, 6400);
        
        listener.start();

        notifier.addListener(tpm);
    }
}


