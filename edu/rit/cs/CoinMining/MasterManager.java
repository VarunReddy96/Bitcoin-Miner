package edu.rit.cs.CoinMining;

public class MasterManager implements MinerListenerInterface{
    private String blockhash,targethash;
    MasterWriter mw;
    private int counter=0;
    private boolean istopped=false;
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
        this.mw.sendchunks(this.blockhash,this.targethash);
    }

    public void nonceFound(){
        this.counter++;
        if(counter<=10){
            this.mw.sendchunks(this.blockhash,this.targethash);
        }else{
            this.istopped = true;
            this.mw.closeconnection();
        }

    }


}
