package com.auts.lcssv.fragment;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.auts.lcssv.BuildConfig;
import com.auts.lcssv.PhApplication;
import com.auts.lcssv.R;
import com.auts.lcssv.activity.JsBridgeActivity;
import com.auts.lcssv.activity.MainActivity;
import com.auts.lcssv.adapter.DeviceListAdapter;
import com.auts.lcssv.adapter.RoomTitleAdapter;
import com.auts.lcssv.base.BaseFragment;
import com.auts.lcssv.bean.DeviceDetail;
import com.auts.lcssv.bean.H5Url;
import com.auts.lcssv.bean.MyDevice;
import com.auts.lcssv.bean.RoomBean;
import com.auts.lcssv.bean.UserConfig;
import com.auts.lcssv.constants.AppConstans;
import com.auts.lcssv.event.AddDeviceSuccessEvent;
import com.auts.lcssv.manager.AccountManager;
import com.auts.lcssv.presenter.DevicesPresenter;
import com.auts.lcssv.presenter.HouseManagePresenter;
import com.auts.lcssv.presenter.viewback.DevicesView;
import com.auts.lcssv.presenter.viewback.HouseManageView;
import com.auts.lcssv.presenter.viewback.ILoadingView;
import com.auts.lcssv.util.LogUtils;
import com.auts.lcssv.util.NetworkUtils;
import com.auts.lcssv.util.RecyclerUtils;
import com.auts.lcssv.util.ShadowUtil;
import com.auts.lcssv.util.SpfUtils;
import com.auts.lcssv.util.ToastUtil;
import com.auts.lcssv.util.ViewUtils;
import com.auts.lcssv.views.refresh.RefreshLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnLongClick;


/**
 * 工作室.
 */
public class WorkroomFragment extends BaseFragment implements ILoadingView {
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.iv_menu)
    ImageView mIvMenu;
    @BindView(R.id.tv_menu)
    TextView mTvMenu;


    private View emptyView;
    private View errorView;
    private View roomNoDeviceView;
    public PopupWindow mPopupTitle;
    private Handler mHandler;

    private boolean mH5Test = false; //与前端联调时才打开

    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_workroom, null);
    }

    @Override
    public void afterInitView() {
        try {
            EventBus.getDefault().register(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mHandler = new Handler();
    }

    protected void initPresenter() {
    }

    /**
     * 模拟数据给H5调试
     *
     * @param url
     * @param deviceId
     */
    private void h5AddDevice(String url, String deviceId) {
    }

    private void setIsPush(List<DeviceDetail> devices) {    //如果存在需要提醒的设备，则总开关为打开，能收到推送
        for (DeviceDetail item : devices) {
            if ("1".equals(item.task_remind)) {
                SpfUtils.put("isAllowPush", true);
                return;
            }
        }
        SpfUtils.put("isAllowPush", false);
    }

    private void initRecyclerView() {
    }

    private void refreshRoomAndDevice(boolean showLoading) {
        LogUtils.debug("refreshRoomAndDevice");
    }

    private void getUserConfig() {
        showLoading();
    }

    private void getFidRooms() {
    }

    private void getMyDevices() {
    }

    @Override
    public void showLoadingDialog(int resId) {
//        showLoading(resId);
    }

    @Override
    public void hideLoadingDialog() {
//        hideLoading();
    }

    @Override
    public void updateLoadingMessage(String message) {
    }

    private void initPopWindow() {
    }

    @Subscribe
    public void onEventMainThread(AddDeviceSuccessEvent event) {
        LogUtils.debug("onEventMainThread: ");
        showLoading();
    }

    private void startTask(long delay) {
    }

    private void stopTask() {
    }

    @Override
    public void onResume() {
        super.onResume();
        startTask(0);
    }

    @Override
    public void onPause() {
        super.onPause();
        stopTask();
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }
}
