package com.auts.lcscli.adapter.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.auts.lcscli.R;
import com.auts.lcscli.activity.DeviceListActivity;
import com.auts.lcscli.bean.DeviceDetail;
import com.auts.lcscli.manager.imageloader.ImageLoader;
import com.auts.lcscli.util.CommonUtils;

/**
 *
 *
 * @author weiming.zeng
 * @date 2017/9/11
 */

public class DeviceItemViewHolder extends CommonViewHolder {

    private ImageView devicePic;
    private TextView deviceName;
    private TextView room;
    private View itemView;

    public DeviceItemViewHolder(Context context, View view) {
        super(context, view);
        itemView = view;
        deviceName = (TextView) view.findViewById(R.id.tv_device_name);
        devicePic = (ImageView) view.findViewById(R.id.iv_device);
        room = (TextView) view.findViewById(R.id.tv_result);
    }

    @Override
    public void bindView(RecyclerView.Adapter adapter, Object o) {
        final DeviceDetail deviceDetail = (DeviceDetail) o;
        deviceName.setText(deviceDetail.device_nickname);
        if (TextUtils.isEmpty(deviceDetail.room_name) || ((DeviceListActivity)context).isSelectRoom()) {
            room.setVisibility(View.GONE);
        } else {
            room.setVisibility(View.VISIBLE);
            room.setText(deviceDetail.room_name);
        }
        String devicePicUrl = CommonUtils.getPicUrlFromList(deviceDetail.device_pic_group.getGroup_pics(), "normal");
        ImageLoader.getLoader(context).load(devicePicUrl).into(devicePic);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }
}
