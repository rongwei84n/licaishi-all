package com.auts.lcssv.bean.litebean;

import org.litepal.annotation.Column;
import org.litepal.annotation.Encrypt;
import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * 将原本保存在SharedPreferences中的关键数据以键值对的形式保存到数据库中
 * 防止清除缓存造成的关键数据丢失。
 * 其中type不可为空且其值唯一，其意义相当于SharedPreferences中的key，value相当于SP中value
 *
 * @author xiaolei.yang
 * @date 2017/7/27
 */

public class DataBean extends DataSupport implements Serializable {
    private static final long serialVersionUID = 8890845444750467488L;

    /**
     * 目前已使用的值：
     * AppConstants.Sp.ACCESS_TOKEN = "ACCESS_TOKEN";//token
     * AppConstants.Sp.REFRESH_TOKEN = "REFRESH_TOKEN";
     * AppConstants.Sp.CLOUD_ACCOUNT_UID = "CLOUD_ACCOUNT_UID";//uid
     * AppConstants.Sp.FAMILY_ID = "_FAMILY_ID";//familyId使用前，动态获取UID拼接到该值前面作为key
     * AppConstants,Sp.TIMESTAMP = "TIMESTAMP";//时间戳
     *
     * AppConstants,Sp.CLOUD_ACCOUNT_PHONE = "CLOUD_ACCOUNT_PHONE";//用户登录账号
     * AppConstants,Sp.CLOUD_ACCOUNT_PWD = "CLOUD_ACCOUNT_PWD";//用户登录密码
     * 使用新值时需要在此备注，防止使用重复值。
     */
    @Column(unique = true, nullable = false)
    private String type;
    @Encrypt(algorithm = AES)
    private String value;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "DataBean{" +
                "type='" + type + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
