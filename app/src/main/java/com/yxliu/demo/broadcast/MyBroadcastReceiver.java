package com.yxliu.demo.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context,"receive in my broadcast",Toast.LENGTH_SHORT).show();

        abortBroadcast();//当发送的广播为有序广播时，调用此方法可以将此广播截断
    }
}
