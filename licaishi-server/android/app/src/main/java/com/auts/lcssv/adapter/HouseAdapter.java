package com.auts.lcssv.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.auts.lcssv.R;
import com.auts.lcssv.activity.HouseEditActivity;
import com.auts.lcssv.activity.RoomAddActivity;
import com.auts.lcssv.adapter.holder.HouseViewHolder;
import com.auts.lcssv.bean.Picture;
import com.auts.lcssv.manager.imageloader.GlideRoundTransform;
import com.auts.lcssv.manager.imageloader.ImageLoader;

import java.util.List;

/**
 *
 * @author zwm
 * @date 2017/8/6
 */

public class HouseAdapter extends RecyclerView.Adapter implements HouseViewHolder.ChooseHousePicListener {

    List<Picture> model;
    Context context;
    //选取的图片
    private int chosePosition;
    //是否是房间类型的图片
    private boolean isRoom;
    private String selectedUrl;

    public HouseAdapter(Context context, List<Picture> model, String selectedUrl, boolean isRoom) {
        this.model = model;
        this.context = context;
        if (TextUtils.isEmpty(selectedUrl)) {
            selectedUrl = model.get(model.size() - 1).getPic_url();//如果没有选择图片，默认最后一张
        }
        this.selectedUrl = selectedUrl;
        this.isRoom = isRoom;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.view_house, null);
        return new HouseViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ((HouseViewHolder)holder).bindView(new LoadImgCallBack() {
            @Override
            public void loadImg(ImageView img, ImageView selected) {
                ImageLoader.getLoader(context).load(model.get(position).getPic_url()).transform(new GlideRoundTransform(context, 10)).into(img);
                selected.setVisibility(View.GONE);
                //显示选中的图片
                if (model.get(position).getPic_url().equals(selectedUrl)) {
                    selected.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return model.size();
    }


    /**
     * 图片被选中后回调
     * @param position
     */
    @Override
    public void chosePic(int position) {
        if (position < 0 || position > model.size()) {
            return;
        }
        this.selectedUrl = model.get(position).getPic_url();
        notifyDataSetChanged();
        Intent intent = new Intent();
        intent.putExtra("position", position);
        intent.putExtra("url", model.get(position).getPic_url());
        if (isRoom) {
            ((Activity)context).setResult(RoomAddActivity.resultCode, intent);
            ((Activity) context).finish();
        } else {
            ((Activity)context).setResult(HouseEditActivity.requestCode_HousePicManageActivity, intent);
            ((Activity) context).finish();
        }

    }

    public interface LoadImgCallBack {
        void loadImg(ImageView img, ImageView selected);
    }
}
