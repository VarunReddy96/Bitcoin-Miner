package edu.rit.cs.CoinMining;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.concurrent.Callable;

/**
 * This class is used to perform the mining operations for each task.
 *
 */

public class MinerCallable implements Callable<Integer> {

    private int start, end;
    private String block, targetHash;
    private MinerNotifierInterface notifier;
    private ThreadPoolManager manager;

    public MinerCallable(String block, String targetHash, int start, int end,ThreadPoolManager manager){
        this.start = start;
        this.end = end;
        this.block = block;
        this.targetHash = targetHash;
        this.notifier = notifier;
        this.manager = manager;
    }

    public MinerCallable(int start, int end){
        this.start = start;
        this.end = end;
        this.block = block;
        this.targetHash = targetHash;
        this.notifier = notifier;
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

    /**
     * get a randomized target hash
     * @return randomized target hash
     */
    public static String getTargetHash() {
        Random rand = new Random();
        int randInt = rand.nextInt(1000);
        return SHA256(String.valueOf(randInt));
    }

    /**
     * perform the proof-of-work
     * @return nonce (a 32-bit integer) that satisfies the requirements
     */
    public int pow() {
        int nonce=0;
        String tmp_hash="undefined";
        for(nonce=start; nonce<=end && this.manager.isTest() && !Thread.currentThread().isInterrupted(); nonce++) {
            tmp_hash = SHA256(SHA256(this.block+String.valueOf(nonce)));
            if(targetHash.compareTo(tmp_hash)>0 || Thread.currentThread().isInterrupted()) {
                break;
            }

        }
        return nonce;
    }

    /**
     * Implementation of call that is required by callable. It calls pow().
     *
     * @return the return value from pow. 
     */
    public Integer call(){
        return pow();
    }
}
