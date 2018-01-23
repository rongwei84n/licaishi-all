package com.auts.lcssv.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.auts.lcssv.R;
import com.auts.lcssv.adapter.HouseAdapter;

/**
 *
 * @author weiming.zeng
 * @date 2017/8/8
 */

public class HouseViewHolder extends RecyclerView.ViewHolder {

    private ImageView img;
    private ImageView chose;

    public HouseViewHolder(View itemView, final ChooseHousePicListener listener) {
        super(itemView);
        img = (ImageView) itemView.findViewById(R.id.iv_house);
        chose = (ImageView) itemView.findViewById(R.id.iv_chose);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.chosePic(getAdapterPosition());
            }
        });
    }

    public void bindView(HouseAdapter.LoadImgCallBack callBack) {
        callBack.loadImg(img, chose);
    }

    public interface ChooseHousePicListener {
        void chosePic(int position);
    }
}
