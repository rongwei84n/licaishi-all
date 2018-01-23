package com.auts.lcssv.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.auts.lcssv.R;
import com.auts.lcssv.adapter.HouseItemAdapter;
import com.auts.lcssv.base.BaseActivity;
import com.auts.lcssv.bean.HouseBean;
import com.auts.lcssv.presenter.HouseManagePresenter;
import com.auts.lcssv.presenter.viewback.HouseManageView;
import com.auts.lcssv.util.ToastUtil;

import java.util.List;

import butterknife.BindView;

/**
 * 家庭管理
 *
 * @author weiming.zeng
 * @date 2017/8/8
 */
public class HouseManageActivity extends BaseActivity {

    private HouseManagePresenter presenter;
    @BindView(R.id.rv_house)
    RecyclerView mRecyclerView;
    public static final int RESULTCODE = 1047;

    @Override
    public void initLayout(Bundle savedInstanceState) {
        setContentView(R.layout.activity_house_manage);
    }

    @Override
    public void afterInitView() {
        initPresenter();
        setPageTitle(R.string.house);
    }

    public void initRecyclerView(List<HouseBean> data) {
        mRecyclerView.setAdapter(new HouseItemAdapter(this, data));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    protected void initPresenter() {
        this.presenter = new HouseManagePresenter(this, new HouseManageView() {
            @Override
            public void getHouseDataSuccess(List<HouseBean> data) {
                //Adapter为空，说明是第一次进入
                if (null != mRecyclerView.getAdapter()) {
                    ((HouseItemAdapter)mRecyclerView.getAdapter()).refersh(data);
                } else {
                    initRecyclerView(data);
                }
            }

            @Override
            public void getHouseDataError(String result, String msg) {
                ToastUtil.show(msg);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getHouseData();
    }

    @Override
    public void onGoback() {
        setResult(RESULTCODE, new Intent());
        super.onGoback();
    }
}
