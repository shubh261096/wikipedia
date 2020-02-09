package com.enguru.wikipedia.view.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.lifecycle.MutableLiveData;

import com.enguru.wikipedia.utils.CommonUtils;

public class NetworkChangeReceiver extends BroadcastReceiver {
    boolean status;
    public static MutableLiveData<Boolean> networkChange = new MutableLiveData<>();

    public NetworkChangeReceiver() {
    }

    public void setStatus(Boolean status) {
        networkChange.postValue(status);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        status = CommonUtils.getConnectivityStatus(context);
        setStatus(status);
    }

}
