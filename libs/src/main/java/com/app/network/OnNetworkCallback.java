package com.app.network;

public interface OnNetworkCallback {
    void isNetworkConnected(boolean isConnected);

    void isNetworkAvailable(boolean isAvailable);

    void isWifiAvailable(boolean isAvailable);

    class IMPL implements OnNetworkCallback {

        @Override
        public void isNetworkConnected(boolean isConnected) {

        }

        @Override
        public void isNetworkAvailable(boolean isAvailable) {

        }

        @Override
        public void isWifiAvailable(boolean isAvailable) {

        }
    }
}
