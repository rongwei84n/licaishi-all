package com.auts.lcscli.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.auts.lcscli.R;
import com.auts.lcscli.util.EditTextUtils;
import com.auts.lcscli.util.KeyboardUtils;

/**
 * 带下划线、删除符号、眼睛符号的编辑框
 * Created by qisheng.lv on 2017/7/24.
 */
public class MyEditText extends LinearLayout {
    private NullMenuEditText mEtContent;
    private View mViewLine;
    private ImageView mIvDelete;
    private ImageView mIvEye;
    private boolean hasEye;

    private boolean isPhone;
    private boolean hasDelete;//是否有“清除全部按钮”
    private boolean isHide;
    private boolean blankEye;//空字符串时是否显示眼睛图标
    private boolean judgeContent; //是否需要根据文本是否有文字更改下划线颜色
    private boolean mHasContent;

    private ContentFocusChangeListener mContentFocusChangeListener;


    public MyEditText(Context context) {
        this(context, null);
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public MyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
        this.setLongClickable(false);
    }

    private void initView(Context context, AttributeSet attrs) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_my_edittext, this);

        mEtContent = (NullMenuEditText) view.findViewById(R.id.et_content);
        mViewLine = view.findViewById(R.id.view_line);
        mIvDelete = (ImageView) view.findViewById(R.id.iv_delete);
        mIvEye = (ImageView) view.findViewById(R.id.iv_eye);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyEditTextAttr);
        int inputType = ta.getInt(R.styleable.MyEditTextAttr_input_type, InputType.TYPE_CLASS_TEXT);
        int maxLength = ta.getInt(R.styleable.MyEditTextAttr_max_length, 20);
        hasEye = ta.getBoolean(R.styleable.MyEditTextAttr_has_eye, false);
        float lineMarginTop = ta.getDimension(R.styleable.MyEditTextAttr_line_margin_top, context.getResources().getDimension(R.dimen.line_margin_top));
        String hint = ta.getString(R.styleable.MyEditTextAttr_hint);
        isPhone = ta.getBoolean(R.styleable.MyEditTextAttr_is_phone, false);
        hasDelete = ta.getBoolean(R.styleable.MyEditTextAttr_has_delete, true);
        boolean allowBlank = ta.getBoolean(R.styleable.MyEditTextAttr_allow_blank, true);
        boolean allowChinese = ta.getBoolean(R.styleable.MyEditTextAttr_allow_blank, false);
        if (isPhone) {
            maxLength += 2;
        }
        blankEye = ta.getBoolean(R.styleable.MyEditTextAttr_blank_eye, false);
        judgeContent = ta.getBoolean(R.styleable.MyEditTextAttr_judge_content, false);

        ta.recycle();

