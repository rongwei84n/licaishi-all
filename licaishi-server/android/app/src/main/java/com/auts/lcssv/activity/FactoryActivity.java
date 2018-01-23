package com.auts.lcssv.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.auts.lcssv.R;
import com.auts.lcssv.adapter.FactoryInfoAdapter;
import com.auts.lcssv.base.BaseActivity;
import com.auts.lcssv.constants.AppConstans;
import com.auts.lcssv.util.SpfUtils;
import com.auts.lcssv.views.recyclerview.MyDecoration;
import com.auts.lcssv.zxing.CaptureActivity;
import com.auts.lcssv.zxing.Intents;

import butterknife.BindView;

/**
 * 工厂界面
 *
 * @author xiaolei.yang
 * @date 2017/7/25
 */

public class FactoryActivity extends BaseActivity {
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private final static int GO_CAPTURE_REQUEST_CODE = 3001;

    @Override
    public void initLayout(Bundle savedInstanceState) {
        setContentView(R.layout.activity_factory);
    }


    @Override
    public void afterInitView() {
        setPageTitle(getString(R.string.title_factory));
        intiRv();
    }

    private void intiRv() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        FactoryInfoAdapter mAdapter = new FactoryInfoAdapter(this);
        mRecyclerView.addItemDecoration(new MyDecoration(this, MyDecoration.VERTICAL_LIST));
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Object item = adapter.getData().get(position);
                String key = "";
                if (item instanceof Pair) {
                    key = (String) ((Pair) item).first;
                }

                if (key != null && key.equals(getString(R.string.h5_test))) {
                    gotoCapture();
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    private void gotoCapture() {
        Intent intent = new Intent(this, CaptureActivity.class);
        startActivityForResult(intent, GO_CAPTURE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GO_CAPTURE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            String urlCapture = data.getStringExtra(Intents.Scan.RESULT);
            if (!TextUtils.isEmpty(urlCapture)) {
                startActivity(new Intent(this, JsBridgeActivity.class).putExtra(AppConstans.Common.INTENT_URL, urlCapture));
            }
        } else {
            String urlSp = (String) SpfUtils.get("current_url", "http://geek.csdn.net/");
            startActivity(new Intent(this, JsBridgeActivity.class).putExtra(AppConstans.Common.INTENT_URL, urlSp));
        }
    }
}
