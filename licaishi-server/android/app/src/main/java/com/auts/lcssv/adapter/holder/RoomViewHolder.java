package com.auts.lcssv.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.auts.lcssv.R;
import com.auts.lcssv.adapter.HouseAdapter;
import com.auts.lcssv.bean.RoomBean;
import com.auts.lcssv.util.EditTextUtils;

/**
 *
 * @author weiming.zeng
 * @date 2017/8/8
 */

public class RoomViewHolder extends RecyclerView.ViewHolder {

    private TextView mTvRoomName;
    private ImageView mIvRoomPic;
    private ImageView mIvDelete;

    public RoomViewHolder(View itemView, final ChoosePicListener listener) {
        super(itemView);
        mTvRoomName = (TextView) itemView.findViewById(R.id.tv_room_name);
        mTvRoomName.setFilters(new InputFilter[]{EditTextUtils.getMaskLenthFilter(8, "...")});//处理房间名称的掩码
        mIvRoomPic = (ImageView) itemView.findViewById(R.id.iv_room_image);
        mIvDelete = (ImageView) itemView.findViewById(R.id.iv_room_delete);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.chosePic(getAdapterPosition());
            }
        });
    }

    public interface ChoosePicListener {
        void chosePic(int position);
    }

    public void bindView(RoomBean bean, HouseAdapter.LoadImgCallBack callBack) {
        mTvRoomName.setText(bean.getRoom_nickname());
        callBack.loadImg(mIvRoomPic, mIvDelete);
    }
}
