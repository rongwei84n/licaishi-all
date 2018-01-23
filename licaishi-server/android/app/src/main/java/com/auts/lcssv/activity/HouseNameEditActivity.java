package com.auts.lcssv.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.auts.lcssv.R;
import com.auts.lcssv.base.BaseActivity;
import com.auts.lcssv.constants.AppConstans;
import com.auts.lcssv.util.EditTextUtils;
import com.auts.lcssv.util.RegexUtils;

import butterknife.BindView;

public class HouseNameEditActivity extends BaseActivity {

    @BindView(R.id.et_housename)
    EditText mEtHouseName;


    @Override
    public void initLayout(Bundle savedInstanceState) {
        setContentView(R.layout.activity_house_name_edit);
    }

    @Override
    public void afterInitView() {
        showTvMenu(R.string.save);
        setTvMenuColor(R.color.text_oringe);
        setPageTitle(R.string.housename_edit);
        EditTextUtils.setInputLengthAndCheckAscll(20, mEtHouseName);
        String houseName = getIntent().getStringExtra("houseName");
        if (!TextUtils.isEmpty(houseName)) {
            mEtHouseName.setText(houseName);
            mEtHouseName.setSelection(mEtHouseName.length());
        }
    }

    @Override
    public void onTvMenuClick(TextView view) {
        super.onTvMenuClick(view);
        //保存家庭名称成功，返回HouseEditActivity
        if (!RegexUtils.checkNameToast(mEtHouseName.getText().toString(), "家庭名称")) {
            return;
        } else {
            Intent intent = new Intent();
            intent.putExtra(AppConstans.Common.HOUSE_NAME, mEtHouseName.getText().toString());
            setResult(HouseEditActivity.requestCode_HouseNameEditActivity, intent);
            finish();
        }
    }

    public static void actionStartActivity(Context context, String param) {
        Intent intent = new Intent();
        intent.putExtra("param1", param);
        context.startActivity(intent);
    }
}
