package com.hausberger.serviceexamples.foregroundservice;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.hausberger.serviceexamples.Constants;
import com.hausberger.serviceexamples.R;

public class MyForegroundService extends Service {

    private static final String TAG = MyForegroundService.class.getSimpleName() + "-->";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Service created");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");

        String input = intent.getStringExtra(Constants.ForegroundService.EXTRA);

        Intent activityIntent = new Intent(this, ForegroundServiceActivity_.class);
        activityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, activityIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        Notification notification = new NotificationCompat.Builder(this, Constants.ForegroundService.CHANNEL_ID)
                .setContentTitle("Example Service")
                .setContentText(input)
                .setSmallIcon(R.drawable.ic_account)
                .setColor(Color.GREEN)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);

        // do some async
        // call stopSelf()

        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Service destroyed");
    }
}
