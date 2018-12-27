package com.hausberger.serviceexamples.backgroundservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.hausberger.serviceexamples.Constants;
import com.hausberger.serviceexamples.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_bg_service)
public class BgServiceActivity extends AppCompatActivity {

    @ViewById
    protected Button startServiceButton;

    @ViewById
    protected Button stopServiceButton;

    @ViewById
    protected TextView counter;

    private BroadcastReceiver myLocalBroadcastReceiver;


    @AfterViews
    protected void init() {
        myLocalBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int value = intent.getIntExtra(Constants.RESULT, -1);
                counter.setText(getResources().getString(R.string.task_executed, value));
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter intentFilter = new IntentFilter(Constants.MY_INTENT);
        LocalBroadcastManager.getInstance(this).registerReceiver(myLocalBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(myLocalBroadcastReceiver);
    }

    @Click(R.id.startServiceButton)
    protected void startButtonClicked() {
        MyAnnotatedService_.intent(getApplication()).start();
        //HelloService_.intent(getApplicationContext()).start();
    }

    @Click(R.id.stopServiceButton)
    protected void stopButtonClicked() {
        MyAnnotatedService_.intent(getApplication()).stop();
        //HelloService_.intent(getApplicationContext()).stop();
    }

    @Click(R.id.startIntentService)
    protected void startIntentServiceButtonClicked() {
        Intent intent = new Intent();
        intent.putExtra(Constants.SERVICE_DURATION, 5);
        MyIntentService_.intent(getApplication()).extras(intent).start();
    }
}
