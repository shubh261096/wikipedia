package com.enguru.wikipedia.view.ui;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.enguru.wikipedia.R;
import com.enguru.wikipedia.view.receiver.NetworkChangeReceiver;
import com.google.android.material.snackbar.Snackbar;

public abstract class BaseActivity extends AppCompatActivity {
    int onStartCount = 0;
    private NetworkChangeReceiver networkChangeReceiver;
    private static Snackbar snackbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        onStartCount = 1;
        if (savedInstanceState == null) // 1st time
        {
            this.overridePendingTransition(R.anim.anim_slide_in_left,
                    R.anim.anim_slide_out_left);
        } else // already created so reverse animation
        {
            onStartCount = 2;
        }

        /* Starting observer of Internet change*/
        networkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        observeInternetChange();
    }

    private void observeInternetChange() {
        NetworkChangeReceiver.networkChange.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean status) {
                if (status != null) {
                    getNetworkStatus(status);
                    if (!status) {
                        snackbar = Snackbar.make(findViewById(android.R.id.content), "No Internet Connection", Snackbar.LENGTH_INDEFINITE);
                        snackbar.show();
                    } else {
                        if (snackbar != null) {
                            if (snackbar.isShown()) {
                                snackbar.dismiss();
                            }
                        }
                    }
                }
            }
        });
    }

    public abstract void getNetworkStatus(boolean status);


    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        if (onStartCount > 1) {
            this.overridePendingTransition(R.anim.anim_slide_in_right,
                    R.anim.anim_slide_out_right);

        } else if (onStartCount == 1) {
            onStartCount++;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);
    }
}
