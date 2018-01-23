/**
 * Copyright © 2015-2020 100msh.com All Rights Reserved
 */

package com.auts.lcssv.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.Formatter;

import com.auts.lcssv.PhApplication;
import com.auts.lcssv.constants.UrlConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * 网络相关的工具类
 *
 * @author qisheng.lv
 * @date 2017/4/12
 */
public class NetworkUtils {

    private NetworkUtils() {
        /* cannot be instantiated */
        try {
            throw new UnsupportedOperationException("cannot be instantiated");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断是否是wifi连接
     */
    public static boolean isWifiConnect() {
        ConnectivityManager cm = (ConnectivityManager) PhApplication.getPhApplication().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return false;
        }

        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null) {
            return false;
        }

        return info.getType() == ConnectivityManager.TYPE_WIFI;

    }

    /**
     * 获取当前连接的wifi信息
     *
     * @return
     */
    public static WifiInfo getWifiInfo() {
        WifiManager wifiManager = (WifiManager) PhApplication.getPhApplication().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifiManager != null) {
            return wifiManager.getConnectionInfo();
        }
        return null;
    }

    /**
     * 打开网络设置界面
     */
    public static void openSetting(Activity activity) {
        Intent intent = new Intent();
        intent.setAction("android.net.wifi.PICK_WIFI_NETWORK");
        intent.putExtra("extra_prefs_show_button_bar", true);
        intent.putExtra("wifi_enable_next_on_connect", true);
        activity.startActivityForResult(intent, 0);
    }

    /**
     * 检测当的网络（WLAN、3G/2G）状态
     *
     * @return true 表示网络可用
     */
    public static boolean isNetAvailable() {
        ConnectivityManager connectivity = (ConnectivityManager) PhApplication.getPhApplication().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected() && info.getState() == NetworkInfo.State.CONNECTED) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取网络连接类型
     *
     * @return
     */
    public static String getNetType() {
        String type = "none";
        ConnectivityManager connectMgr = (ConnectivityManager) PhApplication.getPhApplication().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectMgr.getActiveNetworkInfo();
        if (info != null) {
            if (ConnectivityManager.TYPE_WIFI == info.getType()) {
                return "WiFi";
            } else {
                int subType = info.getSubtype();
                if (subType == TelephonyManager.NETWORK_TYPE_CDMA || subType == TelephonyManager.NETWORK_TYPE_GPRS
                        || subType == TelephonyManager.NETWORK_TYPE_EDGE) {
                    return "2G";
                } else if (subType == TelephonyManager.NETWORK_TYPE_UMTS
                        || subType == TelephonyManager.NETWORK_TYPE_HSDPA
                        || subType == TelephonyManager.NETWORK_TYPE_EVDO_A
                        || subType == TelephonyManager.NETWORK_TYPE_EVDO_0
                        || subType == TelephonyManager.NETWORK_TYPE_EVDO_B) {
                    return "3G";
                } else if (subType == TelephonyManager.NETWORK_TYPE_LTE) {
                    // LTE是3g到4g的过渡，是3.9G的全球标准
                    return "4G";
                }
            }
        }
        return type;
    }

    /**
     * 获取当前连接路由的管理IP
     * 从理论上来说，路由管理IP应该和DHCP服务IP一致
     *
     * @return
     */
    public static String getGatewayIp() {
        WifiManager wifiManager = (WifiManager) PhApplication.getPhApplication().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        return Formatter.formatIpAddress(wifiManager.getDhcpInfo().gateway);
    }

    public static String getLocalUrl() {
        String url = isWifiConnect() ? "http://" + getGatewayIp() : UrlConfig.LocalUrl.URL_ROUTER_HOST;
        LogUtils.debug("getLocalUrl: " + url);
        return url;
    }


    /**
     * 利用WifiConfiguration.KeyMgmt的管理机制，来判断当前wifi是否需要连接密码
     *
     * @return true：需要密码连接，false：不需要密码连接
     */
    public static boolean checkIsCurrentWifiHasPassword() {
        WifiManager wifiManager = (WifiManager) PhApplication.getPhApplication().getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);

        // 得到当前连接的wifi热点的信息
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();

        // 得到当前WifiConfiguration列表，此列表包含所有已经连过的wifi的热点信息，未连过的热点不包含在此表中
        List<WifiConfiguration> wifiConfiguration = wifiManager.getConfiguredNetworks();

        String currentSSID = wifiInfo.getSSID();
        if (currentSSID != null && currentSSID.length() > 2) {
            if (currentSSID.startsWith("\"") && currentSSID.endsWith("\"")) {
                currentSSID = currentSSID.substring(1, currentSSID.length() - 1);
            }

            if (wifiConfiguration != null && wifiConfiguration.size() > 0) {
                for (WifiConfiguration configuration : wifiConfiguration) {
                    if (configuration != null && configuration.status == WifiConfiguration.Status.CURRENT) {
                        String ssid = null;
                        if (!TextUtils.isEmpty(configuration.SSID)) {
                            ssid = configuration.SSID;
                            if (configuration.SSID.startsWith("\"") && configuration.SSID.endsWith("\"")) {
                                ssid = configuration.SSID.substring(1, configuration.SSID.length() - 1);
                            }
                        }
                        if (TextUtils.isEmpty(currentSSID) || currentSSID.equalsIgnoreCase(ssid)) {
                            //KeyMgmt.NONE表示无需密码
                            return (!configuration.allowedKeyManagement.get(WifiConfiguration.KeyMgmt.NONE));
                        }
                    }
                }
            }
        }
        //默认为需要连接密码
        return true;

    }

    /**
     * 获取手机周围的wifi列表
     */
    public static List<ScanResult> getScanResult() {
        WifiManager wifiManager = (WifiManager) PhApplication.getPhApplication().getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);
        if (null == wifiManager) {
            return null;
        }
        return wifiManager.getScanResults();
    }
}
