package edu.rit.cs.CoinMining;

public interface MinerNotifierInterface {

    public void addListener(MinerListenerInterface listener);

    public void foundNonce();
}
