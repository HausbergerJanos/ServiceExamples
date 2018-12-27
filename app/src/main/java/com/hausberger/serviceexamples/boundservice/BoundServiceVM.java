package com.hausberger.serviceexamples.boundservice;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

public class BoundServiceVM extends ViewModel {

    private static final String TAG = BoundServiceVM.class.getSimpleName() + "-->";

    private MutableLiveData<Boolean> isProgressUpdating;
    private MutableLiveData<MyBoundService.MyBinder> binder;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected: connected to service.");
            MyBoundService.MyBinder myBinder = (MyBoundService.MyBinder) service;
            binder.postValue(myBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected: disconnected from service.");
            binder.postValue(null);
        }
    };

    public BoundServiceVM() {
        isProgressUpdating = new MutableLiveData<>();
        binder = new MutableLiveData<>();
    }

    public LiveData<Boolean> getIsProgressUpdating() {
        return isProgressUpdating;
    }

    public LiveData<MyBoundService.MyBinder> getBinder() {
        return binder;
    }

    public ServiceConnection getServiceConnection() {
        return serviceConnection;
    }

    public void setIsUpdating(Boolean isUpdating) {
        isProgressUpdating.postValue(isUpdating);
    }
}
