package com.softdesign.devintensive.ui.activities;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.softdesign.devintensive.R;
import com.softdesign.devintensive.utils.ConstantManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static String TAG = ConstantManager.TAG_PREFIX + "Main Activity";
    protected EditText mEditText;
    protected Button mRedBtn, mGreenBtn;
    protected int mColorMode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG,"onCreate");


        if (savedInstanceState==null){
            //активити было запущено впервые
        }else{
            //активити уже создавалось

            mColorMode = savedInstanceState.getInt(ConstantManager.COLOR_MODE_KEY);
            if(mColorMode==Color.RED){
                mEditText.setBackgroundColor(Color.RED);
            }else if (mColorMode == Color.GREEN){
                mEditText.setBackgroundColor(Color.GREEN);
            }
        }
    }



    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onResume");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG,"onRestart");
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "OnClick");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.d(TAG, "onSaveInstance");
        outState.putInt(ConstantManager.COLOR_MODE_KEY, mColorMode);
    }
}
