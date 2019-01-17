package com.yxliu.demo.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.yxliu.demo.R;
import com.yxliu.demo.activity.base.BaseActivity;
import com.yxliu.demo.util.PermissionsUtils;

import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends BaseActivity {

    ArrayAdapter<String> adapter;

    List<String> contactList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        ListView contactView = findViewById(R.id.lv_contact_view);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contactList);
        contactView.setAdapter(adapter);

        String[] permissions = new String[]{Manifest.permission.READ_CONTACTS};
        PermissionsUtils.getInstance().checkPermissions(this, permissions, new PermissionsUtils.IPermissionsResult() {
            @Override
            public void passPermisson() {
                readContact();
            }

            @Override
            public void forbitPermisson() {
                Toast.makeText(ContactActivity.this, "没添加权限", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void readContact() {
        Cursor cursor;

        cursor = getContentResolver().query(ContactsContract.CommonDataKinds
                .Phone.CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                contactList.add("\n" + displayName + "\n" + number + "\n");
            }
            adapter.notifyDataSetChanged();
            cursor.close();
        }
    }
}
