package com.app.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;


public class NetworkCallbackImpl extends ConnectivityManager.NetworkCallback {

    private Context context;
    private boolean isNetworkConnected = false;
    private boolean isNetworkAvailable = false;
    private boolean isWifiAvailable = false;

    private OnNetworkCallback onNetworkChangeCallback;

    public NetworkCallbackImpl(Context context, OnNetworkCallback onNetworkChangeCallback) {
        this.context = context.getApplicationContext();
        this.onNetworkChangeCallback = onNetworkChangeCallback;
        initNetwork();
    }

    private void initNetwork() {
        NetworkInfo activeNetInfo = NetworkUtil.getNetworkInfo(context);
        if (activeNetInfo != null && activeNetInfo.isConnected()) {
            onNetworkChangeCallback.isNetworkConnected(isNetworkConnected = true);
            if (activeNetInfo.getState() == NetworkInfo.State.CONNECTED) {
                onNetworkChangeCallback.isNetworkAvailable(isNetworkAvailable = true);
                onNetworkChangeCallback.isWifiAvailable(isWifiAvailable = activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI);
            } else {
                onNetworkChangeCallback.isNetworkAvailable(isNetworkAvailable = false);
                onNetworkChangeCallback.isWifiAvailable(isWifiAvailable = false);
            }
        } else {
            onNetworkChangeCallback.isNetworkConnected(isNetworkConnected = false);
            onNetworkChangeCallback.isNetworkAvailable(isNetworkAvailable = false);
            onNetworkChangeCallback.isWifiAvailable(isWifiAvailable = false);
        }
    }

    /**
     * 网络已断开连接
     *
     * @param network
     */
    @Override
    public void onLost(Network network) {
        super.onLost(network);
        checkNetwork();
    }

    /**
     * 网络连接超时或网络不可达
     */
    @Override
    public void onUnavailable() {
        super.onUnavailable();
        checkNetwork();
    }

    /**
     * 当网络状态修改（网络依然可用）时调用
     *
     * @param network
     * @param ca
     */
    @Override
    public void onCapabilitiesChanged(Network network, NetworkCapabilities ca) {
        super.onCapabilitiesChanged(network, ca);
        checkNetwork(ca);
    }

    private void checkNetwork(NetworkCapabilities ca) {
        if (isConnected(ca)) {
            setNetworkConnected(true);
            if (isAvailable(ca)) {
                setNetworkAvailable(true);
                setWifiAvailable(isWifiAvailable(ca));
            } else {
                setNetworkAvailable(false);
                setWifiAvailable(false);
            }
        } else {
            setNetworkConnected(false);
            setNetworkAvailable(false);
            setWifiAvailable(false);
        }
    }
    private boolean isConnected(NetworkCapabilities ca) {
        return ca.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
    }

    private boolean isAvailable(NetworkCapabilities ca) {
        return ca.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
    }

    private boolean isWifiAvailable(NetworkCapabilities ca) {
        return ca.hasTransport(NetworkCapabilities.TRANSPORT_WIFI);
    }

    private void checkNetwork() {
        NetworkInfo activeNetInfo = NetworkUtil.getNetworkInfo(context);
        if (activeNetInfo != null && activeNetInfo.isConnected()) {
            setNetworkConnected(true);
            if (activeNetInfo.getState() == NetworkInfo.State.CONNECTED) {
                setNetworkAvailable(true);
                setWifiAvailable(activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI);
            } else {
                setNetworkAvailable(false);
                setWifiAvailable(false);
            }
        } else {
            setNetworkConnected(false);
            setNetworkAvailable(false);
            setWifiAvailable(false);
        }
    }

    private void setNetworkConnected(boolean isCheck) {
        if (isCheck != isNetworkConnected) {
            onNetworkChangeCallback.isNetworkConnected(isNetworkConnected = isCheck);
        }
    }

    private void setNetworkAvailable(boolean isCheck) {
        if (isCheck != isNetworkAvailable) {
            onNetworkChangeCallback.isNetworkAvailable(isNetworkAvailable = isCheck);
        }
    }

    private void setWifiAvailable(boolean isCheck) {
        if (isCheck != isWifiAvailable) {
            onNetworkChangeCallback.isWifiAvailable(isWifiAvailable = isCheck);
        }
    }
}
