package com.auts.lcssv.views;

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

import com.auts.lcssv.R;
import com.auts.lcssv.util.EditTextUtils;
import com.auts.lcssv.util.KeyboardUtils;
import com.auts.lcssv.util.StringUtils;

/**
 * 带下划线、删除符号、眼睛符号的编辑框
 *
 * @author xiaolei.yang
 * @date 2017/11/1
 */
public class MySuperPasswordEditText extends LinearLayout {
    private NullMenuEditText mEtContent;
    private View mViewLine;
    private ImageView mIvDelete;
    private ImageView mIvEye;

    private boolean isHide;

    public MySuperPasswordEditText(Context context) {
        this(context, null);
    }

    public MySuperPasswordEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public MySuperPasswordEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
        this.setLongClickable(false);
    }

    private void initView(Context context, AttributeSet attrs) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_my_super_edittext, this);
        mEtContent = (NullMenuEditText) view.findViewById(R.id.et_content);
        mViewLine = view.findViewById(R.id.view_line);
        mIvDelete = (ImageView) view.findViewById(R.id.iv_delete);
        mIvEye = (ImageView) view.findViewById(R.id.iv_eye);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyEditTextAttr);
        int maxLength = ta.getInt(R.styleable.MyEditTextAttr_max_length, 20);
        float lineMarginTop = ta.getDimension(R.styleable.MyEditTextAttr_line_margin_top, context.getResources().getDimension(R.dimen.line_margin_top));
        String hint = ta.getString(R.styleable.MyEditTextAttr_hint);
        boolean allowBlank = ta.getBoolean(R.styleable.MyEditTextAttr_allow_blank, true);
        boolean allowChinese = ta.getBoolean(R.styleable.MyEditTextAttr_allow_blank, false);

        ta.recycle();

        EditTextUtils.setInputLength(maxLength, mEtContent);
        EditTextUtils.setBlankChineseInputFilter(mEtContent, allowBlank, allowChinese);

        mEtContent.setHint(hint);
        mEtContent.setHintTextColor(getResources().getColor(R.color.text_hint));
        mEtContent.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        LayoutParams lp = (LayoutParams) mViewLine.getLayoutParams();
        lp.setMargins(0, (int) lineMarginTop, 0, 0);
        mViewLine.setLayoutParams(lp);

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

        mEtContent.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    setSelectionEnd();
                    if (isEmpty()) {
                        setViewLineGray();
                        mIvDelete.setVisibility(INVISIBLE);
                    } else {
                        setViewLineBlack();
                        mIvDelete.setVisibility(VISIBLE);
                    }
                } else {
                    setViewLineGray();
                    mIvDelete.setVisibility(INVISIBLE);
                }
            }
        });


        mEtContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {

                if (!TextUtils.isEmpty(s) && StringUtils.isContainNonAscii(s.toString())) {
                    mEtContent.setText(StringUtils.replaceNonAsciiToNullString(s.toString()));
                    setSelectionEnd();
                    return;
                }

                if (isEmpty()) {
                    setViewLineGray();
                    mIvDelete.setVisibility(INVISIBLE);
                    mIvEye.setImageResource(R.drawable.look_visible);
                    mEtContent.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    if (mEtContent.hasFocus()) {
                        setViewLineBlack();
                        mIvDelete.setVisibility(VISIBLE);
                    } else {
                        setViewLineGray();
                        mIvDelete.setVisibility(INVISIBLE);
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

        });

        mIvDelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mEtContent.setText("");
            }
        });

        mIvEye.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int visibility = mIvDelete.getVisibility();
                setHide(isHide);
                isHide = !isHide;
                setSelectionEnd();
                mIvDelete.setVisibility(visibility);
            }
        });

    }

    public void setHide(boolean b) {
        isHide = b;
        if (isHide) {
            mIvEye.setImageResource(R.drawable.look_visible);
            mEtContent.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            mIvEye.setImageResource(R.drawable.look);
            mEtContent.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

    public String getContent() {
        return mEtContent.getText().toString().trim();
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

    public void setViewLineBlack() {
        mViewLine.setBackgroundColor(getResources().getColor(R.color.focused_line));
    }

    public void setViewLineGray() {
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

    private void setSelectionEnd() {
        String content = mEtContent.getText().toString();
        if (!TextUtils.isEmpty(content)) {
            mEtContent.setSelection(content.length());
        }
    }

    public boolean isEmpty() {
        return (TextUtils.isEmpty(mEtContent.getText()) || TextUtils.isEmpty(mEtContent.getText().toString().trim()));
    }


}
