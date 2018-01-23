package com.auts.lcssv.activity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.RelativeLayout;

import com.auts.lcssv.R;
import com.auts.lcssv.base.BaseActivity;
import com.auts.lcssv.bean.HouseBean;
import com.auts.lcssv.constants.AppConstans;
import com.auts.lcssv.manager.imageloader.GlideCircleTransform;
import com.auts.lcssv.manager.imageloader.ImageLoader;
import com.auts.lcssv.presenter.HouseManagePresenter;
import com.auts.lcssv.presenter.viewback.HouseManageView;
import com.auts.lcssv.util.SpfUtils;
import com.auts.lcssv.util.ToastUtil;
import com.auts.lcssv.views.ItemBar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 家庭管理
 *
 * @author weiming.zeng
 * @date 2017/08/11
 */
public class HouseEditActivity extends BaseActivity {

    @BindView(R.id.ib_housename)
    ItemBar mHouseName;
    @BindView(R.id.ib_houseImage)
    ItemBar mHouseImage;
    @BindView(R.id.ib_room_manage)
    ItemBar mHouseManage;

    private HouseManagePresenter presenter;
    public static final int requestCode_HousePicManageActivity = 2;
    public static final int requestCode_HouseNameEditActivity = 1;
    public static String familyId;
    private HouseBean houseBean;

    private String houseName;
    private String houseUrl;

    private int position;

    @Override
    public void initLayout(Bundle savedInstanceState) {
        setContentView(R.layout.activity_house_edit);
    }

    @Override
    public void afterInitView() {
        setPageTitle(R.string.edit_house);
        houseBean = (HouseBean) getIntent().getSerializableExtra(AppConstans.Common.HOUSE_BEAN);
        familyId = houseBean.getFamily_id();
        initPresenter();
        mHouseName.setLeftMessage(houseBean.getFamily_nickname());
        mHouseName.reset();
        mHouseName.getmLeft_message().setTextColor(getResources().getColor(R.color.focused_line));
        //给mHouseName设置新的布局属性
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mHouseName.getmLeft_message().getLayoutParams();
        params.removeRule(RelativeLayout.END_OF);
        params.addRule(RelativeLayout.LEFT_OF, R.id.iv_RightArrow);
        mHouseName.getmLeft_message().setLayoutParams(params);

        ImageLoader.getLoader(this).load(houseBean.getFamily_pic_url()).transform(new GlideCircleTransform(this)).into(mHouseImage.getmRight_img());
        mHouseImage.getmRight_img().setVisibility(View.VISIBLE);
        houseName = houseBean.getFamily_nickname();
        houseUrl = houseBean.getFamily_pic_url();
        position = houseBean.getPosition();
    }

    protected void initPresenter() {
        presenter = new HouseManagePresenter(this, new HouseManageView() {
            @Override
            public void modifyHouseInfoSuccess(String msg) {
                ToastUtil.show(msg);
                houseBean.setFamily_nickname(houseName);
                houseBean.setFamily_pic_url(houseUrl);
                houseBean.setPosition(position);
                mHouseName.setLeftMessage(houseName);
                mHouseName.reset();
                SpfUtils.put(AppConstans.Sp.SELECTED_HOUSE, houseName);
                ImageLoader.getLoader(HouseEditActivity.this).load(houseUrl).transform(new GlideCircleTransform(HouseEditActivity.this)).into(mHouseImage.getmRight_img());
            }

            @Override
            public void modifyHouseInfoError(String code, String msg) {
                ToastUtil.show(msg);
                houseName = houseBean.getFamily_nickname();
                houseUrl = houseBean.getFamily_pic_url();
                position = houseBean.getPosition();
            }
        });
    }

    @OnClick(R.id.ib_housename)
    public void editHouseName(ItemBar view) {
        Intent intent = new Intent(HouseEditActivity.this, HouseNameEditActivity.class);
        intent.putExtra(AppConstans.Common.HOUSE_PIC, getIntent().getStringExtra(AppConstans.Common.HOUSE_PIC));
        intent.putExtra("houseName", houseName);
        startActivityForResult(intent, HouseEditActivity.requestCode_HouseNameEditActivity);
    }

    @OnClick(R.id.ib_houseImage)
    public void editHousePic(ItemBar view) {
        HousePicManageActivity.actionStartActivity(this, houseBean.getFamily_pic_url(), false);
    }

    @OnClick(R.id.ib_room_manage)
    public void editRoomPic(ItemBar view) {
        RoomManageActivity.actionStartActivity(this, familyId);
    }

    public static void actionStartActivity(Context context, HouseBean houseBean, View shareView, View shareText) {
        Intent intent = new Intent(context, HouseEditActivity.class);
        intent.putExtra(AppConstans.Common.HOUSE_BEAN, houseBean);
        ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation((Activity) context,
                Pair.create(shareView, "shareImg"),
                Pair.create(shareText, "shareMsg"));
        context.startActivity(intent, transitionActivityOptions.toBundle());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == HouseEditActivity.requestCode_HouseNameEditActivity && null != data) {
            //修改房间名称
            houseName = data.getStringExtra(AppConstans.Common.HOUSE_NAME);
            presenter.modifyHouseInfo(familyId, houseBean.getFamily_pic_url(), data.getStringExtra(AppConstans.Common.HOUSE_NAME), houseBean.getPosition());
        } else if (resultCode == HouseEditActivity.requestCode_HousePicManageActivity && null != data) {
            //修改房间图片
            this.position = data.getIntExtra("position", 0);
            presenter.modifyHouseInfo(familyId, data.getStringExtra("url"), houseBean.getFamily_nickname(), position);
            houseUrl = data.getStringExtra("url");
        }
    }

}
