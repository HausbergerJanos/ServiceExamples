package com.hausberger.serviceexamples.foregroundservice;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.hausberger.serviceexamples.Constants;
import com.hausberger.serviceexamples.R;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_foreground_service)
public class ForegroundServiceActivity extends AppCompatActivity {

    @ViewById
    protected EditText editText;

    @ViewById
    protected Button startServiceButton;

    @ViewById
    protected Button stopServiceButton;

    @Click(R.id.startServiceButton)
    protected void onStartServiceButton() {
        String input = editText.getText().toString();

        Intent intent = new Intent(this, MyForegroundService.class);
        intent.putExtra(Constants.ForegroundService.EXTRA, input);

        ContextCompat.startForegroundService(this, intent);
    }

    @Click(R.id.stopServiceButton)
    protected void onStopServiceButton() {
        Intent intent = new Intent(this, MyForegroundService.class);
        stopService(intent);
    }
}
