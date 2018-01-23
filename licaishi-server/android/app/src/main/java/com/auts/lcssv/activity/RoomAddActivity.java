package com.auts.lcssv.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.auts.lcssv.R;
import com.auts.lcssv.base.BaseActivity;
import com.auts.lcssv.bean.RoomBean;
import com.auts.lcssv.constants.AppConstans;
import com.auts.lcssv.manager.imageloader.GlideCircleTransform;
import com.auts.lcssv.manager.imageloader.ImageLoader;
import com.auts.lcssv.presenter.HouseManagePresenter;
import com.auts.lcssv.presenter.viewback.HouseManageView;
import com.auts.lcssv.util.EditTextUtils;
import com.auts.lcssv.util.KeyboardUtils;
import com.auts.lcssv.util.LogUtils;
import com.auts.lcssv.util.RegexUtils;
import com.auts.lcssv.util.ToastUtil;
import com.auts.lcssv.util.UmengUtil;
import com.auts.lcssv.views.ItemBar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 房间添加和编辑
 *
 * @author weiming.zeng
 * @date 2017/08/11
 */
public class RoomAddActivity extends BaseActivity {

    @BindView(R.id.btn_save)
    Button mBtnSave;
    @BindView(R.id.et_room_name)
    EditText mEtRoomName;
    @BindView(R.id.ib_roomPic)
    ItemBar mIbRoomPic;
    private boolean isEdit; //判断是编辑房间还是添加房间

    private HouseManagePresenter presenter;
    private RoomBean roomBean;
    public static final int requestCode = 10003;
    public static final int resultCode = 1004;


    public static void actionStartActivity(Context context, RoomBean roomBean, int requestCode, boolean addRoom) {
        //编辑房间add_room应该是false，添加是true
        Intent intent = new Intent(context, RoomAddActivity.class);
        intent.putExtra("add_Room", addRoom);
        intent.putExtra(AppConstans.Common.ROOM_BEAN, roomBean);
        ((Activity) context).startActivityForResult(intent, requestCode);
    }

    @Override
    public void initLayout(Bundle savedInstanceState) {
        setContentView(R.layout.activity_room_add);
    }

    @Override
    public void afterInitView() {
        roomBean = (RoomBean) getIntent().getSerializableExtra(AppConstans.Common.ROOM_BEAN);
        EditTextUtils.setInputLengthAndCheckAscll(20, mEtRoomName);//设置允许输入的字符长度
        isEdit = getIntent().getBooleanExtra("add_Room", false);
        if (isEdit) {
            setPageTitle(R.string.add_room);
        } else {
            setPageTitle(R.string.room_edit_title);
        }
        initPresenter();
        if (roomBean == null) {
            //如果是添加房间，则使用默认图片
            Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.house_example);
            //设置为圆形
            RoundedBitmapDrawable roundImg = RoundedBitmapDrawableFactory.create(getResources(), image);
            roundImg.setCircular(true);
            mIbRoomPic.getmRight_img().setImageDrawable(roundImg);
            mIbRoomPic.getmRight_img().setVisibility(View.VISIBLE);
            return;
        }
        if (!TextUtils.isEmpty(roomBean.getRoom_nickname())) {
            mEtRoomName.setText(roomBean.getRoom_nickname());
            mEtRoomName.setSelection(mEtRoomName.length());
        }
        if (!TextUtils.isEmpty(roomBean.getRoom_pic_url())) {
            ImageLoader.getLoader(this).load(roomBean.getRoom_pic_url()).transform(new GlideCircleTransform(this)).into(mIbRoomPic.getmRight_img());
            mIbRoomPic.getmRight_img().setVisibility(View.VISIBLE);
        } else {
            mIbRoomPic.getmRight_img().setImageResource(R.drawable.house_example);
        }

    }

    protected void initPresenter() {
        presenter = new HouseManagePresenter(this, new HouseManageView() {
            @Override
            public void saveRoomInfoSuccess(String result) {
                if (isEdit) {
                    UmengUtil.onEvent(RoomAddActivity.this, UmengUtil.ROOM_ADD_SAVE_SUCCESS);
                } else {
                    UmengUtil.onEvent(RoomAddActivity.this, UmengUtil.ROOM_EDIT_SAVE_SUCCESS);
                }
                RoomBean resultRb = null;
                try {
                    resultRb = JSON.parseObject(result, RoomBean.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (resultRb != null && !TextUtils.isEmpty(resultRb.getRoom_id())) {
                    roomBean = resultRb;
                }
                LogUtils.debug("roomBean11: " + roomBean.toString());
                setResult(RoomManageActivity.resultCode, new Intent().putExtra(AppConstans.Common.ROOM_BEAN, roomBean));
                KeyboardUtils.hideSoftInputFromWindow(mEtRoomName);
                finish();
            }

            @Override
            public void saveRoomInfoError(String result, String msg) {
                if (isEdit) {
                    UmengUtil.onEvent(RoomAddActivity.this, UmengUtil.ROOM_ADD_SAVE_FAIL);
                } else {
                    UmengUtil.onEvent(RoomAddActivity.this, UmengUtil.ROOM_EDIT_SAVE_FAIL);
                }
                ToastUtil.show(msg);
                finish();
            }
        });
    }

    @OnClick(R.id.btn_save)
    public void save() {
        if (!RegexUtils.checkNameToast(mEtRoomName.getText().toString(), "房间名称")) {
            return;
        } else if (!getIntent().getBooleanExtra("add_Room", false)) {
            //编辑房间的情况
            presenter.modifyRoomInfo(roomBean.getFamily_id(), roomBean.getRoom_id(), roomBean.getRoom_pic_url(), mEtRoomName.getText().toString(), roomBean.getPosition());
        } else if (getIntent().getBooleanExtra("add_Room", false)) {
            //添加房间的情况

            if (roomBean == null) {
                UmengUtil.onEvent(RoomAddActivity.this, UmengUtil.ROOM_ADD_SAVE);
                presenter.addRoomInfo(HouseEditActivity.familyId, null, mEtRoomName.getText().toString());
            } else {
                UmengUtil.onEvent(RoomAddActivity.this, UmengUtil.ROOM_EDIT_SAVE);
                presenter.addRoomInfo(roomBean.getFamily_id(), roomBean.getRoom_pic_url(), mEtRoomName.getText().toString());
            }
        }
    }

    @OnClick(R.id.ib_roomPic)
    public void getRoomPic() {
        HousePicManageActivity.actionStartActivity(this, roomBean == null ? null : roomBean.getRoom_pic_url(), true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //从房间图片选择页面跳回，HousePicManageActivity
        if (resultCode == RoomAddActivity.resultCode) {
            if (roomBean == null) {
                roomBean = new RoomBean();
                roomBean.setFamily_id(HouseEditActivity.familyId);
            }
            roomBean.setPosition(data.getIntExtra("position", 0));
            roomBean.setRoom_pic_url(data.getStringExtra("url"));
            ImageLoader.getLoader(this).load(roomBean.getRoom_pic_url()).transform(new GlideCircleTransform(this)).into(mIbRoomPic.getmRight_img());
            mIbRoomPic.getmRight_img().setVisibility(View.VISIBLE);

        }

    }

    @Override
    public void onGoback() {
        super.onGoback();
        KeyboardUtils.hideSoftInputFromWindow(mEtRoomName);
    }
}
