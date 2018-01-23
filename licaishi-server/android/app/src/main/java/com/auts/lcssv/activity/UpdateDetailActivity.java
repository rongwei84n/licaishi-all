package com.auts.lcssv.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.auts.lcssv.R;
import com.auts.lcssv.base.BaseActivity;
import com.auts.lcssv.bean.Introduction;
import com.auts.lcssv.util.ToastUtil;

import butterknife.BindView;

public class UpdateDetailActivity extends BaseActivity {

    @BindView(R.id.iv_content)
    TextView mTvContent;

    @Override
    public void initLayout(Bundle savedInstanceState) {
        setContentView(R.layout.activity_update_detail);
    }

    @Override
    public void afterInitView() {
        setPageTitle(R.string.update_detail);
        Introduction introduction = (Introduction) getIntent().getSerializableExtra("Introduction");
        if (introduction == null) {
            ToastUtil.show(R.string.retry);
        }
        mTvContent.setText(introduction.getIntroduction_content());
    }

    public static void actionStartActivity(Context context, Introduction introduction) {
        Intent intent = new Intent(context, UpdateDetailActivity.class);
        intent.putExtra("Introduction", introduction);
        context.startActivity(intent);
    }
}
