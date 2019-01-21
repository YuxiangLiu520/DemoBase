package com.yxliu.demo.application;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import org.litepal.LitePal;


public class DemoApplication extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("DemoApplication---","onCreate");

        mContext = getApplicationContext();
        LitePal.initialize(mContext);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        Log.d("DemoApplication---","onTerminate");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();

        Log.d("DemoApplication---","onLowMemory");
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);

        Log.d("DemoApplication---","onTrimMemory");
    }

    public static Context getContext(){
        return mContext;
    }
}
