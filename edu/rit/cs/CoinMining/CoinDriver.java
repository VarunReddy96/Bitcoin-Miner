public class CoinDriver{
    public static void main(String[] args){
        System.out.println(args[0]);
        int target = Integer.parseInt(args[0]);
        CoinMining miner = new CoinMining();
        miner.mine(target, 14050);
    }
}
