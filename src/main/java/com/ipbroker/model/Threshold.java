package com.ipbroker.model;

public class Threshold {
    private int INITIAL_LIMIT = 0;
    public  int LIMIT = 0;
    public  int TIME = 0;

     public  Threshold(int limit, int time) {
        this.INITIAL_LIMIT = limit;
        this.LIMIT = limit;
        this.TIME = time;
    }

    public void decreaseLimit() {
        this.LIMIT--;
    }
    public void reset() {
        this.LIMIT = this.INITIAL_LIMIT;
    }
    
    
}
