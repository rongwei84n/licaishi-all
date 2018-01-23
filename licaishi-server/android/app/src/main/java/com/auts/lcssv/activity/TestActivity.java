package com.auts.lcssv.activity;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.auts.lcssv.R;
import com.auts.lcssv.base.BaseFragmentActivity;
import com.auts.lcssv.util.EditTextUtils;

/**
 * @author xiaolei.yang
 */
public class TestActivity extends BaseFragmentActivity {

    @Override
    public void initLayout(Bundle savedInstanceState) {
        setContentView(R.layout.activity_test);
    }

    @Override
    public void afterInitView() {
        final TextView textView = (TextView) findViewById(R.id.text);
        textView.setFilters(new InputFilter[] {EditTextUtils.getMaskLenthFilter(6, "...")});
        Button button = (Button) findViewById(R.id.button11);
        final EditText editText = (EditText) findViewById(R.id.inputtext);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(editText.getText().toString());
            }
        });
    }



}
