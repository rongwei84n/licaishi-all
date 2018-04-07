package com.auts.lcscli.receiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;

import com.auts.lcscli.PhApplication;
import com.auts.lcscli.activity.SplashActivity;
import com.auts.lcscli.event.NetworkNameChangeEvent;
import com.auts.lcscli.event.NetworkRecoveryEvent;
import com.auts.lcscli.event.NetworkWiFiConnectEvent;
import com.auts.lcscli.manager.UpdateManager;
import com.auts.lcscli.util.AppManager;
import com.auts.lcscli.util.NetworkUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * 监听网络状态变化
 *
 * @author xiaolei.yang
 * @date 2017/7/7
 */

public class NetworkStateReceiver extends BroadcastReceiver {

    /**
     * true-网络可用，false-网络不可用
     */
    private boolean networkIsAble;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            return;
        }
        ConnectivityManager connectMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectMgr == null) {
            return;
        }
        NetworkInfo info = connectMgr.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (!networkIsAble) {
                EventBus.getDefault().post(new NetworkRecoveryEvent());
                if (!PhApplication.mHasCheckUpdate) {
                    Activity activity = AppManager.getAppManager().currentActivity();
                    if (null != activity && !(activity instanceof SplashActivity)) {
                        new UpdateManager(activity).appUpdate(false);
                    }
                }
            }
            networkIsAble = true;
            WifiInfo wifiInfo = NetworkUtils.getWifiInfo();
            if (wifiInfo != null) {
                EventBus.getDefault().post(new NetworkNameChangeEvent());
                //把这几个做为有连接的原因，见SupplicanState的隐藏方法  isConnecting(SupplicantState state)
                if (wifiInfo.getSupplicantState() == SupplicantState.ASSOCIATING
                        || wifiInfo.getSupplicantState() == SupplicantState.ASSOCIATED
                        || wifiInfo.getSupplicantState() == SupplicantState.FOUR_WAY_HANDSHAKE
                        || wifiInfo.getSupplicantState() == SupplicantState.GROUP_HANDSHAKE
                        || wifiInfo.getSupplicantState() == SupplicantState.COMPLETED) {
                    if (NetworkUtils.isWifiConnect()) {
                        //有wifi连接
                        EventBus.getDefault().post(new NetworkWiFiConnectEvent(true));
                    } else {
                        //有wifi连接
                        EventBus.getDefault().post(new NetworkWiFiConnectEvent(false));
                    }
                } else {
                    //无wifi连接
                    EventBus.getDefault().post(new NetworkWiFiConnectEvent(false));
                }
            } else {
                //无wifi连接
                EventBus.getDefault().post(new NetworkWiFiConnectEvent(false));
            }
        } else {
            networkIsAble = false;
            //无wifi连接
            EventBus.getDefault().post(new NetworkWiFiConnectEvent(false));
        }
    }
}
