package com.kh.duel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Random;

import android.util.Log;

public class NumSolver {

    private static final String TAG = "Duel:NumSolver";

    private int NUM_LENGTH = 4;

    private String mCompNum;
    private String mUserNum;

    private ArrayList<AttemptNum>  mCompMoves;
    private ArrayList<Integer>     mVarDigits;
    private ArrayList<String>      mVarMoves;

    public NumSolver(String userNum) {
        mCompNum   = newRandom();
        mUserNum   = userNum;
        mCompMoves = new ArrayList<AttemptNum>();
        initVarMap();

        Log.i(TAG, "NumSolver created, comp = " + mCompNum + ", user = " + mUserNum);
    }

    public String getCompNum() {
        return mCompNum;
    }

    public String getUserNum() {
        return mUserNum;
    }

    private String newRandom() {
        String output = "";
        Random rnd = new Random();
        while(output.length() < NUM_LENGTH) {
            String num = String.valueOf(rnd.nextInt(10));
            if (!output.contains(num)) {
                output = output.concat(num);
            }
        }
        return output;
    }

    public AttemptNum check(String num, String compNum) {

        int hints = 0;
        int hits = 0;

        for (int i = 0; i < NUM_LENGTH; i++) {
            if (compNum.contains(String.valueOf(num.charAt(i)))) {
                hints++;
                if (compNum.charAt(i) == num.charAt(i)) {
                    hits++;
                }
            }
        }

        AttemptNum aNum = new AttemptNum(num, hints, hits);
        if (hits == NUM_LENGTH) {
            aNum.setVictory(true);
        }
        return aNum;
    }

    private String getVarRandom() {

        Random rnd = new Random();
        int pos = rnd.nextInt(mVarMoves.size());

        return mVarMoves.get(pos);
    }


    public AttemptNum nextMove_test() {
        String sNum = newRandom();
        AttemptNum aNum = check(sNum, mCompNum);
        mCompMoves.add(aNum);
        return aNum;
    }



    public AttemptNum nextMove() {

        String sNum = getVarRandom();
        AttemptNum aNum = check(sNum, mCompNum);
        mCompMoves.add(aNum);
        filterVarMap(aNum);

        return aNum;

    }

    private void filterVarMap(AttemptNum aNum) {
        int hints =   aNum.getHints();
        int hits =    aNum.getHits();
        String move = aNum.getNumber();

        if (aNum.isVictory()) {
            return;
        }

        removeDigits(move, hints, hits);
    }

    private void removeDigits(String move, int hints, int hits) {
        if (hints == 0) {
            for (String varMove : mVarMoves) {
                for (int i = 0; i < NUM_LENGTH; i++) {
                    if (varMove.contains(move.substring(i, i))) {
                        mVarMoves.remove(varMove);
                        break;
                    }
                }
            }
        } else if (hints == 1) {
            for (String varMove : mVarMoves) {
                for (int i = 0; i < NUM_LENGTH; i++) {
                    for (int j = i + 1; j < NUM_LENGTH; j++) {
                        if (varMove.contains(move.substring(i, i)) && varMove.contains(move.substring(j, j))) {
                            mVarMoves.remove(varMove);
                        }
                    }
                    if (hits ==0 && (varMove.charAt(i) == move.charAt(i))) {
                        mVarMoves.remove(varMove);
                    }
                }
            }
        } else if (hints == 2) {
            for (String varMove : mVarMoves) {
                for (int i = 0; i < NUM_LENGTH; i++) {
                    for (int j = i + 1; j < NUM_LENGTH; j++) {
                        for (int k = j + 1; k < NUM_LENGTH; k++) {
                            if (varMove.contains(move.substring(i, i)) && varMove.contains(move.substring(j, j))
                                    && varMove.contains(move.substring(k, k))) {
                                mVarMoves.remove(varMove);
                            }
                        }
                    }
                    if (hits ==0 && (varMove.charAt(i) == move.charAt(i))) {
                        mVarMoves.remove(varMove);
                    }
                }
            }
        } else if (hints == 3) {
            for (String varMove : mVarMoves) {
                if (varMove.contains(move.substring(0, 0)) && varMove.contains(move.substring(1, 1))
                        && varMove.contains(move.substring(2, 2)) && varMove.contains(move.substring(3, 3))) {
                    mVarMoves.remove(varMove);
                }
                for (int i = 0; hits ==0 && i < NUM_LENGTH; i++) {
                    if (varMove.charAt(i) == move.charAt(i)) {
                        mVarMoves.remove(varMove);
                    }
                }
            }
        }
    }

    private void initVarMap() {
        String varNum;
        for (int pos1 = 0; pos1 < NUM_LENGTH; pos1++) {
            varNum = "" + pos1;
            for (int pos2 = 0; pos2 < NUM_LENGTH; pos2++) {
                if (pos1 == pos2) continue;
                varNum += pos2;
                for (int pos3 = 0; pos3 < NUM_LENGTH; pos3++) {
                    if (pos1 == pos3 || pos2 == pos3) continue;
                    varNum += pos3;
                    for (int pos4 = 0; pos4 < NUM_LENGTH; pos4++) {
                        if (pos1 == pos4 || pos2 == pos4 || pos3 == pos4) continue;
                        varNum += pos4;
                        mVarMoves.add(varNum);
                    }
                }
            }
        }
    }




}
