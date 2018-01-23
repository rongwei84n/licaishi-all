package com.auts.lcssv.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.auts.lcssv.R;
import com.auts.lcssv.bean.DeviceSsidsBean;
import com.auts.lcssv.views.recyclerview.BasePullUpDownAdapter;

import java.util.List;

/**
 * 设备周围ssid的列表
 *
 * @author xiaolei.yang
 * @date 2017/7/6
 */

public class WifiScanListAdapter extends BasePullUpDownAdapter<DeviceSsidsBean, BaseViewHolder> {

    public WifiScanListAdapter(@Nullable List<DeviceSsidsBean> data) {
        super(R.layout.activity_wifi_scan_list_item, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, DeviceSsidsBean item) {
        if (item != null) {
            holder.setText(R.id.tv_ssid, item.getValue());
            holder.setVisible(R.id.iv_wifi_encrypted, item.getEncryptedType() != 0);
            if (item.getStrength() < 60) {
                holder.setImageResource(R.id.iv_wifi_strength, R.drawable.wifi_strength_one);
            } else if (item.getStrength() >= 80) {
                holder.setImageResource(R.id.iv_wifi_strength, R.drawable.wifi_strength_three);
            } else {
                holder.setImageResource(R.id.iv_wifi_strength, R.drawable.wifi_strength_two);
            }
        }
    }


}
