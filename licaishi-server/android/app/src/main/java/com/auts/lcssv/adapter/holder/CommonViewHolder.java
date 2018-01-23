package com.auts.lcssv.adapter.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 *
 * @author weiming.zeng
 * @date 2017/9/5
 */

public abstract class CommonViewHolder<T> extends RecyclerView.ViewHolder {
    protected Context context;

    public CommonViewHolder(Context context, View view) {
        super(view);
        this.context = context;
    }

    public abstract void bindView(RecyclerView.Adapter adapter, T t);
}
