package edu.rit.cs.CoinMining;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class MasterManager implements MinerListenerInterface{
    private String blockhash,targethash;
    MasterWriter mw;
    private int counter=0;
    private boolean istopped=false;
    MyTimer myTimer = new MyTimer("CurrentBlockID:"+"");

    public MasterManager(String blockhash,String targethash,MasterWriter mw){
        this.blockhash = blockhash;
        this.targethash = targethash;
        this.mw = mw;
    }

    public void putstop(){
        this.istopped = true;
    }

    public boolean getstop(){
        return this.istopped;
    }

    public void manage(){
        myTimer = new MyTimer("CurrentBlockID:"+this.blockhash);
        this.mw.sendchunks(this.blockhash,this.targethash);
        myTimer.start_timer();
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

    public void nonceFound(String nonce){
        if(counter < 9){
            myTimer.stop_timer();
            myTimer.print_elapsed_time();

            // found a new block
            this.blockhash = SHA256(this.blockhash+"|"+nonce);

            // update the target
            if(myTimer.get_elapsed_time_in_sec()<30)
                this.targethash = HexValueDivideBy(this.targethash, 2);
            else
                this.targethash = HexValueMultipleBy(this.targethash, 2);

            myTimer = new MyTimer("CurrentBlockID:"+this.blockhash);
            this.mw.sendchunks(this.blockhash,this.targethash);
            myTimer.start_timer();
            System.out.println("Sending chunks with counter "+ this.counter );
        }else{
            this.istopped = true;
            this.mw.closeconnection();
            System.exit(0);
        }
        this.counter++;

    }


}