//        mEtContent.setFilters(hasnew InputFilter[]{hasnew InputFilter.LengthFilter(maxLength)});
        EditTextUtils.setInputLength(maxLength, mEtContent);
        EditTextUtils.setBlankChineseInputFilter(mEtContent, allowBlank, allowChinese);

        if (!blankEye) {
            mIvEye.setVisibility(hasEye ? View.VISIBLE : View.GONE);
        }

        mEtContent.setHint(hint);
        mEtContent.setHintTextColor(getResources().getColor(R.color.text_hint));
        setInType(inputType);

        LinearLayout.LayoutParams lp = (LayoutParams) mViewLine.getLayoutParams();
        lp.setMargins(0, (int) lineMarginTop, 0, 0);
        mViewLine.setLayoutParams(lp);

        mEtContent.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (judgeContent) {
                    if (mHasContent) {
                        setViewLineBlack();
                    } else {
                        setViewLineGray();
                    }
                } else {
                    if (hasFocus) {
                        mViewLine.setBackgroundColor(getResources().getColor(R.color.focused_line));
                        setSelectionEnd();

                        if (hasDelete && !TextUtils.isEmpty(mEtContent.getText())) {
                            mIvDelete.setVisibility(VISIBLE);
                        } else {
                            mIvDelete.setVisibility(INVISIBLE);
                        }
                    } else {
                        mViewLine.setBackgroundColor(getResources().getColor(R.color.default_line));
                        mIvDelete.setVisibility(INVISIBLE);
                    }

                }
                if (mContentFocusChangeListener != null) {
                    mContentFocusChangeListener.onFocusChange(hasFocus);
                }
            }
        });

        mEtContent.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId) {
                    case EditorInfo.IME_ACTION_DONE:
                        mEtContent.clearFocus();
                        KeyboardUtils.hideSoftInputFromWindow(mEtContent);
                        break;

                    default:
                        break;
                }
                return true;
            }
        });


        mEtContent.addTextChangedListener(new NewTextWatcher());

        mIvDelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mEtContent.setText("");
            }
        });

        isHide = mEtContent.getInputType() == InputType.TYPE_TEXT_VARIATION_PASSWORD;

        mIvEye.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isHide) {
                    mIvEye.setImageResource(R.drawable.look_visible);
                    mEtContent.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    mIvEye.setImageResource(R.drawable.look);
                    mEtContent.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                isHide = !isHide;

                //更换inputType的方式会导致hint文字中的数字和符号略微变化
//                boolean textType = mEtContent.getInputType() != (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
//                mIvEye.setImageResource(textType ? R.drawable.look : R.drawable.look_visible);
//                mEtContent.setInputType(textType ? InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD :
//                        InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

                mEtContent.requestFocus();
                mEtContent.requestFocusFromTouch();
                setSelectionEnd();

            }
        });

    }

    private boolean b;

    public void addContentFocusChangeListener(ContentFocusChangeListener listener) {
        this.mContentFocusChangeListener = listener;
    }

    private class NewTextWatcher implements TextWatcher {

        private int lastLen;//字符串最终的长度
        private String lastString = "";//最终的字符串
        private int myEnd;
        private StringBuilder sb;

        String str = "";//编辑框中的字符串
        String strTrim = "";//编辑框中字符串去除空格后的字符串
        int len;//strTrim的长度

        @Override
        public void afterTextChanged(Editable s) {
            String content = mEtContent.getText().toString();
            if (hasDelete) {
                mIvDelete.setVisibility(mEtContent.hasFocus() && !TextUtils.isEmpty(content) ? View.VISIBLE : View.INVISIBLE);
            } else {
                mIvDelete.setVisibility(View.GONE);
            }

            mHasContent = !TextUtils.isEmpty(content);

            if (blankEye) {
                if (TextUtils.isEmpty(s)) {
                    mIvEye.setVisibility(View.GONE);
                } else {
                    mIvEye.setVisibility(hasEye ? View.VISIBLE : View.GONE);
                }
            }

            if (judgeContent) {
                if (mHasContent) {
                    setViewLineBlack();
                } else {
                    setViewLineGray();
                }
                if (hasEye) {
                    mIvEye.setVisibility(View.VISIBLE);
                }
            }

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s == null || s.length() == 0) {
                return;
            }
            if (isPhone) {
                if (b) {
                    lastLen = mEtContent.getText().toString().replaceAll(" ", "").length();
                    b = false;
                    return;
                }
                sb = new StringBuilder();
                myEnd = mEtContent.getSelectionEnd();
                str = mEtContent.getText().toString();
                strTrim = str.replaceAll(" ", "");
                sb.append(strTrim);
                len = strTrim.length();

                if (len == lastLen) {
                    if (len == 1) {
                        textAddAction();
                    } else {
                        int index = mEtContent.getSelectionStart();
                        if (index == 0) {
                            textAddAction();
                        } else {
                            Editable editable = mEtContent.getText();
                            editable.delete(index - 1, index);

                            sb = new StringBuilder();
                            myEnd = mEtContent.getSelectionEnd();
                            str = mEtContent.getText().toString();
                            strTrim = str.replaceAll(" ", "");
                            sb.append(strTrim);
                            len = strTrim.length();

                            textMinusAction();
                        }
                    }
                } else if (len > lastLen) {
                    textAddAction();
                } else {
                    textMinusAction();
                }

            }
        }

        private void textAddAction() {
            lastString = mEtContent.getText().toString();
            lastLen = len;
            int j = 0;
            for (int i = 0; i < len - 1; i++) {
                if (i == 2 || i == 6) {
                    j++;
                    sb.insert(i + j, " ");
                    b = true;
                }
            }
            if (b) {
                mEtContent.setText(sb.toString());
                if (myEnd > 1) {
                    if (sb.length() > 0 && sb.charAt(myEnd - 1) == ' ') {
                        mEtContent.setSelection(myEnd + 1);
                    } else {
                        mEtContent.setSelection(myEnd);
                    }
                }

            }
        }

        private void textMinusAction() {
            lastString = mEtContent.getText().toString();
            lastLen = len;
            int j = 0;
            if (len == 3) {
                b = true;
                myEnd = 3;
            } else {
                for (int i = 0; i < len - 1; i++) {
                    if (i == 2 || i == 6) {
                        j++;
                        sb.insert(i + j, " ");
                        b = true;
                    }
                }
            }
            if (b) {
                mEtContent.setText(sb.toString());
                if (myEnd > 0 && lastString.charAt(myEnd - 1) == ' ') {
                    mEtContent.setSelection(myEnd - 1);
                } else {
                    mEtContent.setSelection(myEnd);
                }
            }
        }

    }


    public String getContent() {
        String content = mEtContent.getText().toString().trim();
        return isPhone ? content.replaceAll(" ", "") : content;
    }

    public String getAllContent() {
        return mEtContent.getText().toString();
    }

    public void setContent(String content) {
        if (content == null) {
            content = "";
        }
        mEtContent.setText(content);
    }

    private void setViewLineBlack() {
        mViewLine.setBackgroundColor(getResources().getColor(R.color.focused_line));
    }

    private void setViewLineGray() {
        mViewLine.setBackgroundColor(getResources().getColor(R.color.default_line));
    }

    public void setHint(String content) {
        mEtContent.setText(content);
    }

    public void setHint(int resId) {
        mEtContent.setHint(resId);
    }

    public void setHintColor(int color) {
        mEtContent.setHintTextColor(color);
    }

    public EditText getEt() {
        return mEtContent;
    }

    public ImageView getIvDelete() {
        return mIvDelete;
    }

    public ImageView getIvEye() {
        return mIvEye;
    }

    private void setInType(int type) {
        switch (type) {
            case 1:
                mEtContent.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;

            case 2:
                mEtContent.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                break;

            default:
                mEtContent.setInputType(InputType.TYPE_CLASS_TEXT);
        }

    }

    private void setSelectionEnd() {
        String content = mEtContent.getText().toString();
        if (!TextUtils.isEmpty(content)) {
            mEtContent.setSelection(content.length());
        }
    }

    public interface ContentFocusChangeListener {
        void onFocusChange(boolean hasFocus);
    }

}
