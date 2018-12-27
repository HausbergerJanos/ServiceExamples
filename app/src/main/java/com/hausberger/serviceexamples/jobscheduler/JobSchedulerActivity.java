package com.hausberger.serviceexamples.jobscheduler;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.hausberger.serviceexamples.R;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_job_scheduler)
public class JobSchedulerActivity extends AppCompatActivity {

    private static final String TAG = JobSchedulerActivity.class.getSimpleName();

    @ViewById
    protected Button scheduleJobButton;

    @ViewById
    protected Button cancelJobButton;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Click(R.id.scheduleJobButton)
    protected void onScheduleJobButtonClicked() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ComponentName componentName = new ComponentName(this, ExampleJobService.class);
            JobInfo info = new JobInfo.Builder(123, componentName)
                    .setRequiresCharging(true)                                  // töltés közben
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)     // korlátlan (Wifi)
                    .setPersisted(true)                                         // rebootolás után is újrakezdi a rendszer ha szükséges
                    .setPeriodic(15 * 60 * 1000)                                // 15 perc a legalacsonyabb. A rendszer garantálja, hogy ebben a periódusban biztosan végre lesz hajtva a jobunk. De arra nincs ráhatásunk, h pontosan mikor.
                    .build();

            JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
            int result = jobScheduler.schedule(info);

            if (result == JobScheduler.RESULT_SUCCESS) {
                Log.d(TAG, "Job scheduling success.");
            } else {
                Log.d(TAG, "Job scheduling failed.");
            }
        } else {
            Toast.makeText(this, "Low SDK version", Toast.LENGTH_SHORT).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Click(R.id.cancelJobButton)
    protected void onCancelJobButtonClicked() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
            jobScheduler.cancel(123);
            Log.d(TAG, "Job cancelled");
        } else {
            Toast.makeText(this, "Low SDK version", Toast.LENGTH_SHORT).show();
        }
    }

}
