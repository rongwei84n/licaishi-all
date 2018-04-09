package com.auts.lcscli.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.auts.lcscli.PhApplication;
import com.auts.lcscli.R;
import com.auts.lcscli.bean.MessageBean;
import com.auts.lcscli.manager.imageloader.ImageLoader;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author weiming.zeng
 * @date 2017/8/22
 */

public class MessageViewHolder extends RecyclerView.ViewHolder {

    private TextView mDeviceName;
    private TextView mContent;
    private TextView time;
    private ImageView mDevicePic;

    public MessageViewHolder(View view) {
        super(view);
        mDeviceName = (TextView) view.findViewById(R.id.tv_device_name);
        mContent = (TextView) view.findViewById(R.id.tv_result);
        time = (TextView) view.findViewById(R.id.tv_time);
        mDevicePic = (ImageView) view.findViewById(R.id.iv_device);
    }

    public void bindView(MessageBean bean) {
        mDeviceName.setText(bean.getDevice_name());
        mContent.setText(bean.getMsg_content());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date = new Date(bean.getCreate_time() * 1000);
        String dateStr = sdf.format(date);
        String today = sdf.format(new Date(System.currentTimeMillis()));
        if (!dateStr.equals(today)) {  //如果不是同一年
            dateStr = new SimpleDateFormat("yyyy年MM月dd日  HH:mm").format(date);
        } else {
            dateStr = new SimpleDateFormat("MM月dd日  HH:mm").format(date);   //同一年
        }
        time.setText(dateStr);
        ImageLoader.getLoader(PhApplication.getAppContext()).load(bean.getDevice_pic_url()).into(mDevicePic);
    }
}
