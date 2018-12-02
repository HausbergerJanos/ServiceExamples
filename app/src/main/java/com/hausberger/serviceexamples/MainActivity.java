package com.hausberger.serviceexamples;

import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @ViewById
    protected Button startServiceButton;

    @ViewById
    protected Button stopServiceButton;


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
        MyIntentService_.intent(getApplication()).start();
    }
}
