package com.auts.lcscli.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.auts.lcscli.R;
import com.auts.lcscli.activity.RemindSettingActivity;
import com.auts.lcscli.adapter.holder.RemindSettingViewHolder;
import com.auts.lcscli.bean.MessageSettingBean;

import java.util.List;

/**
 *
 * @author weiming.zeng
 * @date 2017/8/22
 */

public class RemindSettingAdapter extends RecyclerView.Adapter {

    private List<MessageSettingBean> model;
    private Context context;
    private int count;//有多少个打开的开关

    public RemindSettingAdapter(List<MessageSettingBean> model, Context context) {
        this.model = model;
        this.context = context;
        //初始化计算count，有多少个已打开状态的开关
        for (MessageSettingBean bean : model) {
            if (bean.getState()) {
//                this.count++;
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_remind_setting, null);
        return new RemindSettingViewHolder(view, new RemindSettingViewHolder.OnChangeCheckableCallBack() {
            @Override
            public void onChangeCheckable(CompoundButton buttonView, boolean isChecked, int position) {
                //如果子项有开启，则总开关开启
                if (isChecked) {
                    ((RemindSettingActivity)context).changeMainTrigger(true);
                    count++;
                } else {
                    count--;
                    if (count == 0) {
                        ((RemindSettingActivity)context).changeMainTrigger(false);
                    }
                }
                ((RemindSettingActivity)context).changeItemTrigger(model.get(position).getDevice_id(), isChecked);
                model.get(position).setState(isChecked);
            }
        });
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((RemindSettingViewHolder)holder).bindeView(model.get(position));
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public void closeAllReminding() {
        for (MessageSettingBean messageSetting : model) {
            messageSetting.setState(false);
        }
        notifyDataSetChanged();
    }

    public void openAllReminding() {
        for (MessageSettingBean messageSetting : model) {
            messageSetting.setState(true);
        }
        notifyDataSetChanged();
    }
}
