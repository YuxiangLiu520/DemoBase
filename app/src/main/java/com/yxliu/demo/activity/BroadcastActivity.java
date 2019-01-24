package com.yxliu.demo.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import com.yxliu.demo.R;
import com.yxliu.demo.activity.base.BaseActivity;

public class BroadcastActivity extends BaseActivity {

    private IntentFilter intentFilter;

    private NetworkChangeReceiver networkChangeReceiver;

    private LocalReceiver localReceiver;

    private LocalBroadcastManager localBroadcastManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);

        intentFilter = new IntentFilter();

        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver, intentFilter);

        //点击发送自定义广播
        findViewById(R.id.btn_send_broadcast).setOnClickListener(v -> {
            Intent intent = new Intent("com.yxliu.demo.broadcast.MY_BROADCAST");
//            sendBroadcast(intent);//标准广播
            sendOrderedBroadcast(intent,null);//有序广播
        });

        localBroadcastManager = LocalBroadcastManager.getInstance(this);//获取实例
        findViewById(R.id.btn_send_local_broadcast).setOnClickListener(v -> {
            Intent intent = new Intent("com.yxliu.demo.broadcast.LOCAL_BROADCAST");
//            sendBroadcast(intent);
            localBroadcastManager.sendBroadcast(intent);//发送本地广播
        });
        intentFilter.addAction("com.yxliu.demo.broadcast.LOCAL_BROADCAST");
        localReceiver = new LocalReceiver();
        localBroadcastManager.registerReceiver(localReceiver,intentFilter);//注册本地广播监听
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);
        unregisterReceiver(localReceiver);
    }

    private class NetworkChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                Toast.makeText(context, "network is connect", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "network is not connect", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class LocalReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context,"receive local broadcast",Toast.LENGTH_SHORT).show();
        }
    }
}
