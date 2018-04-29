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
    @BindView(R.id.tv_room_title)
    TextView mTvRoomTitle;
    @BindView(R.id.view_line)
    View mViewLine;
    @BindView(R.id.reprl)
    RefreshLayout mRefreshLayout;
    @BindView(R.id.iv_down)
    ImageView mIvDown;

    private DevicesPresenter mDevicesPresenter;
    private HouseManagePresenter mHousePresenter;
    private DeviceListAdapter mAdapter;
    private RoomTitleAdapter mTitleAdapter;

    private View emptyView;
    private View errorView;
    private View roomNoDeviceView;
    public PopupWindow mPopupTitle;
    private ArrayList<RoomBean> mTitleRooms;
    private List<DeviceDetail> mDevices = new ArrayList<>();
    public String mRoomId = "1a";
    private boolean hasUserConfig;
    private Handler mHandler;
    private List<DeviceDetail> mTempList = new ArrayList<>();
    private ListView mRoomListView;
    private static final String DEFAULT_ROOM = "全部设备";

    Runnable mDeviceR = new Runnable() {
        @Override
        public void run() {
            if (hasUserConfig) {
                refreshRoomAndDevice(false);
            }
            mHandler.postDelayed(this, getIntervalTime());
        }
    };

    private String mH5Url = "http://172.31.34.233:8089";
    private boolean mH5Test = false; //与前端联调时才打开

    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_device, null);
    }

    @Override
    public void afterInitView() {
        try {
            EventBus.getDefault().register(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mHandler = new Handler();
        initPresenter();
        initPopWindow();
        initRecyclerView();
        getUserConfig();
    }

    protected void initPresenter() {
        mDevicesPresenter = new DevicesPresenter(this, this, new DevicesView() {
            @Override
            public void onGetUserConfigError(String code, String msg) {
                ToastUtil.show(msg);
                hideLoading();
                mAdapter.setMyEmptyView(errorView);
            }

            @Override
            public void onGetUserConfigSuccess(UserConfig userConfig) {
                hasUserConfig = true;
                SpfUtils.put(AppConstans.Sp.SELECTED_HOUSE, userConfig.selected_family.family_nickname);
                startTask(0);
            }

            @Override
            public void onGetMyDevicesError(String code, String msg) {
                if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
                    ToastUtil.show(msg);
                }
                hideLoading();
                if (!isAdded()) {
                    return;
                }
                mDevices.clear();
                mRefreshLayout.refreshComplete();
                hideLoading();
                mAdapter.setMyEmptyView(errorView);
            }

            @Override
            public void onGetMyDevicesSuccess(MyDevice myDevice) {
                hideLoading();
                if (!isAdded()) {
                    return;
                }
                setIsPush(myDevice.devices);    //设置是否允许推送
                mRefreshLayout.refreshComplete();
                if (myDevice.devices != null && myDevice.devices.size() > 0) {
                    mDevices = myDevice.devices;
                    // H5调试
                    if (mH5Test) {
                        for (DeviceDetail dd : mDevices) {
                            H5Url h5Url = new H5Url();
                            h5Url.module_h5url = mH5Url;
                            h5Url.module_h5version = "1.0.0";
                            ArrayList<H5Url> list = new ArrayList<>();
                            list.add(h5Url);
                            dd.relative_h5urls = list;
                        }

                    }
                } else {
                    mDevices.clear();
                }

                doSelecteRoom();
            }
        });

        mHousePresenter = new HouseManagePresenter(this, new HouseManageView() {
            @Override
            public void getRoomDataSuccess(List<RoomBean> data) {
                if (!isAdded()) {
                    return;
                }
                if (data != null && mRoomListView != null) {
                    mTitleRooms = (ArrayList<RoomBean>) data;
                    mTitleRooms.addAll(0, getDefaultRooms());
                    mTitleAdapter.refreshData(mTitleRooms);
                    mTitleAdapter.notifyDataSetChanged();

                    //根据房间数据，动态改变listview的高度
                    ViewGroup.LayoutParams params = mRoomListView.getLayoutParams();
                    params.height = mTitleRooms.size() > 5 ? ViewUtils.dip2px(195) : ViewGroup.LayoutParams.WRAP_CONTENT;
                    params.width = ViewUtils.dip2px(95);
                    mRoomListView.setLayoutParams(params);
                }
            }

            @Override
            public void getRoomDataError(String result, String msg) {
//                hideLoading();
            }
        });
    }

    /**
     * 模拟数据给H5调试
     *
     * @param url
     * @param deviceId
     */
    private void h5AddDevice(String url, String deviceId) {
        H5Url h5Url = new H5Url();
        h5Url.module_h5url = url;
        h5Url.module_h5version = "1.0.0";
        ArrayList<H5Url> list = new ArrayList<>();
        list.add(h5Url);

        DeviceDetail device = new DeviceDetail();
        device.device_nickname = deviceId;
        device.device_id = deviceId;
        device.relative_h5urls = list;
        device.device_online_status = 1;

        mTempList.add(device);
        doSelecteRoom();
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
        mRefreshLayout.setCanPullUpRefresh(false);
        mRefreshLayout.setPullDownRefreshListener(new RefreshLayout.PullDownRefreshListener() {
            @Override
            public void onPullDownRefresh(RefreshLayout refreshLayout) {
                startTask(getIntervalTime());
                refreshRoomAndDevice(false);
            }
        });

        errorView = RecyclerUtils.getErrorView(getActivity(), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTask(getIntervalTime());
                refreshRoomAndDevice(true);
            }
        });
        emptyView = RecyclerUtils.getEmptyView(getActivity(), R.drawable.empty_device, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //空页面
            }
        });
        roomNoDeviceView = RecyclerUtils.getEmptyView(getActivity(), R.drawable.empty_room_device, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshRoomAndDevice(true);
            }
        });

        mAdapter = new DeviceListAdapter(getActivity(), mDevices);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter.setOnRefreshEnabled(true);
        mAdapter.setEnableLoadMore(false);
        mAdapter.enableLoadMoreEndClick(false);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (!NetworkUtils.isNetAvailable()) {
                    ToastUtil.show("当前网络不可用，请检查网络设置");
                    return;
                }
                DeviceDetail deviceDetail = mDevices.get(position);
                String webUrl = deviceDetail.relative_h5urls.get(0).module_h5url;
                if (TextUtils.isEmpty(webUrl)) {
                    ToastUtil.show("H5为空");
                    return;
                }
                String version = deviceDetail.relative_h5urls.get(0).module_h5version;
                webUrl = TextUtils.isEmpty(version) ? webUrl + "?deviceId=" + deviceDetail.device_id : webUrl + "?deviceId=" + deviceDetail.device_id + "&v=" + version;
                startActivityForResult(new Intent(getActivity(), JsBridgeActivity.class)
                        .putExtra(AppConstans.Common.INTENT_URL, webUrl)
                        .putExtra("device_id", deviceDetail.device_id), 107);
            }
        });

    }

    private void refreshRoomAndDevice(boolean showLoading) {
        LogUtils.debug("refreshRoomAndDevice");
        if (showLoading) {
            if (!NetworkUtils.isNetAvailable()) {
                ToastUtil.show("当前网络不可用，请检查网络设置");
                return;
            }
            showLoading();
        }
        getFidRooms();
        getMyDevices();
    }

    private void getUserConfig() {
        showLoading();
        mDevicesPresenter.getUserConfig();
    }

    private void getFidRooms() {
        mHousePresenter.getRoomData(AccountManager.getInstance().getFamilyId());
    }

    private void getMyDevices() {
        mDevicesPresenter.getMyDevices();
    }

    @OnClick(R.id.iv_add_device)
    public void iv_add() {
//        if (NetworkUtils.isWifiConnect()) {
//            AccountManager.getInstance().saveValue(AppConstans.Sp.LAST_SSID, new EasyLink(getActivity()).getSSID());
//        }
//        Intent intent = new_icon Intent(getActivity(), AddDeviceSuccessActivity.class);
    }

    /**
     * 长按+进入H5自定制设备页面，给前端人员调试使用
     *
     * @return
     */
    @OnLongClick(R.id.iv_add_device)
    public boolean add_long() {
        if (!BuildConfig.isDebug) {
            return true;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_h5test_dialog, null);
        final EditText etUrl = (EditText) view.findViewById(R.id.et_h5url);
        final EditText etDid = (EditText) view.findViewById(R.id.et_did);
        Button positive = (Button) view.findViewById(R.id.btn_h5_positive);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = etUrl.getText().toString();
                String did = etDid.getText().toString();
                dialog.dismiss();
                h5AddDevice(url, did);
                stopTask();
            }
        });
        dialog.show();
        return true;
    }

    @OnClick(R.id.ll_title)
    public void ll_title() {
        if (mPopupTitle == null) {
            return;
        }
        if (mPopupTitle.isShowing()) {
            mPopupTitle.dismiss();
        } else {
            float density = PhApplication.getAppContext().getResources().getDisplayMetrics().density;
            int dpPx = (int) (density * 90);
            mPopupTitle.showAsDropDown(mViewLine, mViewLine.getWidth() / 2 - dpPx / 2, 0);
            mIvDown.setRotation(180);
            mTitleAdapter.setRoomId(mRoomId);
            mRoomListView.setSelection(0);
        }
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
        mTitleRooms = getDefaultRooms();
        final View contentView = LayoutInflater.from(getContext()).inflate(R.layout.popup_room_selected, null);
        ShadowUtil.setShadowBg(contentView.findViewById(R.id.fl_shadow),
                PhApplication.getAppContext().getResources().getColor(R.color.transparent),
                PhApplication.getAppContext().getResources().getColor(R.color.btn_disable_bg), 3);

        mRoomListView = (ListView) contentView.findViewById(R.id.lv_room);
        mTitleAdapter = new RoomTitleAdapter(getActivity(), mTitleRooms, mRoomId);
        mRoomListView.setAdapter(mTitleAdapter);

        mPopupTitle = new PopupWindow(contentView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mPopupTitle.setTouchable(true);
        mPopupTitle.setOutsideTouchable(true);
        mPopupTitle.setFocusable(true);

        mRoomListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mRoomId = mTitleRooms.get(position).getRoom_id();
                mTitleAdapter.setRoomId(mRoomId);
                mTvRoomTitle.setText(mTitleRooms.get(position).getRoom_nickname());
                doSelecteRoom();
                mIvDown.setRotation(0);
                mPopupTitle.dismiss();
            }
        });

        mPopupTitle.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mIvDown.setRotation(0);
            }
        });
    }

    private void doSelecteRoom() {
        LogUtils.debug("doSelecteRoom");
        if (mDevices != null) {
            mDevices.addAll(mTempList);
        }
        if (mDevices == null || mDevices.size() == 0) {
            LogUtils.debug("doSelecteRoom emptyDevice");
            mAdapter.setMyEmptyView(emptyView);
            return;
        }

        List<DeviceDetail> roomDevices = new ArrayList<>();
        if (mRoomId.equals("1a")) {
            roomDevices = mDevices;
        } else {
            for (DeviceDetail dd : mDevices) {
                if (mRoomId.equals(dd.room_id)) {
                    roomDevices.add(dd);
                }
            }
        }

        if (mDevices != null && mDevices.size() > 0 && roomDevices.size() == 0) {
            mAdapter.setMyEmptyView(roomNoDeviceView);
            return;
        }

        mAdapter.setShowRoom(mRoomId.equals("1a"));
        mAdapter.setNewData(roomDevices);
        mAdapter.setEnableLoadMore(false);
        mAdapter.loadMoreComplete();
    }

    private ArrayList<RoomBean> getDefaultRooms() {
        List<String> nameList = new ArrayList<>();
        nameList.add(DEFAULT_ROOM);
        ArrayList<RoomBean> roomList = new ArrayList<>();
        for (int i = 0; i < nameList.size(); i++) {
            RoomBean roomBean = new RoomBean();
            roomBean.setRoom_id(i + 1 + "a");
            roomBean.setRoom_nickname(nameList.get(i));
            roomList.add(roomBean);
        }
        return roomList;
    }

    @Subscribe
    public void onEventMainThread(AddDeviceSuccessEvent event) {
        LogUtils.debug("onEventMainThread: ");
        showLoading();
        if (event.getRoomBean() == null || TextUtils.isEmpty(event.getRoomBean().getRoom_nickname())) {
            LogUtils.debug("onEventMainThread 22222: ");
            mTvRoomTitle.setText(DEFAULT_ROOM);
            mRoomId = "1a";
        } else {
            LogUtils.debug("onEventMainThread 33333: ");
            mTvRoomTitle.setText(event.getRoomBean().getRoom_nickname());
            mRoomId = event.getRoomBean().getRoom_id();
        }
    }

    private void startTask(long delay) {
        int fgItem = ((MainActivity) getActivity()).mViewPager.getCurrentItem();
        if (fgItem == 0 && mHandler != null && mDeviceR != null) {
            mHandler.removeCallbacks(mDeviceR);
            mHandler.postDelayed(mDeviceR, delay == 0 ? 100 : delay);
            LogUtils.debug("startTask");
        }
    }

    private void stopTask() {
        if (mHandler != null && mDeviceR != null) {
            mHandler.removeCallbacks(mDeviceR);
            LogUtils.debug("stopTask");
        }
    }

    /**
     * 获取首页设备列表轮询时间
     *
     * @return
     */
    private long getIntervalTime() {
        if (NetworkUtils.isWifiConnect() || !NetworkUtils.isNetAvailable()) {
            return 30 * 1000;
        } else {
            return 60 * 1000;
        }
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
