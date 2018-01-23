package com.auts.lcssv.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.auts.lcssv.R;
import com.auts.lcssv.activity.HouseEditActivity;
import com.auts.lcssv.adapter.holder.HouseItemViewHolder;
import com.auts.lcssv.bean.HouseBean;
import com.auts.lcssv.constants.AppConstans;
import com.auts.lcssv.manager.imageloader.GlideCircleTransform;
import com.auts.lcssv.manager.imageloader.ImageLoader;
import com.auts.lcssv.util.SpfUtils;

import java.util.List;

/**
 *
 * @author weiming.zeng
 * @date 2017/8/9
 */

public class HouseItemAdapter extends RecyclerView.Adapter implements HouseItemViewHolder.HouseItemCallback {

    private Context context;
    private List<HouseBean> model;

    public HouseItemAdapter(Context context, List<HouseBean> data) {
        this.context = context;
        this.model = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.view_house_item, null);
        return new HouseItemViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((HouseItemViewHolder)holder).bindView(model.get(position));
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    @Override
    public void loadHouseImg(String url, ImageView imageView) {
        ImageLoader.getLoader(context).load(url).transform(new GlideCircleTransform(context)).into(imageView);
        imageView.setVisibility(View.VISIBLE);
    }

    public void refersh(List<HouseBean> data) {
        this.model = data;
        notifyDataSetChanged();
    }

    @Override
    public void onItemClick(HouseBean bean, ImageView shareImg, TextView shareText) {
        HouseEditActivity.actionStartActivity(context, bean, shareImg, shareText);
        SpfUtils.put(AppConstans.Sp.SELECTED_HOUSE, bean.getFamily_nickname());
    }
}
