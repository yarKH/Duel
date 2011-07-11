package com.kh.duel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import android.util.Log;

public class NumSolver {

    private static final String TAG = "Duel:NumSolver";

    private static int NUM_LENGTH = 4;

    private String mCompNum;
    private String mUserNum;

    private ArrayList<AttemptNum>  mCompMoves;
    private ArrayList<Integer>     mVarDigits;
    private ArrayList<String>      mVarMoves;

    public NumSolver(String userNum) {
        mCompNum   = newRandom();
        mUserNum   = userNum;
        mCompMoves = new ArrayList<AttemptNum>();
        initVarMoves();
        printVarMoves();

        Log.i(TAG, "NumSolver created, comp = " + mCompNum + ", user = " + mUserNum);
    }

    public void reset() {
        mUserNum = newRandom();
        mCompMoves.clear();
        mVarMoves.clear();
        initVarMoves();
    }

    public String getCompNum() {
        return mCompNum;
    }

    public String getUserNum() {
        return mUserNum;
    }

    public static String newRandom() {

        String output = "";
        Random rnd = new Random();
        while(output.length() < NUM_LENGTH) {
            String num = String.valueOf(rnd.nextInt(10));
            if (!output.contains(num)) {
                output = output.concat(num);
            }
        }
        Log.i(TAG, "newRandom " + output);
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
        Log.i(TAG, "check " + num + "hints " + hints + " hits " + hits);
        AttemptNum aNum = new AttemptNum(num, hints, hits);
        if (hits == NUM_LENGTH) {
            aNum.setVictory(true);
        }
        return aNum;
    }

    private String getVarRandom() {

        Random rnd = new Random();
        int pos = rnd.nextInt(mVarMoves.size());

        String output = mVarMoves.get(pos);
        Log.i(TAG, "getVarRandom " + output);

        return output;
    }

    public AttemptNum nextMove() {
        Log.i(TAG, "nextMove =============");

        String sNum = getVarRandom();
        AttemptNum aNum = check(sNum, mUserNum);
        mCompMoves.add(aNum);
        if (!aNum.isVictory()) {
            filterVarMoves(aNum);
            printVarMoves();
            //filterDigits(aNum);
        }

        return aNum;

    }

    private void filterDigits(AttemptNum aNum) {
        int hints =   aNum.getHints();
        int hits =    aNum.getHits();
        String move = aNum.getNumber();

        switch (hints) {
        case 0:

            break;
        }

    }

    private void filterVarMoves(AttemptNum aNum) {
        Log.i(TAG, "filterVarMap");

        int hints =   aNum.getHints();
        int hits =    aNum.getHits();
        String move = aNum.getNumber();

        mVarMoves.remove(move);

        Set<String> listToRemove = new HashSet<String>();

        switch (hints) {
        case 0:
            for (String varMove : mVarMoves) {
                for (int i = 0; i < NUM_LENGTH; i++) {
                    if (varMove.contains(move.substring(i, i + 1))) {
                        listToRemove.add(varMove);
                        break;
                    }
                }
            }
            break;

        case 1:
            for (String varMove : mVarMoves) {
                for (int i = 0; i < NUM_LENGTH; i++) {
                    for (int j = i + 1; j < NUM_LENGTH; j++) {
                        if (varMove.contains(move.substring(i, i + 1)) && varMove.contains(move.substring(j, j + 1))) {
                            listToRemove.add(varMove);
                        }
                    }
                    if (hits == 0 && (varMove.charAt(i) == move.charAt(i))) {
                        listToRemove.add(varMove);
                    }
                }
            }
            break;

        case 2:
            for (String varMove : mVarMoves) {
                for (int i = 0; i < NUM_LENGTH; i++) {
                    for (int j = i + 1; j < NUM_LENGTH; j++) {
                        for (int k = j + 1; k < NUM_LENGTH; k++) {
                            if (varMove.contains(move.substring(i, i + 1)) && varMove.contains(move.substring(j, j + 1))
                                    && varMove.contains(move.substring(k, k + 1))) {
                                listToRemove.add(varMove);
                            }
                        }
                    }
                    if (hits == 0 && (varMove.charAt(i) == move.charAt(i))) {
                        listToRemove.add(varMove);
                    }
                }
            }
            break;

        case 3:
            for (String varMove : mVarMoves) {
                if (varMove.contains(move.substring(0, 1)) && varMove.contains(move.substring(1, 2))
                        && varMove.contains(move.substring(2, 3)) && varMove.contains(move.substring(3, 4))) {
                    listToRemove.add(varMove);
                }
                for (int i = 0; hits == 0 && i < NUM_LENGTH; i++) {
                    if (varMove.charAt(i) == move.charAt(i)) {
                        listToRemove.add(varMove);
                    }
                }
            }
            break;
        case 4:
            if (hits == 0) {
                for (String varMove : mVarMoves) {
                    for (int i = 0; i < NUM_LENGTH; i++) {
                        if (varMove.charAt(i) == move.charAt(i)) {
                            listToRemove.add(varMove);
                        }
                    }
                }
            }
            break;
        }
        Log.i(TAG, "listToRemove " +  listToRemove.size());
        mVarMoves.removeAll(listToRemove);
    }

    private void initVarMoves() {
        Log.i(TAG, "initVarMoves");

        mVarMoves = new  ArrayList<String>();

        for (int pos1 = 0; pos1 < 10; pos1++) {
            for (int pos2 = 0; pos2 < 10; pos2++) {
                if (pos1 == pos2) {
                    continue;
                }
                for (int pos3 = 0; pos3 < 10; pos3++) {
                    if (pos1 == pos3 || pos2 == pos3) {
                        continue;
                    }
                    for (int pos4 = 0; pos4 < 10; pos4++) {
                        if (pos1 == pos4 || pos2 == pos4 || pos3 == pos4) {
                            continue;
                        }
                        mVarMoves.add("" + pos1 + pos2 + pos3 + pos4);
                    }
                }
            }
        }
    }

    public void printVarMoves() {
        Log.i(TAG, "printVarMoves size " + mVarMoves.size());
        /*if (mVarMoves.size() < 20 && mVarMoves.size() !=0) {
            for (String num : mVarMoves) {
                Log.e(TAG, "num " + num );
            }
        }*/
    }

    public static void printAttemptNum(AttemptNum aNum) {
        Log.i(TAG, "printAttemptNum num " + aNum.getNumber() + " hints " + aNum.getHints() + " hits " + aNum.getHits());
    }

    public void printCompMoves() {
        Log.e(TAG, "=========== ");
        Log.e(TAG, "NUM " + mUserNum);
        Log.e(TAG, "--- ");
        for (AttemptNum aNum : mCompMoves) {
            Log.e(TAG, aNum.getNumber() + " [" + aNum.getHints() + "," + aNum.getHits() + "] " );
        }
        Log.e(TAG, "=========== ");
    }




}
