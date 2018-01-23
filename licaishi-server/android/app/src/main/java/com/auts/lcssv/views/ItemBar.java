package com.auts.lcssv.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.auts.lcssv.R;


/**
 * 通用选项条
 * Created by weiming.zeng on 2017/8/4.
 */

public class ItemBar extends RelativeLayout {
    View view;
    //左边的标题显示，靠边
    private TextView mLeft_title;
    //右边边的标题显示，靠箭头
    private TextView mRight_title;
    //左边的说明信息，靠图片
    private TextView mLeft_message;
    //右边的说明信息，靠图片
    private TextView mRight_message;
    //左边的图片
    private ImageView mLeft_img;

    public TextView getmLeft_title() {
        return mLeft_title;
    }

    public TextView getmRight_title() {
        return mRight_title;
    }

    public TextView getmLeft_message() {
        return mLeft_message;
    }

    public TextView getmRight_message() {
        return mRight_message;
    }

    //右边的图片
    private ImageView mRight_img;

    public String leftTitle;
    public SpannableString leftTitleSpan;
    public String rightTitle;
    public String leftMessage;
    public String rightMessage;
    public Drawable leftImg;
    public Drawable rightImg;
    public Drawable leftImgBackRound;
    public Drawable rightImgBackRound;

    public ItemBar(Context context) {
        super(context);
        initView();
    }

    public ItemBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        getAttrs(attrs);
        getView();
    }

    public ItemBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        getAttrs(attrs);
        getView();
    }

    private void initView() {
        view = View.inflate(getContext(), R.layout.view_itembar, this);
        mLeft_img = (ImageView) findViewById(R.id.iv_LeftImage);
        mRight_img = (ImageView) findViewById(R.id.iv_RightImage);
    }

    //获取自定义属性
    private void getAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ItemBar);
        leftTitle = typedArray.getString(R.styleable.ItemBar_left_title);
        rightTitle = typedArray.getString(R.styleable.ItemBar_right_title);
        leftMessage = typedArray.getString(R.styleable.ItemBar_left_message);
        rightMessage = typedArray.getString(R.styleable.ItemBar_right_message);
        leftImg = typedArray.getDrawable(R.styleable.ItemBar_left_image);
        rightImg = typedArray.getDrawable(R.styleable.ItemBar_right_image);
        leftImgBackRound = typedArray.getDrawable(R.styleable.ItemBar_left_image_background);
        rightImgBackRound = typedArray.getDrawable(R.styleable.ItemBar_right_image_background);
    }


    private void getView() {
        if (!TextUtils.isEmpty(leftTitle)) {
            mLeft_title = (TextView) findViewById(R.id.tv_LeftTitle);
            mLeft_title.setText(leftTitle);
            mLeft_title.setVisibility(View.VISIBLE);
        }
        if (leftTitleSpan != null) {
            mLeft_title = (TextView) findViewById(R.id.tv_LeftTitle);
            mLeft_title.setText(leftTitleSpan);
            mLeft_title.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(rightTitle)) {
            mRight_title = (TextView) findViewById(R.id.tv_RightTitle);
            mRight_title.setText(rightTitle);
            mRight_title.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(leftMessage)) {
            mLeft_message = (TextView) findViewById(R.id.tv_LeftMessage);
            mLeft_message.setText(leftMessage);
            mLeft_message.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(rightMessage)) {
            mRight_message = (TextView) findViewById(R.id.tv_RightMessage);
            mRight_message.setText(rightMessage);
            mRight_message.setVisibility(View.VISIBLE);
        }
        if (leftImg != null) {
            mLeft_img.setImageDrawable(leftImg);
            mLeft_img.setVisibility(VISIBLE);
        }
    }

    public void reset() {
        getView();
    }

    public void setLeftTitle(String leftTitle) {
        this.leftTitle = leftTitle;
    }

    public void setLeftTitle(SpannableString leftTitle) {
        this.leftTitleSpan = leftTitle;
    }

    public void setRightTitle(String rightTitle) {
        this.rightTitle = rightTitle;
    }

    public void setLeftMessage(String leftMessage) {
        this.leftMessage = leftMessage;
    }

    public void setRightMessage(String rightMessage) {
        this.rightMessage = rightMessage;
    }

    public void setLeftImg(Drawable leftImg) {
        this.leftImg = leftImg;
    }

    public void setRightImg(Drawable rightImg) {
        this.rightImg = rightImg;
    }

    public void setLeftImgBackRound(Drawable leftImgBackRound) {
        this.leftImgBackRound = leftImgBackRound;
    }

    public void setRightImgBackRound(Drawable rightImgBackRound) {
        this.rightImgBackRound = rightImgBackRound;
    }

    public ImageView getmLeft_img() {
        return mLeft_img;
    }

    public ImageView getmRight_img() {
        return mRight_img;
    }
}
