package com.hausberger.serviceexamples;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @ViewById
    protected Button startServiceButton;

    @ViewById
    protected Button stopServiceButton;
}
