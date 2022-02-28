package com.app.network;

import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;


public class NetworkCallbackImpl extends ConnectivityManager.NetworkCallback {
    private OnNetworkChangeCallback onNetworkChangeCallback;

    public NetworkCallbackImpl(OnNetworkChangeCallback onNetworkChangeCallback) {
        this.onNetworkChangeCallback = onNetworkChangeCallback;
    }

    /**
     * 网络连接成功回调
     *
     * @param network
     */
    @Override
    public void onAvailable(Network network) {
        super.onAvailable(network);
    }

    /**
     * 网络正在丢失连接
     *
     * @param network
     * @param maxMsToLive
     */
    @Override
    public void onLosing(Network network, int maxMsToLive) {
        super.onLosing(network, maxMsToLive);
    }

    /**
     * 网络已断开连接
     *
     * @param network
     */
    @Override
    public void onLost(Network network) {
        super.onLost(network);
    }

    /**
     * 网络连接超时或网络不可达
     */
    @Override
    public void onUnavailable() {
        super.onUnavailable();
    }

    /**
     * 网络状态变化
     *
     * @param network
     * @param networkCapabilities
     */
    @Override
    public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
        super.onCapabilitiesChanged(network, networkCapabilities);
        if (onNetworkChangeCallback != null) {
            onNetworkChangeCallback.onChange(networkCapabilities);
            if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
                //表示是否连接上了互联网
                onNetworkChangeCallback.isConnected();
                if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    //表示接入的是WIFI网络
                    onNetworkChangeCallback.isWifiConnected();
                }
            }
            if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
                //表示能够和互联网通信,能够上网
                onNetworkChangeCallback.isAvailable();
                if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    //表示接入的是WIFI网络
                    onNetworkChangeCallback.isWifiAvailable();
                }
            }
        }
    }

    /**
     * 网络连接属性变化
     *
     * @param network
     * @param linkProperties
     */
    @Override
    public void onLinkPropertiesChanged(Network network, LinkProperties linkProperties) {
        super.onLinkPropertiesChanged(network, linkProperties);
    }

    /**
     * 访问的网络阻塞状态发生变化
     *
     * @param network
     * @param blocked 网络是否阻塞
     */
//    @Override
//    public void onBlockedStatusChanged(Network network, boolean blocked) {
//        super.onBlockedStatusChanged(network, blocked);
//    }
}
