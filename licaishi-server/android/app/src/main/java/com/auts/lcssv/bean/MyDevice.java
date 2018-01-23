package com.auts.lcssv.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by qisheng.lv on 2017/8/9.
 */

public class MyDevice implements Serializable{

    private static final long serialVersionUID = 6214738266981053409L;

    public int has_next_page;

    public int total_nums;

    public int page;

    public int page_size;

    public String latest_timestamp;

    public List<DeviceDetail> devices;

}
