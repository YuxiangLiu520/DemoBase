package com.yxliu.demo.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;
import android.widget.VideoView;

import com.yxliu.demo.R;
import com.yxliu.demo.activity.base.BaseActivity;

import java.io.File;

public class MediaActivity extends BaseActivity {

    private VideoView mVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);

        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mVideoView != null) {
            mVideoView.suspend();
        }
    }

    private void init() {
        mVideoView = findViewById(R.id.video_view);

        if (ContextCompat.checkSelfPermission(MediaActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MediaActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            initVideoPath();
        }

        findViewById(R.id.btn_play).setOnClickListener(v -> {
            if (!mVideoView.isPlaying()) {
                mVideoView.start();
            }
        });

        findViewById(R.id.btn_pause).setOnClickListener(v -> {
            if (mVideoView.isPlaying()) {
                mVideoView.pause();
            }
        });

        findViewById(R.id.btn_replay).setOnClickListener(v -> {
            if (mVideoView.isPlaying()) {
                mVideoView.resume();
            }
        });
    }

    private void initVideoPath() {
        File file = new File(Environment.getExternalStorageDirectory(), "movie.mp4");
        mVideoView.setVideoPath(file.getPath());
        mVideoView.start();
//        mVideoView.setVideoPath(Environment.getExternalStorageDirectory().getAbsolutePath()+"/movie.mp4");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initVideoPath();
                } else {
                    Toast.makeText(this, "拒绝权限无法使用程序！", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
                break;
        }
    }
}
