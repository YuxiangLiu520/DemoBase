package com.yxliu.demo.activity.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.yxliu.demo.R;
import com.yxliu.demo.activity.LoginActivity;
import com.yxliu.demo.util.ActivityCollector;

/**
 * 创建 yxliu
 * 时间 2018.11.11
 * 描述 BaseActivity
 */
public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity---";

    ForceOfflineReceive forceOfflineReceive;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, getClass().getSimpleName());//显示当前Activity
        ActivityCollector.addActivity(this);//收集创建的Activity

        initSystemBarTint(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //注册强制下线的广播
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.yxliu.demo.FORCE_OFFLINE");
        forceOfflineReceive = new ForceOfflineReceive();
        registerReceiver(forceOfflineReceive, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 取消注册的广播
        if (forceOfflineReceive != null){
            unregisterReceiver(forceOfflineReceive);
            forceOfflineReceive = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    /**
     * 强制下线的广播接收器
     */
    private class ForceOfflineReceive extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Warning");
            builder.setMessage("You are forced to be offline. Please try to login again.");
            builder.setCancelable(false);
            builder.setPositiveButton("OK", (dialog, which) -> {
                ActivityCollector.finishAll();//销毁所有活动
                Intent backIntent = new Intent(context,LoginActivity.class);
                context.startActivity(backIntent);//重新启动LoginActivity
            });
            builder.show();
        }
    }

    /**
     * 设置状态栏颜色
     */
    protected void initSystemBarTint(boolean isSystemBarTranslucent) {
        if (isSystemBarTranslucent) {
            // 设置状态栏全透明
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
            return;
        }
        // 沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT &&
                Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintColor(getDarkColorPrimary());
        }
    }

    /**
     * 获取主题色
     *
     * @return
     */
    public int getColorPrimary() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }

    /**
     * 获取深主题色
     *
     * @return
     */
    public int getDarkColorPrimary() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValue, true);
        return typedValue.data;
    }

    /**
     * 初始化tooBar
     *
     * @param toolbar         状态栏
     * @param homeAsUpEnabled home
     * @param title           标题
     */
    public void initToolBar(Toolbar toolbar, boolean homeAsUpEnabled, String title) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(homeAsUpEnabled);
    }

    public void initToolBar(Toolbar toolbar, boolean homeAsUpEnabled, int resTitle) {
        initToolBar(toolbar, homeAsUpEnabled, getString(resTitle));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
