package com.auts.lcscli.bean;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author qisheng.lv
 * @date 2017/8/9
 */

public class DeviceDetail implements Serializable{

    private static final long serialVersionUID = -8089651066641358234L;

    public String device_id;

    public String device_mac;

    public String device_nickname;

    public String fid;

    public String room_id;

    public String room_name;

    public String model;

    public String hardware_version;

    public String rom_version;

    public String position;

    public String task_remind;

    public int device_online_status;

    public List<H5Url> relative_h5urls;

    public String room_pic_url;

    public DevicePic device_pic_group;

}
