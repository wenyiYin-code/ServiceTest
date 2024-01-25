package com.example.servicetest;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class MyService extends Service {

    private DownloadBinder mBinder = new DownloadBinder();

    class DownloadBinder extends Binder {
        public void startDownload() {
            Log.d("MyService", "startDownload executed");
        }

        public int getProgress() {
            Log.d("MyService", "getProgress executed");
            return 0;
        }
    }

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return mBinder;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        Log.d("MyService", "onCreate executed");
        //创建notificationManager对通知进行管理
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            String channelId = "001";//通知渠道的标识符
            CharSequence channelName = "QQ";//通知渠道的位置
            String channelDescription = "来自QQ好友的消息";//通知渠道的描述

            //通知渠道的级别
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            //创建通知渠道
            NotificationChannel notificationChannel = new NotificationChannel(channelId,channelName,importance);
            notificationChannel.setDescription(channelDescription);

            //系统中注册消息
            notificationManager.createNotificationChannel(notificationChannel);
        }

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);

        //创建通知
        Notification notification = new NotificationCompat.Builder(this,"001")
                .setContentTitle("QQ消息")//消息标题
                .setContentText("你好，我是xx")//消息内容
                .setWhen(System.currentTimeMillis())//指定通知被通知创建的时间
                .setSmallIcon(R.mipmap.ic_launcher)//通知的小图标
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))//通知的大图标
                .setContentIntent(pi)
                .build();
        //显示一个通知
        startForeground(1, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {//服务启动时调用
        Log.d("MyService", "onStartCommand executed");
        return super.onStartCommand(intent,flags,startId);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d("MyService", "onDestroy executed");
    }
}