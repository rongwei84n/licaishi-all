package com.auts.lcssv.manager;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.auts.lcssv.bean.litebean.DataBean;
import com.auts.lcssv.constants.AppConstans;
import com.auts.lcssv.util.LogUtils;
import com.auts.lcssv.util.SpfUtils;

import org.litepal.crud.DataSupport;

import java.util.List;


/**
 * 云账号管理类
 * Created by qisheng.lv on 2017/4/12.
 */

public class AccountManager {

    private static volatile AccountManager mInstance;

    private AccountManager() {

    }

    public static AccountManager getInstance() {
        if (mInstance == null) {
            synchronized (AccountManager.class) {
                if (mInstance == null) {
                    mInstance = new AccountManager();
                }
            }
        }
        return mInstance;
    }


    public void saveAuthCode(String code) {
        if (!TextUtils.isEmpty(code)) {
            SpfUtils.put(AppConstans.Sp.AUTHORIZATION_CODE, code);
        }
    }

    public String getAuthCode() {
        return (String) SpfUtils.get(AppConstans.Sp.AUTHORIZATION_CODE, "");
//        return "feixun.SH_7";
    }

    public boolean hasAuthCode() {
        String code = getAuthCode();
        return !TextUtils.isEmpty(code);
    }

    //    type的值为AppConstans.Sp下的常量，使用、新增或删除时以这里为准，方便统一管理。
    public void saveValue(String type, String value) {
        DataBean dataBean = new DataBean();
        dataBean.setType(type);
        dataBean.setValue(value);
        dataBean.saveOrUpdate("type = ?", type);
    }

    public String getValue(String type) {
        List<DataBean> values = DataSupport.where("type = ?", type).find(DataBean.class);
        if (values != null && values.size() > 0) {
            return values.get(0).getValue();
        }
        return "";
    }


//    public void cleanLoginPwd() {
//        saveValue(AppConstans.Sp.CLOUD_ACCOUNT_PWD, "");
//    }
//
//    /**
//     * 保存用户登录密码
//     */
//    public void saveLoginPwd(String pwd) {
//        saveValue(AppConstans.Sp.CLOUD_ACCOUNT_PWD, pwd);
//    }
//
//    /**
//     * 获取用户登录密码
//     *
//     * @return
//     */
//    public String getLoginPwd() {
//        return getValue(AppConstans.Sp.CLOUD_ACCOUNT_PWD);
//    }

    /**
     * 保存用户登录账号
     */
    public void saveLoginPhone(String phone) {
        saveValue(AppConstans.Sp.CLOUD_ACCOUNT_PHONE, phone);
    }

    /**
     * 获取用户登录账号
     *
     * @return
     */
    public String getLoginPhone() {
        return getValue(AppConstans.Sp.CLOUD_ACCOUNT_PHONE);
    }

    public String getRefreshToken() {
        return getValue(AppConstans.Sp.REFRESH_TOKEN);
    }

    public void clearRefreshToken() {
        saveValue(AppConstans.Sp.REFRESH_TOKEN, "");
    }

    //参数请写在AppConstans.CountdownTimeKey中，避免重复
    public void saveCountdownTime(int left, @Nullable String key) {
        SpfUtils.put(key, System.currentTimeMillis() / 1000 + "-" + left);
    }

    //参数请写在AppConstans.CountdownTimeKey中，避免重复
    public int getCountdownTime(@Nullable String key) {
        return getCountdownTime(key, AppConstans.Common.REGISTER_CODE_TIME);
    }

    public int getCountdownTime(@Nullable String key, int totalTime) {
        String lastStr = (String) SpfUtils.get(key, "");
        if (TextUtils.isEmpty(lastStr)) {
            return totalTime;
        }
        try {
            String[] split = lastStr.split("-");
            int lastExitTime = 0;
            if (0 < split.length) {
                lastExitTime = Integer.valueOf(split[0]);
            }
            int lastLef = 0;
            if (1 < split.length) {
                lastLef = Integer.valueOf(split[1]);
            }
            int now = (int) (System.currentTimeMillis() / 1000);

            int now2Last = now - lastExitTime;
            if (lastLef > now2Last) {
                return lastLef - now2Last;
            }

            return totalTime;

        } catch (Exception e) {
            LogUtils.debug("getRegisterCodeTime exception: " + e);
        }
        return totalTime;
    }

    public void saveFamilyId(String fid) {
        saveValue(getValue(AppConstans.Sp.CLOUD_ACCOUNT_UID) + AppConstans.Sp.FAMILY_ID, fid);
    }

    public String getFamilyId() {
        String fid = getValue(getValue(AppConstans.Sp.CLOUD_ACCOUNT_UID) + AppConstans.Sp.FAMILY_ID);
        return fid;
    }

}
