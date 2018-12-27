package com.hausberger.serviceexamples.jobscheduler;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class ExampleJobService extends JobService {

    private static final String TAG = ExampleJobService.class.getSimpleName();

    private boolean jobCancelled;

    /**
     * Ha az itt végrehajtott művelet rövid, akkor false-ot kell visszaadnunk. Ha viszont a műelet hosszú futási
     * idővel rendelkezik, pl async task, akkor true a visszatérési érték.
     */
    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "Job started");
        doBackgroundWork(params);
        return true;
    }

    private void doBackgroundWork(final JobParameters params) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    Log.d(TAG, "run: " + i);
                    if (jobCancelled) {
                        return;
                    }

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Log.d(TAG, "Job finished.");
                jobFinished(params, false);
            }
        }).start();
    }

    /**
     * Akkor hívódik meg, ha a job nem sikerült. Pl wifihez volt kötve, de időközben kikapcsolták a wifit.
     * A visszatérési érték azt mondja meg, hogy a rendszernek újra kell-e terveznie a job-ot ha az nem sikerült.
     */
    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "Job cancelled before completion.");
        jobCancelled = true;
        return true;
    }
}
