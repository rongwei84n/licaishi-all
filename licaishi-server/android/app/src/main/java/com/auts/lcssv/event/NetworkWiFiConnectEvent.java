package com.auts.lcssv.event;

/**
 * 网络是否连接wifi
 * Created by xiaolei.yang on 2017/10/17.
 */

public class NetworkWiFiConnectEvent {
    private boolean isConnect;

    public NetworkWiFiConnectEvent(boolean isConnect) {
        this.isConnect = isConnect;
    }

    public boolean isConnect() {
        return isConnect;
    }
}
