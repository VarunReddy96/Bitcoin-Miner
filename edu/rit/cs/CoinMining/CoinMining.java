//import java.security.MessageDigest;
//import java.nio.ByteBuffer;
//
//public class CoinMining{
//
//    /**
//     *
//     * @param target
//     * @param blockData
//     * @return
//     */
//    public boolean mine(int target, int blockData){
//        int Nonce = Integer.MIN_VALUE;
//        MessageDigest md;
//        try{
//            md = MessageDigest.getInstance("SHA-256");
//        } catch (Exception e){
//            System.err.println(e.toString());
//            return false;
//        }
//        byte[] digest = new byte[8];
//        do {
//            Nonce ++;
//            md.digest(ByteBuffer.allocate(8).putInt(blockData).putInt(Nonce).array());
//            digest = md.digest();
//
//        } while(checkDigestToTarget(target, digest));
//        return true;
//    }
//
//    /**
//     *
//     * @param target
//     * @param digest
//     * @return
//     */
//    public boolean checkDigestToTarget(int target, byte[] digest){
//        ByteBuffer value = ByteBuffer.wrap(digest);
//        int digestInt = value.getInt();
//        return digestInt < target;
//    }
//
//}
