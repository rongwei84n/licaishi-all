package com.auts.lcssv.adapter.holder;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.auts.lcssv.R;
import com.auts.lcssv.activity.SceneEditActivity;
import com.auts.lcssv.activity.SettingActivity;
import com.auts.lcssv.bean.MissionBean;
import com.auts.lcssv.util.EditTextUtils;

/**
 * 场景任务
 *
 * @author weiming.zeng
 * @date 2017/9/8
 */

public class MissionViewHolder extends CommonViewHolder {

    private TextView room;
    private TextView device;
    private TextView state;
    private TextView socket;
    private ImageView remove;


    public MissionViewHolder(Context context, View view) {
        super(context, view);
        room = (TextView) view.findViewById(R.id.tv_room_name);
        device = (TextView) view.findViewById(R.id.tv_device_name);
        socket = (TextView) view.findViewById(R.id.tv_socket_name);
        state = (TextView) view.findViewById(R.id.tv_status);
        state.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        remove = (ImageView) view.findViewById(R.id.iv_remove);

    }

    @Override
    public void bindView(RecyclerView.Adapter adapter, Object o) {
        final MissionBean missionBean = (MissionBean)o;
        socket.setText("_" + missionBean.getSocket_name());
        device.setFilters(new InputFilter[] {getMaskLenthFilter(12, "...")});
        device.setText(missionBean.getDevice_name());
        room.setFilters(new InputFilter[] {EditTextUtils.getMaskLenthFilter(8, "...")});
        room.setText(missionBean.getRoom_name());
        state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingActivity.actionStartActivity(context, missionBean.getTask_act() != 0, getAdapterPosition());
            }
        });
        if (missionBean.getTask_act() == 0) {
            state.setText("关");
            state.setTextColor(context.getResources().getColor(R.color.text_oringe));
        } else {
            state.setText("开");
            state.setTextColor(context.getResources().getColor(R.color.green));
        }
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SceneEditActivity)context).removeMission(missionBean);
            }
        });
    }

    public static InputFilter getMaskLenthFilter(final int maxLen, final String mask) {
        return new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                int count = 0;
                int index = 0;
                while (count <= maxLen && index < source.length()) {
                    char c = source.charAt(index++);
                    if (c < 128) {
                        count += 1; //字符
                    } else {
                        count += 2; //汉字
                    }
                }
                if (count > maxLen) { //超过限制字数
                    return source.subSequence(0, index - 1) + mask;
                }
                return source.subSequence(0, index) ;
            }
        };
    }
}
