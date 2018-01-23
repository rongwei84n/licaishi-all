package com.auts.lcssv.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * softAp模式下，获取到的设备周围的ssid列表
 *
 * @author xiaolei.yang
 * @date 2017/8/9
 */

public class DeviceSsidsBean implements Parcelable {

    private String key; // key的结构为ssid0:100:6，第一个为真正的key，第二个为信号强度，第三个为加密方式（0代表未加密）
    private String value;

    public DeviceSsidsBean() {
    }

    public DeviceSsidsBean(String key, String value) {
        this.key = key;
        this.value = value;
    }

    protected DeviceSsidsBean(Parcel in) {
        key = in.readString();
        value = in.readString();
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    //获取信号强度
    public int getStrength() {
        if (key != null) {
            String[] sps = key.split(":");
            if (sps.length > 2) {
                if (sps[1] == null) {
                    return 0;
                } else {
                    try {
                        return Integer.parseInt(sps[1]);
                    } catch (NumberFormatException e) {
                        return 0;
                    }
                }

            } else {
                return 0;
            }
        }
        return 0;
    }


    //获取加密方式
    public int getEncryptedType() {
        if (key != null) {
            String[] sps = key.split(":");
            if (sps.length > 2) {
                if (sps[2] == null) {
                    return -1;
                } else {
                    try {
                        return Integer.parseInt(sps[2]);
                    } catch (NumberFormatException e) {
                        return -1;
                    }
                }

            } else {
                return -1;
            }
        }
        return -1;

    }

    public static final Creator<DeviceSsidsBean> CREATOR = new Creator<DeviceSsidsBean>() {
        @Override
        public DeviceSsidsBean createFromParcel(Parcel in) {
            return new DeviceSsidsBean(in);
        }

        @Override
        public DeviceSsidsBean[] newArray(int size) {
            return new DeviceSsidsBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(key);
        dest.writeString(value);
    }
}
