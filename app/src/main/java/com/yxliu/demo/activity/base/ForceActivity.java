package com.yxliu.demo.activity.base;

import android.content.Intent;
import android.os.Bundle;

import com.yxliu.demo.R;

public class ForceActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_force);

        findViewById(R.id.btn_force_offline).setOnClickListener(v -> {
            Intent intent = new Intent("com.yxliu.demo.FORCE_OFFLINE");
            sendBroadcast(intent);//发送标准广播
        });
    }
}
