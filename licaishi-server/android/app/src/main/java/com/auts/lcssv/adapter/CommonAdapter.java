package com.auts.lcssv.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.auts.lcssv.adapter.holder.CommonViewHolder;

import java.lang.reflect.Constructor;
import java.util.List;

/**
 *
 * @author weiming.zeng
 * @date 2017/9/5
 */

public class CommonAdapter<T, K extends CommonViewHolder> extends RecyclerView.Adapter {
    private Context context;
    private List<T> model;
    private Class<K> clazz;
    private RecyclerView.LayoutManager layoutManager;
    private OnClickItemListener mListener;

    @LayoutRes
    int itemResource;
    private boolean inflateParent = true;

    public CommonAdapter(Context context, List<T> model, @LayoutRes int itemResource, Class<K> clazz) {
        this.context = context;
        this.model = model;
        this.clazz = clazz;
        this.itemResource = itemResource;
    }

    public CommonAdapter(Context context, List<T> model, @LayoutRes int itemResource, Class<K> clazz, RecyclerView.LayoutManager layoutManager) {
        this.context = context;
        this.model = model;
        this.clazz = clazz;
        this.layoutManager = layoutManager;
        this.itemResource = itemResource;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        K viewHolder = null;
        final View itemView;
        if (inflateParent) {
            itemView = LayoutInflater.from(context).inflate(itemResource, parent, false);
        } else {
            itemView = LayoutInflater.from(context).inflate(itemResource, null);
        }
        if (mListener != null) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //暂时还没用到，先不实现
//                    mListener.onItemClick(CommonAdapter.this, itemView, get);
                }
            });
        }
        try {
            Constructor<K> constructor = clazz.getConstructor(Context.class, View.class);
            viewHolder = constructor.newInstance(context, itemView);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((K) holder).bindView(this, model.get(position));
    }


    @Override
    public int getItemCount() {
        if (model == null) {
            return 0;
        }
        return model.size();
    }

    public void refreshData(List<T> data) {
        model = data;
        notifyDataSetChanged();
    }

    public View getItemViewByPostion(int position) {
        if (layoutManager == null) {
            return null;
        }
        return layoutManager.findViewByPosition(position);
    }

    public void setInflateParent(boolean bool) {
        this.inflateParent = bool;
    }

    public interface OnClickItemListener {
        void onItemClick(CommonAdapter adapter, View view, int position);
    }

    public void setmListener(OnClickItemListener mListener) {
        this.mListener = mListener;
    }
}
