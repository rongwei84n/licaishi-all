package com.auts.lcssv.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.auts.lcssv.R;
import com.auts.lcssv.adapter.IntroductionAdapter;
import com.auts.lcssv.base.BaseActivity;
import com.auts.lcssv.bean.Introduction;
import com.auts.lcssv.presenter.AppPresenter;
import com.auts.lcssv.presenter.viewback.AppManageView;
import com.auts.lcssv.util.ToastUtil;

import java.util.List;

import butterknife.BindView;

public class IntroductionActivity extends BaseActivity {

    @BindView(R.id.rv_instruction)
    RecyclerView mRecyclerView;
    private AppPresenter presenter;

    @Override
    public void initLayout(Bundle savedInstanceState) {
        setContentView(R.layout.activity_instruction);
    }

    @Override
    public void afterInitView() {
        setPageTitle(R.string.function_introduction);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_instruction);
        initPresenter();
        presenter.getInstroduction();
    }

    protected void initPresenter() {
        presenter = new AppPresenter(this, new AppManageView() {
            @Override
            public void getIntroductionSuccess(List<Introduction> data) {
                mRecyclerView.setAdapter(new IntroductionAdapter(IntroductionActivity.this, data));
                mRecyclerView.setLayoutManager(new LinearLayoutManager(IntroductionActivity.this));
            }

            @Override
            public void getIntroductionError(String msg) {
                ToastUtil.show(msg);
            }
        });
    }


}
