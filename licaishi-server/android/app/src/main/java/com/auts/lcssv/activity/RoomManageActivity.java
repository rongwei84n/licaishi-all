package com.auts.lcssv.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.auts.lcssv.R;
import com.auts.lcssv.adapter.RoomAdapter;
import com.auts.lcssv.base.BaseActivity;
import com.auts.lcssv.bean.RoomBean;
import com.auts.lcssv.constants.AppConstans;
import com.auts.lcssv.presenter.HouseManagePresenter;
import com.auts.lcssv.presenter.viewback.HouseManageView;
import com.auts.lcssv.util.ToastUtil;

import java.util.List;

import butterknife.BindView;

/**
 * 房间管理
 *
 * @author weiming.zeng
 * @date 2017/08/11
 */
public class RoomManageActivity extends BaseActivity {

    @BindView(R.id.rv_roomPic)
    RecyclerView mRecyclerView;
    private HouseManagePresenter presenter;
    private String familyId;
    public static final int requestCode = 1000;
    public static final int resultCode = 1001;

    @Override
    public void initLayout(Bundle savedInstanceState) {
        setContentView(R.layout.activity_room_manage);
    }

    @Override
    public void afterInitView() {
        if (null != getIntent().getStringExtra(AppConstans.Common.FAMILY_ID)) {
            familyId = getIntent().getStringExtra(AppConstans.Common.FAMILY_ID);
        } else {
            familyId = HouseEditActivity.familyId;
        }
        initPresenter();
        initData();
        setPageTitle(R.string.room_manage_title);


    }

    protected void initPresenter() {
        presenter = new HouseManagePresenter(this, new HouseManageView() {
            @Override
            public void getRoomDataSuccess(List<RoomBean> data) {
                if (mRecyclerView.getAdapter() != null) {
                    ((RoomAdapter) mRecyclerView.getAdapter()).refresh(data);
                }
                mRecyclerView.setAdapter(new RoomAdapter(RoomManageActivity.this, data));
                mRecyclerView.setLayoutManager(new GridLayoutManager(RoomManageActivity.this, 3));
            }

            @Override
            public void getRoomDataError(String result, String msg) {
                ToastUtil.show(msg);
            }

            @Override
            public void deleteRoomInfoSuccess() {
                startActivity(new Intent(RoomManageActivity.this, RoomManageActivity.class));
            }

            @Override
            public void deleteRoomInfoError(String result, String msg) {
                ToastUtil.show(msg);
            }
        });
    }

    private void initData() {
        presenter.getRoomData(familyId);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mRecyclerView.getAdapter() != null) {
            presenter.getRoomData(familyId);
        }
    }

    /**
     * @param context
     */
    public static void actionStartActivity(Context context, String familyId) {
        Intent intent = new Intent(context, RoomManageActivity.class);
        intent.putExtra(AppConstans.Common.FAMILY_ID, familyId);
        context.startActivity(intent);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        presenter.getRoomData(HouseEditActivity.familyId);
    }

    @Override
    public void onGoback() {
        setResult(RESULT_OK, new Intent());
        super.onGoback();
    }

}
