package com.auts.lcssv.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.auts.lcssv.R;
import com.auts.lcssv.adapter.HouseAdapter;
import com.auts.lcssv.base.BaseActivity;
import com.auts.lcssv.bean.Picture;
import com.auts.lcssv.presenter.HouseManagePresenter;
import com.auts.lcssv.presenter.viewback.HouseManageView;
import com.auts.lcssv.util.ToastUtil;

import java.util.List;

import butterknife.BindView;

/**
 *
 * 选取家庭图片页面
 *
 * @author weiming.zeng
 * @date 2017/8/8
 */

public class HousePicManageActivity extends BaseActivity {

    @BindView(R.id.rv_housePic)
    RecyclerView mRecyclerView;
    //用户所选择的图片url
    private String url;
    private boolean isRoom;

    private HouseManagePresenter presenter;

    @Override
    public void initLayout(Bundle savedInstanceState) {
        setContentView(R.layout.activity_house_pic_manage);
    }

    @Override
    public void afterInitView() {
        isRoom = getIntent().getBooleanExtra("isRoom", true);
        url = getIntent().getStringExtra("url");
        initPresenter();
        if (isRoom) {
            setPageTitle(R.string.select_room_image);
            presenter.getPicData(HouseManagePresenter.ROOM_PIC);
        } else {
            setPageTitle(R.string.choseHouseName_title);
            presenter.getPicData(HouseManagePresenter.FAMILY_PIC);
        }
    }

    protected void initPresenter() {
        presenter = new HouseManagePresenter(this, new HouseManageView() {
            @Override
            public void getPicSuccess(List<Picture> data) {
                mRecyclerView.setAdapter(new HouseAdapter(HousePicManageActivity.this, data, url, isRoom));
                mRecyclerView.setLayoutManager(new GridLayoutManager(HousePicManageActivity.this, 3));
                mRecyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void getPicError(String result, String msg) {
                ToastUtil.show(msg);
                mRecyclerView.setVisibility(View.GONE);
            }
        });
    }


    /**
     *
     * @param context
     * @param url 用户已经选择的图片url
     */
    public static void actionStartActivity(Activity context, String url, boolean isRoom) {
        Intent intent = new Intent(context, HousePicManageActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("isRoom", isRoom);
        if (isRoom) {
            context.startActivityForResult(intent, RoomAddActivity.requestCode);
        } else {
            context.startActivityForResult(intent, HouseEditActivity.requestCode_HousePicManageActivity);
        }
    }
}
