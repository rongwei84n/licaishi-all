package com.auts.lcscli.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.auts.lcscli.R;
import com.auts.lcscli.adapter.holder.MessageViewHolder;
import com.auts.lcscli.bean.MessageBean;

import java.util.List;

/**
 *
 * @author weiming.zeng
 * @date 2017/8/22
 */

public class MessageAdapter extends RecyclerView.Adapter {

    private List<MessageBean> model;
    private Context context;

    public MessageAdapter(Context context, List<MessageBean> model) {
        this.model = model;
        this.context = context;
    }

    public void refreshData(List data) {
        model = data;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_message_item, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MessageViewHolder)holder).bindView(model.get(position));
    }

    @Override
    public int getItemCount() {
        return model.size();
    }
}
