package com.kh.duel;

public class AttemptNum {

    private String mNumber;

    private int mHints;
    private int mHits;

    private boolean mVictory;

    public AttemptNum(String num, int hints, int hits) {
        mNumber = num;
        mHints = hints;
        mHits = hits;
    }



    public String getNumber() {
        return mNumber;
    }

    public int getHits() {
        return mHits;
    }

    public int getHints() {
        return mHints;
    }

    public boolean isVictory() {
        return mVictory;
    }

    public void setVictory(boolean victory) {
        mVictory = victory;
    }


}
