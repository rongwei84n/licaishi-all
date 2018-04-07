package com.auts.lcscli.activity;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.auts.lcscli.R;
import com.auts.lcscli.bean.DevicePic;
import com.auts.lcscli.bean.Picture;

import java.util.List;

/**
 * Created by weiming.zeng on 2017/8/15.
 */

class DevicePicViewHolder extends RecyclerView.ViewHolder {
    private ImageView img;
    private List<Picture> pictures;
    private LoadImgCallBack callback;

    private String pic_url_normal;
    private String pic_url_select;

    public DevicePicViewHolder(View itemView, final LoadImgCallBack callBack) {
        super(itemView);
        img = (ImageView) itemView.findViewById(R.id.iv_device_pic);
        this.callback = callBack;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.loadImg(img, false, getAdapterPosition());
            }
        });
    }

    public void bindView(DevicePic bean) {
        if (bean == null || bean.getGroup_pics() == null) {
            return;
        }
        pictures = bean.getGroup_pics();
        for (Picture pic : pictures) {
            if ("unselected".equals(pic.getPic_type())) {
                pic_url_normal = pic.getPic_url();
                img.setTag(R.id.tag_normal, pic_url_normal);
            } else if ("selected".equals(pic.getPic_type())) {
                pic_url_select = pic.getPic_url();
                img.setTag(R.id.tag_select, pic_url_select);
            }
        }
        callback.loadImg(img, true, getAdapterPosition());
    }

    public interface LoadImgCallBack {
        void loadImg(ImageView img, boolean isLoad, int position);
    }
}
