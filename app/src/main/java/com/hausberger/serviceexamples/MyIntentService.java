package com.hausberger.serviceexamples;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import org.androidannotations.annotations.EService;

@EService
public class MyIntentService extends IntentService {

    private static final String TAG = MyIntentService.class.getSimpleName() + "-->";

    public MyIntentService() {
        super("MyBackgroundThread");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(TAG, "onHandleIntent. Thread name: " + Thread.currentThread().getName());

        int i = 1;

        while (i < 12) {
            try {
                Thread.sleep(1000);
                Log.d(TAG, "Time: " + String.valueOf(i) + " sec.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i++;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate. Thread name: " + Thread.currentThread().getName());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy. Thread name: " + Thread.currentThread().getName());
    }
}
