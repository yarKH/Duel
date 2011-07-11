package com.kh.duel;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

public class NumDuel extends Activity  {
    /** Called when the activity is first created. */

    View mEditDigit;
    LinearLayout mNumPad;
    ArrayAdapter<String> mAdapter;
    NumSolver mSolver;
    EditText edit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mNumPad = (LinearLayout)findViewById(R.id.num_pad);
        //mNumPad.setVisibility(View.GONE);

        final String[] COUNTRIES = new String[] {
            "1234 - [2,1]", "4567 - [3,0]", "7524 - [0,0]", "4383 - [3,1]", "4567 - [0,0]", "4967 - [0,2]"};
        final String[] COUNTRIES1 = new String[] {""};

        ListView lv1 = (ListView)findViewById(R.id.list1);
        ListView lv2 = (ListView)findViewById(R.id.list2);

        mAdapter = new ArrayAdapter<String>(this, R.layout.list_item_main, COUNTRIES1);

        lv1.setAdapter(mAdapter);
        lv2.setAdapter(new ArrayAdapter<String>(this, R.layout.list_item_main, COUNTRIES));

        //mSolver = new NumSolver();

        edit = (EditText) findViewById(R.id.input_text);

    }

    public void onNumClick(View view) {
        Log.i("LOG", "onClick");
        if (edit.getText().length() > 3) {
            //Log.i("LOG", mSolver.check(edit.getText().toString()));
        }



    }

    public void onDigitClick(View view) {
        if (mEditDigit == view) {
            view.setBackgroundColor(Color.WHITE);
            mNumPad.setVisibility(View.GONE);
            mEditDigit = null;
        } else {
            mNumPad.setVisibility(View.VISIBLE);
            view.setBackgroundColor(Color.YELLOW);
            if (mEditDigit != null) {
                mEditDigit.setBackgroundColor(Color.WHITE);
            }
            mEditDigit = view;
        }
    }


}