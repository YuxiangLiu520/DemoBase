package com.yxliu.demo.activity;

import android.os.Bundle;
import android.util.Log;

import com.yxliu.demo.R;
import com.yxliu.demo.activity.base.BaseActivity;

public class WelcomeActivity extends BaseActivity {

    private static String TAG = "WelcomeActivity---";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Log.d(TAG,"onCreate");
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


}
