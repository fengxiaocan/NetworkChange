package com.app.networkchange;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.app.network.NetworkUtil;
import com.app.network.OnNetworkCallback;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NetworkUtil.registerNetworkCallback(this, new OnNetworkCallback.IMPL() {

            @Override
            public void isWifiAvailable(boolean isAvailable) {
                Log.e("noah", "isWifiAvailable = " + isAvailable);
            }
        });
    }
}