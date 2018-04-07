package com.auts.lcscli.util;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.auts.lcscli.PhApplication;
import com.auts.lcscli.R;

/**
 *
 * @author qisheng.lv
 * @date 2017/7/24
 */

public class ViewUtils {

    public static void linkage(final TextView et1, final int n1, final EditText et2, final int n2, final TextView btn) {
        if (et1 != null) {
            et1.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    String phone = et1.getText().toString().trim();
                    String pwd = et2.getText().toString().trim();
                    btn.setEnabled(!TextUtils.isEmpty(phone) && phone.length() >= n1 && !TextUtils.isEmpty(pwd) && pwd.length() >= n2);
                }
            });
        }

        et2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String pwd = et2.getText().toString().trim();
                if (et1 != null) {
                    String phone = et1.getText().toString().trim();
                    btn.setEnabled(!TextUtils.isEmpty(phone) && phone.length() >= n1 && !TextUtils.isEmpty(pwd) && pwd.length() >= n2);
                } else {
                    btn.setEnabled(!TextUtils.isEmpty(pwd) && pwd.length() >= n2);
                }
            }
        });

    }

    public static void changeBtn(final TextView et1, final int n1, final EditText et2, final int n2, final TextView btn) {
        if (et1 != null) {
            et1.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    String phone = et1.getText().toString().trim();
                    String pwd = et2.getText().toString().trim();
                    boolean isEnable = !TextUtils.isEmpty(phone) && phone.length() >= n1 && !TextUtils.isEmpty(pwd) && pwd.length() >= n2;
                    selectorBtn(btn, isEnable);
                }
            });
        }

        et2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String pwd = et2.getText().toString().trim();
                if (et1 != null) {
                    String phone = et1.getText().toString().trim();
                    boolean isEnable = !TextUtils.isEmpty(phone) && phone.length() >= n1 && !TextUtils.isEmpty(pwd) && pwd.length() >= n2;
                    selectorBtn(btn, isEnable);
                } else {
                    boolean isEnable = !TextUtils.isEmpty(pwd) && pwd.length() >= n2;
                    selectorBtn(btn, isEnable);
                }
            }
        });
    }

    private static void selectorBtn(TextView btn, boolean enable) {
        btn.setEnabled(enable);
        if (enable) {
            ShadowUtil.setShadowSelectorBg(btn,
                    PhApplication.getAppContext().getResources().getColor(R.color.btn_disable_bg),
                    PhApplication.getAppContext().getResources().getColor(R.color.btn_enable_bg),
                    PhApplication.getAppContext().getResources().getColor(R.color.press_bg));
        } else {
            ShadowUtil.setShadowSelectorBg(btn, PhApplication.getAppContext().getResources().getColor(R.color.btn_disable_bg));
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px( float dpValue) {
        final float scale = PhApplication.getAppContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(float pxValue) {
        final float scale = PhApplication.getAppContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}
