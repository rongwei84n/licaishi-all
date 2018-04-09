package com.auts.lcscli.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.auts.lcscli.R;
import com.auts.lcscli.base.BaseActivity;

import butterknife.BindView;

public class ProtocolActivity extends BaseActivity {
    @BindView(R.id.tv_protocol)
    TextView mTvProtocol;

    @Override
    public void initLayout(Bundle savedInstanceState) {
        setContentView(R.layout.activity_protocol);
    }

    @Override
    public void afterInitView() {
        setPageTitle(R.string.user_protocol);
        mTvProtocol.setText(
                "        《用户协议》（以下简称“本协议”）");
    }

}
