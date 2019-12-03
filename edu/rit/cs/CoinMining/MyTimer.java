package edu.rit.cs.CoinMining;

public class MyTimer {
    private String timerName;
    private String hashname;
    private long startTime;
    private long endTime;
    private long elapsedTime;

    public MyTimer(String timerName, String name){
        this.timerName = timerName;
        this.hashname = name;
    }

    public void start_timer(){
        this.startTime = System.nanoTime();
    }

    public void stop_timer(){
        this.endTime = System.nanoTime();
    }

    public void print_elapsed_time(){
        this.elapsedTime = this.endTime - this.startTime;
        double elapsedTimeInSecond = (double) this.elapsedTime / 1_000_000_000;
        System.out.println("ElapsedTime (" + this.timerName + "): "+ elapsedTimeInSecond + " seconds"+" "+this.hashname);
    }

    public double get_elapsed_time_in_sec() {
        return (double) this.elapsedTime / 1_000_000_000;
    }

}
