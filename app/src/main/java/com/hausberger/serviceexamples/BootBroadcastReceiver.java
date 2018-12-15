package com.hausberger.serviceexamples;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            Intent i = new Intent(context, MyIntentService_.class);
            i.putExtra(Constants.SERVICE_DURATION, 12);
            //context.startService(i);
            MyJobIntentService.enqueWork(context, i);
        }
    }
}
