package com.hausberger.serviceexamples.boundservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class MyBoundService extends Service {

    private static final String TAG = MyBoundService.class.getSimpleName();

    private IBinder binder;
    private Handler handler;
    private int progress;
    private int maxValue;
    private Boolean isPaused;

    @Override
    public void onCreate() {
        super.onCreate();

        binder = new MyBinder();
        handler = new Handler();
        maxValue = 5000;
        isPaused = true;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    private void startPretendLongRunningTask() {
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (progress >= maxValue || isPaused) {
                    Log.d(TAG, "run: removing callbacks.");
                    handler.removeCallbacks(this);
                    pauseLongRunningTask();
                } else {
                    Log.d(TAG, "run: progress: " + progress);
                    progress += 100;
                    handler.postDelayed(this, 100);
                }
            }
        };
        handler.postDelayed(runnable, 100);
    }

    public void pauseLongRunningTask() {
        isPaused = true;
    }

    public void unPauseLongRunningTask() {
        isPaused = false;
        startPretendLongRunningTask();
    }

    public int getProgress() {
        return progress;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public Boolean getPaused() {
        return isPaused;
    }

    public void resetTask() {
        progress = 0;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        stopSelf();
    }

    public class MyBinder extends Binder {

        MyBoundService getService() {
            return MyBoundService.this;
        }
    }
}
