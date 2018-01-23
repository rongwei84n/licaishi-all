package com.auts.lcssv.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;

import com.auts.lcssv.PhApplication;
import com.umeng.analytics.MobclickAgent;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * 友盟统计工具类
 * Created by weiming.zeng on 2018/1/8.
 */

public class UmengUtil {

    /*-----------------------我------------------*/
    public static final String NICK_NAME_SAVE = "NICK_NAME_SAVE";
    public static final String TEL_SAVE_SUCCESS = "TEL_SAVE_SUCCESS";
    public static final String TEL_SAVE_FAIL = "TEL_SAVE_FAIL";
    public static final String  AVATAR_SAVE_SUCCESS = " AVATAR_SAVE_SUCCESS";
    public static final String AVATAR_SAVE_FAIL = "AVATAR_SAVE_FAIL";
    public static final String LOGOUT_FAIL = "LOGOUT_FAIL";
    public static final String LOGOUT_SUCCESS = "LOGOUT_SUCCESS";
    public static final String PWD_MODIFY_SUCCESS = "PWD_MODIFY_SUCCESS";
    public static final String PWD_MODIFY_FAIL = "PWD_MODIFY_FAIL";

    /*-----------------------家庭管理------------------*/
    public static final String ROOM_ADD_CLICK = "ROOM_ADD_CLICK";
    public static final String ROOM_ADD_SAVE = "ROOM_ADD_SAVE";
    public static final String ROOM_ADD_SAVE_SUCCESS = "ROOM_ADD_SAVE_SUCCESS";
    public static final String ROOM_ADD_SAVE_FAIL = "ROOM_ADD_SAVE_FAIL";
    public static final String ROOM_EDIT_CLICK = "ROOM_EDIT_CLICK";
    public static final String ROOM_EDIT_SAVE = "ROOM_EDIT_SAVE";
    public static final String ROOM_EDIT_SAVE_SUCCESS = "ROOM_EDIT_SAVE_SUCCESS";
    public static final String ROOM_EDIT_SAVE_FAIL = "ROOM_EDIT_SAVE_FAIL";
    public static final String TIMING_SWITCH_ON = "TIMING_SWITCH_ON";
    public static final String TIMING_SWITCH_OFF = "TIMING_SWITCH_OFF";

    /*-----------------------添加智能排插------------------*/
    public static final String SCAN_SUCCESS = "SCAN_SUCCESS";
    public static final String SCAN_FAIL = "SCAN_FAIL";
    public static final String AP_STATION_ADD_FAIL = "AP_STATION_ADD_FAIL";
    public static final String AP_STATION_ADD_SUCCESS = "AP_STATION_ADD_SUCCESS";
    public static final String SMART_LINK_ADD_FAIL = "SMART_LINK_ADD_FAIL";
    public static final String SMART_LINK_ADD_SUCCESS = "SMART_LINK_ADD_SUCCESS";

    /*-----------------------添加智能排插------------------*/
    public static final String SCENE_OPERATE = "SCENE_OPERATE";
    public static final String SCENE_OPERATE_SUCCESS = "SCENE_OPERATE_SUCCESS";
    public static final String SCENE_OPERATE_FAIL = "SCENE_OPERATE_FAIL";
    public static final String SCENE_ADD_SAVE = "SCENE_ADD_SAVE";
    /*-----------------------设备------------------*/
    public static final String DEVICE_ENTER = "DEVICE_ENTER";


    /**
     * 友盟打点，附带参数
     *
     * @param context
     * @param event
     * @param map
     */
    public static void onEvent(Context context, String event, HashMap<String, String> map) {
        if (context != null) {
            MobclickAgent.onEvent(context, event, map);
        } else {
            MobclickAgent.onEvent(PhApplication.getPhApplication(), event, map);
        }
    }
    public static void onEvent(Context context, String eventId) {
        if (context != null) {
            MobclickAgent.onEvent(context, eventId);
        } else {
            MobclickAgent.onEvent(PhApplication.getPhApplication(), eventId);
        }
    }

    public static void onEvent(String eventId) {
        MobclickAgent.onEvent(PhApplication.getPhApplication(), eventId);
    }

    public static void onPageStart(String pageTitle) {
        MobclickAgent.onPageStart(pageTitle);
    }

    public static void onPageEnd(String pageTitle) {
        MobclickAgent.onPageEnd(pageTitle);
    }


    public static boolean checkPermission(Context context, String permission) {
        boolean result = false;
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                Class<?> clazz = Class.forName("android.content.Context");
                Method method = clazz.getMethod("checkSelfPermission", String.class);
                int rest = (Integer) method.invoke(context, permission);
                if (rest == PackageManager.PERMISSION_GRANTED) {
                    result = true;
                } else {
                    result = false;
                }
            } catch (Exception e) {
                result = false;
            }
        } else {
            PackageManager pm = context.getPackageManager();
            if (pm.checkPermission(permission, context.getPackageName()) == PackageManager.PERMISSION_GRANTED) {
                result = true;
            }
        }
        return result;
    }
    public static String getDeviceInfo(Context context) {
        try {
            org.json.JSONObject json = new org.json.JSONObject();
            android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            String device_id = null;
            if (checkPermission(context, Manifest.permission.READ_PHONE_STATE)) {
                device_id = tm.getDeviceId();
            }
            String mac = null;
            FileReader fstream = null;
            try {
                fstream = new FileReader("/sys/class/net/wlan0/address");
            } catch (FileNotFoundException e) {
                fstream = new FileReader("/sys/class/net/eth0/address");
            }
            BufferedReader in = null;
            if (fstream != null) {
                try {
                    in = new BufferedReader(fstream, 1024);
                    mac = in.readLine();
                } catch (IOException e) {
                } finally {
                    if (fstream != null) {
                        try {
                            fstream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            json.put("mac", mac);
            if (TextUtils.isEmpty(device_id)) {
                device_id = mac;
            }
            if (TextUtils.isEmpty(device_id)) {
                device_id = android.provider.Settings.Secure.getString(context.getContentResolver(),
                        android.provider.Settings.Secure.ANDROID_ID);
            }
            json.put("device_id", device_id);
            return json.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
