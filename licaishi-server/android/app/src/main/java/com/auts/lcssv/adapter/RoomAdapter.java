package com.auts.lcssv.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.auts.lcssv.PhApplication;
import com.auts.lcssv.R;
import com.auts.lcssv.activity.RoomAddActivity;
import com.auts.lcssv.activity.RoomManageActivity;
import com.auts.lcssv.adapter.holder.RoomAddViewHolder;
import com.auts.lcssv.adapter.holder.RoomViewHolder;
import com.auts.lcssv.bean.RoomBean;
import com.auts.lcssv.event.AddDeviceSuccessEvent;
import com.auts.lcssv.manager.imageloader.GlideRoundTransform;
import com.auts.lcssv.manager.imageloader.ImageLoader;
import com.auts.lcssv.presenter.HouseManagePresenter;
import com.auts.lcssv.presenter.viewback.HouseManageView;
import com.auts.lcssv.util.ToastUtil;
import com.auts.lcssv.util.UmengUtil;
import com.auts.lcssv.views.CommonDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 房间管理adapter
 *
 * @author weiming.zeng
 * @date 2017/8/8
 */

public class RoomAdapter extends RecyclerView.Adapter implements RoomViewHolder.ChoosePicListener {

    private List<RoomBean> model;
    private Context context;
    private HouseManagePresenter presenter;
    private static final int TYPE_ADD = 1;
    private static final int TYPE_NORMAL = 2;
    private CommonDialog dialog;
    private RoomBean roomBean;

    public RoomAdapter(final Context context, List<RoomBean> model) {
        this.model = model;
        //为了让加号显示，要使得model增大一位
        model.add(new RoomBean());
        this.context = context;
        initDialog();
        presenter = new HouseManagePresenter((RoomManageActivity)context, new HouseManageView() {
            @Override
            public void deleteRoomInfoSuccess() {
                EventBus.getDefault().post(new AddDeviceSuccessEvent(null));
                context.startActivity(new Intent(context, RoomManageActivity.class));
            }

            @Override
            public void deleteRoomInfoError(String result, String msg) {
                ToastUtil.show(msg);
            }
        });
    }

    private void initDialog() {
        dialog = new CommonDialog(context, "确定要删除该房间吗?", "取消", "删除", null, null);
        dialog.getmNegativeBtn().setTextColor(PhApplication.getAppContext().getResources().getColor(R.color.red));
        dialog.setPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setNegativetiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.deleteRoomInfo(roomBean.getFamily_id(), roomBean.getRoom_id(), roomBean.getRoom_pic_url(), roomBean.getRoom_nickname(), roomBean.getPosition());
                dialog.dismiss();
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ADD) {
            View itemView = LayoutInflater.from(context).inflate(R.layout.view_room_add, parent, false);
            return new RoomAddViewHolder(itemView, new RoomAddViewHolder.AddRoomListener() {
                @Override
                public void addRoom() {
                    //添加房间
                    UmengUtil.onEvent(context, UmengUtil.ROOM_ADD_CLICK);
                    RoomAddActivity.actionStartActivity(context, null, RoomManageActivity.requestCode, true);
                }
            });
        }
        View itemView = LayoutInflater.from(context).inflate(R.layout.view_room, parent, false);
        return new RoomViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof RoomAddViewHolder) {
            ((RoomAddViewHolder) holder).bindView();
            return;
        }
        ((RoomViewHolder)holder).bindView(model.get(position), new HouseAdapter.LoadImgCallBack() {
            @Override
            public void loadImg(ImageView img, ImageView delete) {
//                if (position == model.size()) {
//
//                }
                ImageLoader.getLoader(context).load(model.get(position).getRoom_pic_url()).transform(new GlideRoundTransform(context, 8)).into(img);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        roomBean = model.get(position);
                        dialog.show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == model.size() - 1) {
            return TYPE_ADD;
        } else {
            return TYPE_NORMAL;
        }
    }

    @Override
    public void chosePic(int position) {
        //编辑房间
        UmengUtil.onEvent(context, UmengUtil.ROOM_EDIT_CLICK);
        RoomAddActivity.actionStartActivity(context, model.get(position), RoomManageActivity.requestCode, false);
    }

    public void refresh(List<RoomBean> data) {
        model = data;
        notifyDataSetChanged();
    }


}
