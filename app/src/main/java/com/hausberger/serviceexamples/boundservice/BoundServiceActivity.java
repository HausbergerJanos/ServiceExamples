package com.hausberger.serviceexamples.boundservice;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hausberger.serviceexamples.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_bound_service)
public class BoundServiceActivity extends AppCompatActivity {

    private static final String TAG = BoundServiceActivity.class.getSimpleName() + "-->";

    @ViewById
    protected ProgressBar progressBar;

    @ViewById
    protected TextView textView;

    @ViewById
    protected Button button;

    private MyBoundService service;
    private BoundServiceVM viewModel;
    private Handler progressBarHandler;
    private Runnable progressBarRunnable;


    @AfterViews
    protected void init() {
        progressBarHandler = new Handler();

        viewModel = ViewModelProviders.of(this).get(BoundServiceVM.class);

        viewModel.getBinder().observe(this, new Observer<MyBoundService.MyBinder>() {
            @Override
            public void onChanged(@Nullable MyBoundService.MyBinder myBinder) {
                if (myBinder != null) {
                    Log.d(TAG, "onChanged: connected to service");
                    service = myBinder.getService();
                } else {
                    Log.d(TAG, "onChanged: unbound from service");
                    service = null;
                }
            }
        });

        viewModel.getIsProgressUpdating().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable final Boolean aBoolean) {
                if (aBoolean) {
                    progressBarRunnable = new Runnable() {
                        @Override
                        public void run() {
                            if (aBoolean) {
                                if (viewModel.getBinder().getValue() != null) {
                                    if (service.getProgress() == service.getMaxValue()) {
                                        viewModel.setIsUpdating(false);
                                    }
                                    progressBar.setMax(service.getMaxValue());
                                    progressBar.setProgress(service.getProgress());
                                    String progress =
                                            String.valueOf(100 * service.getProgress() / service.getMaxValue()) + "%";
                                    textView.setText(progress);
                                    progressBarHandler.postDelayed(this, 100);
                                } else {
                                    progressBarHandler.removeCallbacks(progressBarRunnable);
                                }
                            }
                        }
                    };
                }

                if (aBoolean) {
                    button.setText("Pause");
                    progressBarHandler.postDelayed(progressBarRunnable, 100);
                } else {
                    if (service.getProgress() == service.getMaxValue()) {
                        button.setText("Restart");
                    } else {
                        button.setText("Start");
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        startService();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (viewModel.getBinder() != null) {
            unbindService(viewModel.getServiceConnection());
        }
    }

    private void startService() {
        Intent serviceIntent = new Intent(this, MyBoundService.class);
        startService(serviceIntent);

        bindService(serviceIntent);
    }

    private void bindService(Intent serviceIntent) {
        // BIND_AUTO_CREATE -> Ha a service nincs meg, akkor a rendszer kreál egyet automatikusan
        bindService(serviceIntent, viewModel.getServiceConnection(), Context.BIND_AUTO_CREATE);
    }

    protected void toggleUpdates() {
        if (service != null) {
            if (service.getProgress() == service.getMaxValue()) {
                service.resetTask();
                button.setText("Start");
            } else {
                if (service.getPaused()) {
                    service.unPauseLongRunningTask();
                    viewModel.setIsUpdating(true);
                } else {
                    service.pauseLongRunningTask();
                    viewModel.setIsUpdating(false);
                }
            }
        }
    }

    @Click(R.id.button)
    protected void onToggleButtonClicked() {
        toggleUpdates();
    }
}
