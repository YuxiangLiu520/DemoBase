package com.yxliu.demo.activity;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.yxliu.demo.R;
import com.yxliu.demo.activity.base.BaseActivity;
import com.yxliu.demo.util.PermissionsUtils;

public class PermissionActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);

        String[] permissions = new String[]{Manifest.permission.WRITE_CALENDAR, Manifest.permission.READ_CALENDAR,Manifest.permission.WRITE_EXTERNAL_STORAGE};

        PermissionsUtils.IPermissionsResult permissionsResult = new PermissionsUtils.IPermissionsResult() {
            @Override
            public void passPermisson() {
                Toast.makeText(PermissionActivity.this,"权限通过，可以做其他事情！",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void forbitPermisson() {
                Toast.makeText(PermissionActivity.this,"权限不通过！",Toast.LENGTH_SHORT).show();
            }
        };

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionsUtils.getInstance().onRequestPermissionsResult(this,requestCode,permissions,grantResults);
    }
}
