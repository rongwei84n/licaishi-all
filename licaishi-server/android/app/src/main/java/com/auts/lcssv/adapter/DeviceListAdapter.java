package com.auts.lcssv.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.chad.library.adapter.base.BaseViewHolder;
import com.auts.lcssv.R;
import com.auts.lcssv.bean.DeviceDetail;
import com.auts.lcssv.bean.Picture;
import com.auts.lcssv.manager.imageloader.GlideRoundTransform;
import com.auts.lcssv.manager.imageloader.ImageLoader;
import com.auts.lcssv.util.EditTextUtils;
import com.auts.lcssv.views.recyclerview.BasePullUpDownAdapter;

import java.util.List;

/**
 * 账户已绑定的设备列表
 *
 * @author xiaolei.yang
 * @date 2017/7/13
 */

public class DeviceListAdapter extends BasePullUpDownAdapter<DeviceDetail, BaseViewHolder> {
    private Context mContext;
    private boolean mShowRoom;

    public DeviceListAdapter(Context context, @Nullable List<DeviceDetail> data) {
        super(R.layout.listitem_my_device, data);
        mContext = context;
    }

    @Override
    protected void convert(final BaseViewHolder holder, DeviceDetail item) {
        String deviceName = item.device_nickname;
        holder.setText(R.id.tv_name, EditTextUtils.getFilterContent(deviceName, 12));
        //如果现在选择的是“全部设备”，需要显示房间名称，否则不需要显示
        holder.setText(R.id.tv_room, mShowRoom ? EditTextUtils.getFilterContent(item.room_name, 8) : "");
        boolean isOnline = item.device_online_status == 1;
        holder.setTextColor(R.id.tv_name, isOnline ? mContext.getResources().getColor(R.color.white) : mContext.getResources().getColor(R.color.outlin_text));
        holder.setVisible(R.id.tv_outline, !isOnline);
        //背景房间图
        ImageLoader.getLoader(mContext)
                .load(item.room_pic_url)
                .transform(new CenterCrop(mContext), new GlideRoundTransform(mContext, 7))
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        ImageLoader.getLoader(mContext)
                                .load(R.drawable.default_room)
                                .transform(new CenterCrop(mContext), new GlideRoundTransform(mContext, 7))
                                .into((ImageView) holder.getView(R.id.iv_bg));
                        return true;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        return false;
                    }
                })
                .into((ImageView) holder.getView(R.id.iv_bg));
//        Bitmap src = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.house_example);
//        RoundCircleDrawable drawable = new_icon RoundCircleDrawable(src);
//        drawable.setRound(10);
//        holder.getView(R.id.iv_bg).setBackground(drawable);

        //设备图，还要区分设备是否在线，以设置不同的状态图片
        String dUrlOnline = "";
        String dUrlOffline = "";
        if (item.device_pic_group != null && item.device_pic_group.getGroup_pics() != null) {
            List<Picture> groupPics = item.device_pic_group.getGroup_pics();
            if (groupPics != null && groupPics.size() > 0) {
                for (Picture pictrue : groupPics) {
                    if (pictrue.getPic_type().equals("home_online")) {
                        dUrlOnline = pictrue.getPic_url();
                    }
                    if (pictrue.getPic_type().equals("home_offline")) {
                        dUrlOffline = pictrue.getPic_url();
                    }
                }
            }
        }

        //设备图标
        ImageLoader.getLoader(mContext)
                .load(isOnline ? dUrlOnline : dUrlOffline)
                .placeholder(R.drawable.default_paicha)
                .into((ImageView) holder.getView(R.id.iv_device));

        (holder.getView(R.id.view_over)).setBackgroundResource(isOnline ? R.drawable.shape_over : R.drawable.shape_over_offline);
    }

    public void setShowRoom(boolean showRoom) {
        mShowRoom = showRoom;
    }
}
