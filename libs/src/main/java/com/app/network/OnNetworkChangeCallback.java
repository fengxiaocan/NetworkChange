package com.app.network;

import android.net.NetworkCapabilities;

public interface OnNetworkChangeCallback {
    void onChange(NetworkCapabilities capabilities);

    void isConnected();

    void isAvailable();

    void isWifiConnected();

    void isWifiAvailable();
}
