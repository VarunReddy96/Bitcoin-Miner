package edu.rit.cs.CoinMining;

import mpi.MPI;
import mpi.*;
import mpi.MPIException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;


public class MPI_Miner {



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
        for(nonce=0; nonce<=100; nonce++) {
            tmp_hash = SHA256(SHA256(blockHash+String.valueOf(nonce)));
            if(targetHash.compareTo(tmp_hash)>0)
                break;
        }
//        System.out.println("Resulting Hash: " + tmp_hash);
//        System.out.println("Nonce:" + nonce);
        return nonce;
    }

    public static void main(String[] args) {
        MPI.Init(args);
        int rank = MPI.COMM_WORLD.getRank(), size = MPI.COMM_WORLD.getSize();
        int chunksize = Integer.MAX_VALUE-Integer.MIN_VALUE / size;
        int sendbuffstart[] = new int[size];
        int sendbuffstop[] = new int[size];
        int rcvstart[] = new int[1];
        int rcvstop[] = new int[1];

        int temp = Integer.MIN_VALUE;
        for (int m = 0; m < size - 1; m++) {
            sendbuffstart[m] = temp;
            sendbuffstop[m] = temp + chunksize;
            temp = temp + chunksize;

        }
        sendbuffstart[size - 1] = temp;
        sendbuffstop[size - 1] = Integer.MAX_VALUE;
        MPI.COMM_WORLD.scatter(sendbuffstart, 1, MPI.INT, rcvstart, 1, MPI.INT, 0);
        MPI.COMM_WORLD.scatter(sendbuffstop, 1, MPI.INT, rcvstop, 1, MPI.INT, 0);
        boolean noncefound = false;
        String tmp_hash="undefined";
        // omp parallel for
        for(int i = rcvstart[0]; i<=rcvstop[0]; i++) {
            if (!noncefound) {
                tmp_hash = SHA256(SHA256(inputhash + String.valueOf(nonce)));
                if (targethash.compareTo(tmp_hash) > 0) {
                    // omp critical
                    {
                        //this.notifier.nonceFound(nonce, InetAddress.getLocalHost(), 100);
                        long end = System.currentTimeMillis();
                        System.out.println("Nonce found: " + nonce + " Time taken(ms): " + (end - start));
                        noncefound = true;
                    }
                }
            }else {
                break;
            }
        }

    }
}
