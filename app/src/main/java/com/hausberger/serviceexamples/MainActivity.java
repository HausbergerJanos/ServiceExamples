package com.hausberger.serviceexamples;

import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.hausberger.serviceexamples.backgroundservice.BgServiceActivity_;
import com.hausberger.serviceexamples.boundservice.BoundServiceActivity_;
import com.hausberger.serviceexamples.foregroundservice.ForegroundServiceActivity;
import com.hausberger.serviceexamples.foregroundservice.ForegroundServiceActivity_;
import com.hausberger.serviceexamples.jobscheduler.JobSchedulerActivity_;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @ViewById
    protected Button bgServiceButton;

    @ViewById
    protected Button foregroundServiceButton;

    @ViewById
    protected Button jobSchedulerButton;

    @ViewById
    protected Button boundServiceButton;


    @Click(R.id.bgServiceButton)
    protected void onBgServiceButtonClick() {
        BgServiceActivity_.intent(this).start();
    }

    @Click(R.id.foregroundServiceButton)
    protected void onForegroundServiceButtonClicked() {
        ForegroundServiceActivity_.intent(this).start();
    }

    @Click(R.id.jobSchedulerButton)
    protected void onJobSchedulerButtonClick() {
        JobSchedulerActivity_.intent(this).start();
    }

    @Click(R.id.boundServiceButton)
    protected void onBoundServiceButtonclick() {
        BoundServiceActivity_.intent(this).start();
    }

}
