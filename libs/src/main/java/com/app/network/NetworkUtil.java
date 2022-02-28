package com.app.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkRequest;

public final class NetworkUtil {
    static class Holder{
        static NetworkUtil INSTANCE = new NetworkUtil();
    }

    private NetworkUtil() {
    }

    private NetworkCallbackImpl networkCallback;

    public static ConnectivityManager getConnectivityManager(Context context) {
        return (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public static NetworkInfo getNetworkInfo(Context context) {
        ConnectivityManager manager = getConnectivityManager(context);
        if (manager == null) {
            return null;
        }
        return manager.getActiveNetworkInfo();
    }

    /**
     * 指示是否存在网络连接，以及是否可能建立连接并传递数据。
     * 总是在尝试执行数据事务之前调用此函数。
     *
     * @return 如果存在网络连接，则为True，否则为false。
     */
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager manager = getConnectivityManager(context);
        if (manager == null) {
            return false;
        }
        NetworkInfo networkinfo = manager.getActiveNetworkInfo();
        return networkinfo != null && networkinfo.isConnected();
    }

    /**
     * 指示网络连接是否可能。当持久或半持久状态阻止连接到该网络的可能性时，该网络不可用。例子包括
     * <ul>
     * <li>该设备不在此类型任何网络的覆盖范围内。</li>
     * <li>设备处于家庭网络以外的网络(即漫游)，且数据漫游已禁用。</li>
     * <li>设备的无线电是关闭的，例如，因为飞机模式是启用的。</li>
     * 从Android L开始，总是返回true，因为系统只返回可用网络的信息。
     *
     * @return 如果网络可用则为True，否则为false
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager manager = getConnectivityManager(context);
        if (manager == null) {
            return false;
        }
        NetworkInfo networkinfo = manager.getActiveNetworkInfo();
        return networkinfo != null && networkinfo.isAvailable();
    }

    /**
     * 检测网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean checkNetworkAvailable(Context context) {
        ConnectivityManager manager = getConnectivityManager(context);
        if (manager == null) {
            return false;
        }
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getState() == NetworkInfo.State.CONNECTED) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检测网络是否是WiFi
     *
     * @return
     */
    public static boolean isWifi(Context context) {
        ConnectivityManager manager = getConnectivityManager(context);
        if (manager != null) {
            NetworkInfo activeNetInfo = manager.getActiveNetworkInfo();
            return activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI;
        }
        return false;
    }


    /**
     * 检测网络是否是WiFi并且活跃状态
     *
     * @return
     */
    public static boolean isWifiAvailable(Context context) {
        ConnectivityManager manager = getConnectivityManager(context);
        if (manager != null) {
            NetworkInfo activeNetInfo = manager.getActiveNetworkInfo();
            if (activeNetInfo != null) {
                if (activeNetInfo.getState() == NetworkInfo.State.CONNECTED) {
                    return activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI;
                }
            }
        }
        return false;
    }
    /**
     * 检测网络是否是移动网络
     *
     * @return
     */
    public static boolean isMobile(Context context) {
        ConnectivityManager manager = getConnectivityManager(context);
        if (manager != null) {
            NetworkInfo activeNetInfo = manager.getActiveNetworkInfo();
            return activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE;
        }
        return false;
    }


    /**
     * 检测网络是否是移动网络并且活跃状态
     *
     * @return
     */
    public static boolean isMobileAvailable(Context context) {
        ConnectivityManager manager = getConnectivityManager(context);
        if (manager != null) {
            NetworkInfo activeNetInfo = manager.getActiveNetworkInfo();
            if (activeNetInfo != null) {
                if (activeNetInfo.getState() == NetworkInfo.State.CONNECTED) {
                    return activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE;
                }
            }
        }
        return false;
    }

    /**
     * 监听网络状态变化
     * @param context
     * @return
     */
    public static void registerNetworkCallback(Context context, OnNetworkChangeCallback networkChangeCallback) {
        unregisterNetworkCallback(context);

        NetworkCallbackImpl networkCallback = new NetworkCallbackImpl(networkChangeCallback);
        if (registerNetworkCallback(context,networkCallback)) {
            Holder.INSTANCE.networkCallback = networkCallback;
        }
    }



    /**
     * 注销监听网络状态变化
     * @param context
     * @return
     */
    public static void unregisterNetworkCallback(Context context) {
        if (Holder.INSTANCE.networkCallback != null) {
            if (unregisterNetworkCallback(context, Holder.INSTANCE.networkCallback)) {
                Holder.INSTANCE.networkCallback = null;
            }
        }
    }

    /**
     * 监听网络状态变化
     * @param context
     * @return
     */
    public static boolean registerNetworkCallback(Context context, ConnectivityManager.NetworkCallback callback) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkRequest build = new NetworkRequest.Builder().build();
            connectivityManager.registerNetworkCallback(build, callback);
            return true;
        }
        return false;
    }


    /**
     * 注销监听网络状态变化
     * @param context
     * @return
     */
    public static boolean unregisterNetworkCallback(Context context, ConnectivityManager.NetworkCallback callback) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            connectivityManager.unregisterNetworkCallback(callback);
            return true;
        }
        return false;
    }


}
