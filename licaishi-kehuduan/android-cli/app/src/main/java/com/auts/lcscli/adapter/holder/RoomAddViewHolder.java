package com.auts.lcscli.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.auts.lcscli.R;

/**
 *
 * @author weiming.zeng
 * @date 2017/8/10
 */

public class RoomAddViewHolder extends RecyclerView.ViewHolder {

    private ImageView img;

    public RoomAddViewHolder(View itemView, final AddRoomListener listener) {
        super(itemView);
        img = (ImageView) itemView.findViewById(R.id.iv_addRoom);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.addRoom();
            }
        });
    }

    public void bindView() {
        img.setImageResource(R.drawable.jia);
    }

    public interface AddRoomListener {
        void addRoom();
    }
}
