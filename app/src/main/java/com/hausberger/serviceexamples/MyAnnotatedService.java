package com.hausberger.serviceexamples;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

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
        doSomeStuff();
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: Thread name: " + Thread.currentThread().getName());
        return null;
    }

    @Background
    protected void doSomeStuff() {
        Log.d(TAG, "doSomeStuff: Thread name: " + Thread.currentThread().getName());
        try {
            Thread.sleep(12000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stopSelf();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: Thread name: " + Thread.currentThread().getName());
        super.onDestroy();
    }
}
