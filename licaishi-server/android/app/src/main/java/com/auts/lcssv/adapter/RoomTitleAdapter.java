package com.auts.lcssv.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.auts.lcssv.R;
import com.auts.lcssv.bean.RoomBean;
import com.auts.lcssv.util.EditTextUtils;

import java.util.ArrayList;

/**
 *
 * @author qisheng.lv
 * @date 2017/8/12
 */

public class RoomTitleAdapter extends BaseAdapter {
    private ArrayList<RoomBean> mData;
    private Context mContext;
    private String mRoomId;

    public RoomTitleAdapter(Context context, ArrayList<RoomBean> data, String roomId) {
        this.mContext = context;
        this.mData = data;
        this.mRoomId = roomId;
    }

    public void refreshData(ArrayList<RoomBean> newData) {
        this.mData = newData;
    }

    @Override
    public int getCount() {
        int count = mData == null ? 0 : mData.size();
        return count;
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.listitem_room_selecte, null);

        RoomBean roomBean = mData.get(position);

        TextView tv = (TextView) convertView.findViewById(R.id.tv_show);
        String rommName = roomBean.getRoom_nickname();
        tv.setText(EditTextUtils.getFilterContent(rommName, 8));
        tv.setTextColor(roomBean.getRoom_id().equals(mRoomId) ? mContext.getResources().getColor(R.color.text_oringe) : mContext.getResources().getColor(R.color.focused_line));
        return convertView;
    }

    public void setRoomId(String roomId) {
        this.mRoomId = roomId;
    }
}
