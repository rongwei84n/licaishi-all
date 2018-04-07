package com.auts.lcscli.util;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.auts.lcscli.PhApplication;


/**
 *
 */
public class AppInfoUtils {

    /**
     * 获取当前包的版本信息
     *
     * @return
     */
    public static String getAppVersionName() {
        String strVer = null;
        try {
            PackageManager manager = PhApplication.getAppContext().getPackageManager();
            PackageInfo info = manager.getPackageInfo(PhApplication.getAppContext().getPackageName(), 0);
            strVer = info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strVer;
    }


    /**
     * 获取当前包的版本编号
     *
     * @return
     */
    public static int getAppVersionCode() {
        int nVer = 0;
        try {
            PackageManager manager = PhApplication.getAppContext().getPackageManager();
            PackageInfo info = manager.getPackageInfo(PhApplication.getAppContext().getPackageName(), 0);
            nVer = info.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nVer;
    }

    /**
     * 获取渠道标志符
     *
     * @return
     */
    public static String getChannel() {
        return (String) CommonUtils.getMetaDataByKey(PhApplication.getAppContext(), "UMENG_CHANNEL");
    }

    /**
     * 获取当前targetSdkVersion
     */

    public static int getTargetSdkVersion() {
        int version = 0;
        PackageManager pm = PhApplication.getAppContext().getPackageManager();
        try {
            ApplicationInfo applicationInfo = pm.getApplicationInfo(PhApplication.getAppContext().getPackageName(), 0);
            if (applicationInfo != null) {
                version = applicationInfo.targetSdkVersion;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    /**
     * 代表当前的环境是正式环境还是测试环境,true代表正式环境
     *
     * @return
     */
    public static boolean isInProductionEnvironment() {
        return true;
//        return BuildConfig.IS_IN_PRODUCTION_ENVIRONMENT;
    }

    /**
     * 获取渠道名
     * @return 如果没有获取成功，那么返回值为空
     */
    public static String getChannelName() {
        String channelName = "";
        try {
            PackageManager packageManager = PhApplication.getAppContext().getPackageManager();
            if (packageManager != null) {
                //注意此处为ApplicationInfo 而不是 ActivityInfo,因为友盟设置的meta-data是在application标签中，而不是某activity标签中，所以用ApplicationInfo
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(PhApplication.getAppContext().getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        channelName = applicationInfo.metaData.getString("BUGLY_APP_CHANNEL");
                    }
                }

            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return channelName;
    }


}
