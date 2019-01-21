package com.yxliu.demo.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.yxliu.demo.R;
import com.yxliu.demo.activity.base.BaseActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FilePersistenceActivity extends BaseActivity {

    private EditText mEtSaveDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_persistence);

        mEtSaveDate = findViewById(R.id.et_save_data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String inputText = load();
        if (!TextUtils.isEmpty(inputText)){
            mEtSaveDate.setText(inputText);
            mEtSaveDate.setSelection(inputText.length());
            Toast.makeText(FilePersistenceActivity.this,"Restoring succeeded",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        String inputText = mEtSaveDate.getText().toString();
        save(inputText);
    }

    private void save(String inputText) {
        FileOutputStream outputStream;
        BufferedWriter bufferedWriter = null;

        try {
            outputStream = openFileOutput("data",Context.MODE_PRIVATE);
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));

            bufferedWriter.write(inputText);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedWriter != null){
                try {
                    bufferedWriter.flush();
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String load(){
        FileInputStream inputStream;
        BufferedReader bufferedReader = null;
        StringBuilder content = new StringBuilder();

        try {
            inputStream = openFileInput("data");
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = bufferedReader.readLine()) != null){
                content.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (bufferedReader != null){
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return content.toString();
    }
}
