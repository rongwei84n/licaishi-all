package com.auts.lcscli.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.auts.lcscli.R;
import com.auts.lcscli.bean.MessageSettingBean;

/**
 *
 * @author weiming.zeng
 * @date 2017/8/22
 */

public class RemindSettingViewHolder extends RecyclerView.ViewHolder {

    private SwitchCompat trigger;
    private TextView deviceName;

    public RemindSettingViewHolder(View itemView, final OnChangeCheckableCallBack callBack) {
        super(itemView);
        trigger = (SwitchCompat) itemView.findViewById(R.id.trigger);
        deviceName = (TextView) itemView.findViewById(R.id.tv_remindtitle);
        trigger.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                callBack.onChangeCheckable(buttonView, isChecked, getAdapterPosition());
            }
        });
    }

    public void bindeView(MessageSettingBean messageSettingBean) {
        deviceName.setText(messageSettingBean.getDeviceName());
        trigger.setChecked(messageSettingBean.getState());
    }

    public interface OnChangeCheckableCallBack {
        void onChangeCheckable(CompoundButton buttonView, boolean isChecked, int position);
    }
}
