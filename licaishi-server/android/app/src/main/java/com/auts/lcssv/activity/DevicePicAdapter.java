package com.auts.lcssv.activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.auts.lcssv.R;
import com.auts.lcssv.bean.DevicePic;
import com.auts.lcssv.manager.imageloader.ImageLoader;

import java.util.List;

/**
 * Created by weiming.zeng on 2017/8/15.
 */

class DevicePicAdapter extends RecyclerView.Adapter {
    private List<DevicePic> model;
    private Context context;
    private RecyclerView.LayoutManager layoutManager;
    //被选中图片的位置
    private int chose = -1;

    public DevicePicAdapter(Context context, List<DevicePic> data, RecyclerView.LayoutManager layoutManager) {
        this.model = data;
        this.context = context;
        this.layoutManager = layoutManager;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.view_devicepic, null);
        return new DevicePicViewHolder(itemView, new DevicePicViewHolder.LoadImgCallBack() {
            /**
             *
             * @param isLoad 是否在加载
             * @param position 图片位置
             */
            @Override
            public void loadImg(ImageView img, boolean isLoad, int position) {
                String gid = model.get(position).getPic_group_id();
                if (chose == position) {
                    ((DevicePicActivity) context).saveUrl((String) img.getTag(R.id.tag_normal), gid);
                    return; //如果点击已选中的图片则直接返回
                }
                if (!isLoad) {
                    //点击图片
                    ImageLoader.getLoader(context).load(img.getTag(R.id.tag_select)).into(img);
                    View view = layoutManager.findViewByPosition(chose);
                    if (null != view) { //将之前选中的图片置为未选中状态
                        ImageView lastSelect = (ImageView) view.findViewById(R.id.iv_device_pic);
                        ImageLoader.getLoader(context).load(lastSelect.getTag(R.id.tag_normal)).into(lastSelect);
                        chose = position;
                    }
                    ((DevicePicActivity) context).saveUrl((String) img.getTag(R.id.tag_normal), gid);
                } else {
                    //说明不是点击图片而是加载图片,通过Gid确认之前选择的图片
                    if (gid.equals(((DevicePicActivity) context).getGid())) {
                        ImageLoader.getLoader(context).load(img.getTag(R.id.tag_select)).into(img);
                        chose = position;
                        return;
                    }
                    ImageLoader.getLoader(context).load(img.getTag(R.id.tag_normal)).into(img);
                }
            }
        });
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((DevicePicViewHolder) holder).bindView(model.get(position));
    }

    @Override
    public int getItemCount() {
        return model.size();
    }
}
