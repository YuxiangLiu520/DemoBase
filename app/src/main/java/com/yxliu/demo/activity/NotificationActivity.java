package com.yxliu.demo.activity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;

import com.yxliu.demo.R;
import com.yxliu.demo.activity.base.BaseActivity;

public class NotificationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        init();
    }

    //    @SuppressLint("InlinedApi")
    private void init() {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        //id:随便写 name:随便写
        NotificationChannel channel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channel = new NotificationChannel("com.yxliu.demo", "yxliu", NotificationManager.IMPORTANCE_HIGH);

            channel.setBypassDnd(true);    //设置绕过免打扰模式
            channel.canBypassDnd();       //检测是否绕过免打扰模式
            channel.setLockscreenVisibility(Notification.VISIBILITY_SECRET);//设置在锁屏界面上显示这条通知
            channel.setDescription("description of this notification");
            channel.setLightColor(Color.GREEN);
            channel.setName("name of this notification");
            channel.setShowBadge(true);
            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            channel.enableVibration(true);
            manager.createNotificationChannel(channel);
        }

        findViewById(R.id.btn_send_notification).setOnClickListener(v -> {

            Intent intent = new Intent(NotificationActivity.this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(NotificationActivity.this, 0, intent, 0);

            Notification notification;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                notification = new Notification.Builder(this)
                        .setContentTitle("New Message from ")
                        .setContentText("Hello world!")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_notification))
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setChannelId("com.yxliu.demo")
                        .setStyle(new Notification.BigTextStyle().bigText("public Notification.Builder setStyle (Notification.Style style)\n" +
                                "Add a rich ic_notification style to be applied at build time."))
    //                    .setStyle(new Notification.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_notification_big)))
                        .build();
            }else {
                notification = new Notification.Builder(this)
                        .setContentTitle("New Message from ")
                        .setContentText("Hello world!")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_notification))
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setStyle(new Notification.BigTextStyle().bigText("public Notification.Builder setStyle (Notification.Style style)\n" +
                                "Add a rich ic_notification style to be applied at build time."))
                        //                    .setStyle(new Notification.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_notification_big)))
                        .build();
            }

            manager.notify(1, notification);
        });
    }
}
