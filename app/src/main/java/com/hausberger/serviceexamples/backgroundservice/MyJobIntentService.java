package com.hausberger.serviceexamples.backgroundservice;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;
import android.util.Log;
import android.widget.Toast;

import com.hausberger.serviceexamples.Constants;

public class MyJobIntentService extends JobIntentService {

    private static final String TAG = MyJobIntentService.class.getSimpleName() + "-->";

    public static void enqueueWork(Context context, Intent intent) {
        enqueueWork(context, MyJobIntentService.class, Constants.MY_JOB_INTENT_ID, intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "Task execution started", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Log.d(TAG, "onHandleWork. Thread name: " + Thread.currentThread().getName());

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
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Task execution finished", Toast.LENGTH_SHORT).show();
    }
}
