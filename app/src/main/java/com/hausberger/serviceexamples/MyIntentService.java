package com.hausberger.serviceexamples;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

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

        Intent localIntent =  new Intent(Constants.MY_INTENT);
        localIntent.putExtra(Constants.RESULT, sec);
        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "Task execution starts", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onCreate. Thread name: " + Thread.currentThread().getName());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Task execution finishes", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onDestroy. Thread name: " + Thread.currentThread().getName());
    }
}
