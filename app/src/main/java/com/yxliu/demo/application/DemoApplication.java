package com.yxliu.demo.application;

import android.app.Application;
import android.util.Log;


public class DemoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d("DemoApplication---","onCreate");
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
}
