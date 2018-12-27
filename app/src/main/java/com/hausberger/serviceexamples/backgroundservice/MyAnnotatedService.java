package com.hausberger.serviceexamples.backgroundservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.hausberger.serviceexamples.Constants;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EService;

@EService
public class MyAnnotatedService extends Service {

    private static final String TAG = MyAnnotatedService.class.getSimpleName() + "-->";

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: Thread name: " + Thread.currentThread().getName());
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: Thread name: " + Thread.currentThread().getName());
        doSomeStuff(intent, startId);
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: Thread name: " + Thread.currentThread().getName());
        return null;
    }

    @Background
    protected void doSomeStuff(Intent intent, int startId) {
        Log.d(TAG, "doSomeStuff: Thread name: " + Thread.currentThread().getName() + "close Id: " + String.valueOf(startId));

        int duration = intent.getIntExtra(Constants.SERVICE_DURATION, 0);
        int sec = 1;

        while (sec < duration) {
            try {
                Thread.sleep(1000);
                Log.d(TAG, "Time: " + String.valueOf(sec) + " sec.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sec++;
        }

        stopSelf(startId);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: Thread name: " + Thread.currentThread().getName());
        super.onDestroy();
    }
}
