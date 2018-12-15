package com.hausberger.serviceexamples;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import org.androidannotations.annotations.EService;

/**
 * This Service runs oly one thread. So use IntentService instead of this.
 */
@EService
public class HelloService extends Service {

    private static final String TAG = HelloService.class.getSimpleName();

    private Looper serviceLooper;
    private ServiceHandler serviceHandler;

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate. Thread name: " + Thread.currentThread().getName());
        // Start up the thread running the service. Note that we create a
        // separate thread because the service normally runs in the process's
        // main thread, which we don't want to block. We also make it
        // background priority so CPU-intensive work doesn't disrupt our UI.
        HandlerThread thread = new HandlerThread("ServiceStartArgument", Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();

        // Get the HandlerThread's Looper and use it for our Handler
        serviceLooper = thread.getLooper();
        serviceHandler = new ServiceHandler(serviceLooper);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand. Thread name: " + Thread.currentThread().getName());
        // For each start request, send a message to start a job and deliver the
        // start ID so we know which request we're stopping when we finish the job
        Message msg = serviceHandler.obtainMessage();
        msg.arg1 = startId;
        msg.obj = intent;
        serviceHandler.sendMessage(msg);

        // If we get killed, after returning from here, restart
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // We don't provide binding, so return null
        return null;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy. Thread name: " + Thread.currentThread().getName());
        serviceLooper.quit();
    }

    // Handler that receives messages from the thread
    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper){
            super(looper);
            Log.d(TAG, "Service Handler created. Thread name:  " + Thread.currentThread().getName());
        }

        @Override
        public void handleMessage(Message msg) {
            Log.d(TAG, "handleMessage. Thread name: " + Thread.currentThread().getName());
            Intent intent = (Intent) msg.obj;

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
    }
}
