package com.auts.lcssv.presenter;

import android.text.TextUtils;

import com.auts.lcssv.R;
import com.auts.lcssv.base.BasePresenter;
import com.auts.lcssv.bean.HouseBean;
import com.auts.lcssv.bean.Picture;
import com.auts.lcssv.bean.RoomBean;
import com.auts.lcssv.manager.AccountManager;
import com.auts.lcssv.model.HouseManageModel;
import com.auts.lcssv.net.callback.BaseCallback;
import com.auts.lcssv.net.callback.ListCallback;
import com.auts.lcssv.presenter.viewback.HouseManageView;
import com.auts.lcssv.presenter.viewback.ILoadingView;

import java.util.List;

import okhttp3.Request;

/**
 * Created by weiming.zeng on 2017/8/8.
 */

public class HouseManagePresenter extends BasePresenter {

    private HouseManageModel model;
    private HouseManageView view;

    public static final String FAMILY_PIC = "family";
    public static final String ROOM_PIC = "room";

    public HouseManagePresenter(ILoadingView loadingView, HouseManageView view) {
        this.mILoadingView = loadingView;
        this.model = new HouseManageModel();
        this.view = view;
    }

    public void getHouseData() {
        showLoading(R.string.empty);
        model.getHouseData(new ListCallback<HouseBean>() {
            @Override
            public void onError(String code, String msg) {
                hideLoading();
                view.getHouseDataError(code, msg);
            }

            @Override
            public void onSuccess(List<HouseBean> houseBeen) {
                hideLoading();
                view.getHouseDataSuccess(houseBeen);
            }
        });
    }

    public void modifyHouseInfo(String familyId, String familyPicUrl, String familyNickname, int position) {
        if (TextUtils.isEmpty(familyId)) {
            familyId = AccountManager.getInstance().getFamilyId();
        }
        showLoading(R.string.empty);
        model.modifyHouseInfo(familyId, familyPicUrl, familyNickname, position, new BaseCallback() {
            @Override
            public void onError(String code, String msg) {
                hideLoading();
                view.modifyHouseInfoError(code, msg);
            }

            @Override
            public void onSuccess(String result, Request request) {
                hideLoading();
                view.modifyHouseInfoSuccess(result);
            }

        });
    }

    public void getRoomData(String familyId) {
        if (TextUtils.isEmpty(familyId)) {
            familyId = AccountManager.getInstance().getFamilyId();
        }
        showLoading(0);
        model.getRoomData(familyId, new ListCallback<RoomBean>() {
            @Override
            public void onSuccess(List<RoomBean> t) {
                hideLoading();
                view.getRoomDataSuccess(t);
            }

            @Override
            public void onError(String code, String msg) {
                hideLoading();
                view.getRoomDataError(code, msg);
            }
        });
    }

    public void addRoomInfo(String familyId, String roomPicUrl, String roomNickName) {
        if (TextUtils.isEmpty(familyId)) {
            familyId = AccountManager.getInstance().getFamilyId();
        }
        showLoading(R.string.empty);
        model.addRoomInfo(familyId, roomPicUrl, roomNickName, new BaseCallback() {
            @Override
            public void onError(String code, String msg) {
                hideLoading();
                view.saveRoomInfoError(code, msg);
            }

            @Override
            public void onSuccess(String result, Request request) {
                hideLoading();
                view.saveRoomInfoSuccess(result);
            }
        });
    }


    public void modifyRoomInfo(String familyId, String roomId, String roomPicUrl, String roomNickName, int position) {
        if (TextUtils.isEmpty(familyId)) {
            familyId = AccountManager.getInstance().getFamilyId();
        }
        showLoading(R.string.empty);
        model.modifyRoomInfo(familyId, roomId, roomPicUrl, roomNickName, new BaseCallback() {
            @Override
            public void onError(String code, String msg) {
                hideLoading();
                view.saveRoomInfoError(code, msg);
            }

            @Override
            public void onSuccess(String result, Request request) {
                hideLoading();
                view.saveRoomInfoSuccess(result);
            }
        }, position);
    }

    public void deleteRoomInfo(String familyId, String roomId, String roomPicUrl, String roomNickName, int position) {
        if (TextUtils.isEmpty(familyId)) {
            familyId = AccountManager.getInstance().getFamilyId();
        }
        model.deleteRoomInfo(familyId, roomId, roomPicUrl, roomNickName, position, new BaseCallback() {
            @Override
            public void onError(String code, String msg) {
                hideLoading();
                view.deleteRoomInfoError(code, msg);
            }

            @Override
            public void onSuccess(String result, Request request) {
                view.deleteRoomInfoSuccess();
            }
        });
    }

    public void getPicData(String picType) {
        showLoading(R.string.empty);
        model.getPicData(picType, new ListCallback<Picture>() {

            @Override
            public void onSuccess(List<Picture> t) {
                hideLoading();
                view.getPicSuccess(t);
            }

            @Override
            public void onError(String code, String msg) {
                hideLoading();
                view.getPicError(code, msg);
            }
        });
    }
}
