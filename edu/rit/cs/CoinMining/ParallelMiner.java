package edu.rit.cs.CoinMining;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.concurrent.Future;


public class ParallelMiner {
    private int start,end;

    public ParallelMiner(int start, int end){
        this.start = start;
        this.end = end;
    }


    /**
     * convert byte[] to hex string
     * @param hash
     * @return hex string
     */
    private static String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    /**
     * get a sha256 of the input string
     * @param inputString
     * @return resulting hash in hex string
     */
    public static String SHA256(String inputString) {
        try {
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            return bytesToHex(sha256.digest(inputString.getBytes(StandardCharsets.UTF_8)));
        }catch (NoSuchAlgorithmException ex) {
            System.err.println(ex.toString());
            return null;
        }
    }

    public static String HexValueDivideBy(String hexValue, int val) {
        BigInteger tmp = new BigInteger(hexValue,16);
        tmp = tmp.divide(BigInteger.valueOf(val));
        String newHex = bytesToHex(tmp.toByteArray());
        while (newHex.length() < hexValue.length()) {
            newHex = '0' + newHex;
        }
        return newHex;
    }

    public static String HexValueMultipleBy(String hexValue, int val) {
        BigInteger tmp = new BigInteger(hexValue,16);
        tmp = tmp.multiply(BigInteger.valueOf(val));
        String newHex = bytesToHex(tmp.toByteArray());
        while (newHex.length() < hexValue.length()) {
            newHex = '0' + newHex;
        }
        return newHex;
    }

    /**
     * get a randomized target hash
     * @return randomized target hash
     */
    public static String getTargetHash() {
        Random rand = new Random();
        int randInt = rand.nextInt(1000);
        return SHA256(String.valueOf(randInt));
    }
    public static void main(String[] args){

        // number of blocks to be generated or number of rounds; default to 5
        int numberOfBlocks=10;
        // average block generation time, default to 30 Secs.
        double avgBlockGenerationTimeInSec = 30.0;

        // init block hash
        String initBlockHash = SHA256("CSCI-654 Foundations of Parallel Computing");
        System.out.println("Initial Block Hash:  " + initBlockHash);
        // init target hash
        String initTargetHash = "0000092a6893b712892a41e8438e3ff2242a68747105de0395826f60b38d88dc";
        System.out.println("Initial Target Hash: " + initTargetHash);
        System.out.println();

        int currentBlockID = 1;
        int nonce = 0;
        String tmpBlockHash = initBlockHash;
        String tmpTargetHash = initTargetHash;
        MyTimer myTimer;
        while(currentBlockID <= numberOfBlocks) {


            long start = System.currentTimeMillis();
            int coreCount = Runtime.getRuntime().availableProcessors();

            MinerListener listener = new MinerListener();
            MinerNotifierInterface notifier = new MinerNotifier();
            notifier.addListener(listener);
            MinerPrinter print = new MinerPrinter(start);
            MinerThreadPoolExecutor threadPool = new MinerThreadPoolExecutor(notifier);
            notifier.addListener(print);
            listener.addShutdown(threadPool);
            Future[] futures = new Future[coreCount];
            print.setFutureArray(futures);

//            int intMax = Integer.MAX_VALUE;
//            int intMin = Integer.MIN_VALUE;
            double t1 = Integer.MAX_VALUE;
            double t2 = Integer.MIN_VALUE;
            int temp = Integer.MIN_VALUE;
            if (coreCount == 1) {
                futures[coreCount - 1] = threadPool.submit(new MinerCallable(Integer.MIN_VALUE, Integer.MAX_VALUE));
            } else {

                int split = (int) (t1 - t2) / coreCount;
                for (int cntr = 0; cntr < coreCount - 1; cntr++) {
                    //System.out.println("cntr: "+cntr);
                    futures[cntr] = threadPool.submit(new MinerCallable(temp, temp + split));
                    temp = temp + split + 1;
                }

                futures[coreCount - 1] = threadPool.submit(new MinerCallable(temp, Integer.MAX_VALUE));

            }

        }
    }
}


