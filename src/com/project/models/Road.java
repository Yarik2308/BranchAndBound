package com.project.models;

public class Road {
    private int In;
    private int Out;
    private int zeroScore;

    public Road(int in, int out){
        this.In = in;
        this.Out = out;
    }

    public int getIn() {
        return In;
    }

    public int getOut() {
        return Out;
    }

    public int getZeroScore() {
        return zeroScore;
    }

    public void setZeroScore(int zeroScore) {
        this.zeroScore = zeroScore;
    }
}
