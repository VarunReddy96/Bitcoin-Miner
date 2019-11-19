package edu.rit.cs.CoinMining;

import java.util.concurrent.Callable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * 
 */
public class MinerCallable implements Callable<Integer> {

    private int start, end;
    private String block, targetHash;
    private MinerNotifierInterface notifier;

    public MinerCallable(String block, String targetHash, int start, int end){
        this.start = start;
        this.end = end;
        this.block = block;
        this.targetHash = targetHash;
        this.notifier = notifier;
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
        String blockHash = SHA256("CSCI-654 Foundations of Parallel Computing");
        String targetHash = "0000092a6893b712892a41e8438e3ff2242a68747105de0395826f60b38d88dc";

        //System.out.println("Performing Proof-of-Work...wait...");
        int nonce=0;
        String tmp_hash="undefined";
        for(nonce=start; nonce<=end; nonce++) {
            tmp_hash = SHA256(SHA256(blockHash+String.valueOf(nonce)));
            if(targetHash.compareTo(tmp_hash)>0 || Thread.currentThread().isInterrupted())
                break;
        }
//        System.out.println("Resulting Hash: " + tmp_hash);
//        System.out.println("Nonce:" + nonce);
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
