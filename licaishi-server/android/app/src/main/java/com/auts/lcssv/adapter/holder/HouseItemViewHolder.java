package com.auts.lcssv.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.auts.lcssv.R;
import com.auts.lcssv.bean.HouseBean;
import com.auts.lcssv.views.ItemBar;

/**
 *
 * @author weiming.zeng
 * @date 2017/8/9
 */

public class HouseItemViewHolder extends RecyclerView.ViewHolder {
    private ItemBar itemBar;
    private HouseItemCallback callback;

    public HouseItemViewHolder(View itemView, HouseItemCallback callback) {
        super(itemView);
        this.callback = callback;
        itemBar = (ItemBar) itemView.findViewById(R.id.ib_house_item);
    }

    public void bindView(final HouseBean bean) {
        itemBar.setLeftMessage(bean.getFamily_nickname());
        callback.loadHouseImg(bean.getFamily_pic_url(), itemBar.getmLeft_img());
        itemBar.reset();
        itemBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onItemClick(bean, itemBar.getmLeft_img(), itemBar.getmLeft_message());
            }
        });
    }

    public interface HouseItemCallback {
        void loadHouseImg(String url, ImageView imageView);

        void onItemClick(HouseBean bean, ImageView shareImg, TextView shareText);
    }
}
