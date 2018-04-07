package com.auts.lcscli.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.auts.lcscli.R;
import com.auts.lcscli.activity.UpdateDetailActivity;
import com.auts.lcscli.adapter.holder.IntroductionViewHolder;
import com.auts.lcscli.bean.Introduction;

import java.util.List;

/**
 *
 * @author weiming.zeng
 * @date 2017/8/18
 */

public class IntroductionAdapter extends RecyclerView.Adapter {
    List<Introduction> model;
    Context context;

    public IntroductionAdapter(Context context, List<Introduction> model) {
        this.model = model;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_instruction_item, parent, false);
        return new IntroductionViewHolder(view, new IntroductionViewHolder.ContentCallBack() {
            @Override
            public void getContent(Introduction introduction) {
                UpdateDetailActivity.actionStartActivity(context, introduction);
            }
        });
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((IntroductionViewHolder) holder).bindView(model.get(position));
    }

    @Override
    public int getItemCount() {
        return model.size();
    }
}
