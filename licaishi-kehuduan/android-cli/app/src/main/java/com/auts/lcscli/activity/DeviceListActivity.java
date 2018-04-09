package com.auts.lcscli.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.auts.lcscli.R;
import com.auts.lcscli.adapter.CommonAdapter;
import com.auts.lcscli.adapter.RoomTitleAdapter;
import com.auts.lcscli.adapter.holder.DeviceItemViewHolder;
import com.auts.lcscli.base.BaseActivity;
import com.auts.lcscli.bean.DeviceDetail;
import com.auts.lcscli.bean.MissionBean;
import com.auts.lcscli.bean.MyDevice;
import com.auts.lcscli.bean.RoomBean;
import com.auts.lcscli.bean.SwitchBean;
import com.auts.lcscli.manager.AccountManager;
import com.auts.lcscli.presenter.DevicesPresenter;
import com.auts.lcscli.presenter.HouseManagePresenter;
import com.auts.lcscli.presenter.viewback.DevicesView;
import com.auts.lcscli.presenter.viewback.HouseManageView;
import com.auts.lcscli.util.LogUtils;
import com.auts.lcscli.util.ToastUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 设备列表
 *
 * @author weiming.zeng
 * @date 2017/09/11
 */
public class DeviceListActivity extends BaseActivity {

    @BindView(R.id.rv_device)
    RecyclerView mRvDevice;
    private DevicesPresenter presenter;
    private CommonAdapter adapter;
    private PopupWindow mPopupTitle;
    private ArrayList<RoomBean> mTitleRooms;
    private RoomTitleAdapter mTitleAdapter;
    public String mRoomId = "1a";
    @BindView(R.id.tv_room_title)
    TextView mTvRoomTitle;
    @BindView(R.id.iv_down)
    ImageView mIvDown;
    @BindView(R.id.iv_goback)
    ImageView mIvback;
    @BindView(R.id.view_line)
    View mViewLine;
    @BindView(R.id.ll_title)
    ViewGroup mHeadView;
    private HouseManagePresenter mHousePresenter;
    public static final int RESULTCODE = 1024;
    private List<DeviceDetail> deviceDetails;
    private List<MissionBean> missions;
    private List<MissionBean> newMissions;

    /**
     * 说明是否进行了房间选择
     */
    private boolean isSelectRoom = false;

    @Override
    public void initLayout(Bundle savedInstanceState) {
        setContentView(R.layout.activity_device_list);
    }

    @Override
    public void afterInitView() {
        setPageTitle(R.string.room_all);
        initPopWindow();
        refreshRommAndDevice();
        missions = (List<MissionBean>) getIntent().getSerializableExtra("missions");
    }

    @Override
    protected void initPresenter() {
        presenter = new DevicesPresenter(this, this, new DevicesView() {
            @Override
            public void onGetMyDevicesError(String code, String msg) {
                ToastUtil.show(msg);
            }

            @Override
            public void onGetMyDevicesSuccess(MyDevice myDevice) {
                deviceDetails = myDevice.devices;
                if (adapter == null) {
                    adapter = new CommonAdapter<>(DeviceListActivity.this, deviceDetails, R.layout.view_device_item, DeviceItemViewHolder.class);
                }
                if (mRvDevice == null) {
                    mRvDevice = (RecyclerView) findViewById(R.id.rv_device);
                    if (mRvDevice == null) {
                        return;
                    }
                }
                mRvDevice.setAdapter(adapter);
                mRvDevice.setLayoutManager(new LinearLayoutManager(DeviceListActivity.this));
            }
        });

        mHousePresenter = new HouseManagePresenter(this, new HouseManageView() {
            @Override
            public void getRoomDataSuccess(List<RoomBean> data) {
                if (data != null) {
                    mTitleRooms = (ArrayList<RoomBean>) data;
                    mTitleRooms.addAll(0, getDefaultRooms());
                    mTitleAdapter.refreshData(mTitleRooms);
                    mTitleAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void getRoomDataError(String result, String msg) {
                ToastUtil.show(msg);
            }
        });
    }

    @OnClick({R.id.tv_room_title, R.id.iv_down})
    public void ll_title() {
        if (mPopupTitle == null) {
            return;
        }

        if (mPopupTitle.isShowing()) {
            mPopupTitle.dismiss();
        } else {
            float density = getResources().getDisplayMetrics().density;
            int dpPx = (int) (density * 90);
            mPopupTitle.showAsDropDown(mViewLine, mViewLine.getWidth() / 2 - dpPx / 2 + 40, 0);
            mIvDown.setRotation(180);
        }
    }

    private void refreshRommAndDevice() {
        LogUtils.show("refreshRommAndDevice");
        mHousePresenter.getRoomData(AccountManager.getInstance().getFamilyId());
        presenter.getMyDevices();
    }


    private void initPopWindow() {
        mTitleRooms = getDefaultRooms();
        final View contentView = LayoutInflater.from(this).inflate(R.layout.popup_room_selected, null);
        ListView listView = (ListView) contentView.findViewById(R.id.lv_room);
        mTitleAdapter = new RoomTitleAdapter(this, mTitleRooms, mRoomId);
        listView.setAdapter(mTitleAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPopupTitle.dismiss();
                mRoomId = mTitleRooms.get(position).getRoom_id();
                mTvRoomTitle.setText(mTitleRooms.get(position).getRoom_nickname());
                mTitleAdapter.setRoomId(mRoomId);
                doSelecteRoom();
                mIvDown.setRotation(0);
            }
        });
        mPopupTitle = new PopupWindow(contentView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mPopupTitle.setTouchable(true);
        mPopupTitle.setOutsideTouchable(true);
        mPopupTitle.setFocusable(true);
        mPopupTitle.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mIvDown.setRotation(0);
            }
        });
    }

    private void doSelecteRoom() {
        List<DeviceDetail> roomDevices = new ArrayList<>();
        if ("1a".equals(mRoomId)) {
            //选择全部房间
            roomDevices = deviceDetails;
            isSelectRoom = false;
        } else {
            isSelectRoom = true;
            if (deviceDetails == null) {
                return;
            }
            for (DeviceDetail dd : deviceDetails) {
                if (mRoomId.equals(dd.room_id)) {
                    roomDevices.add(dd);
                }
            }
        }
        if (adapter == null) {
            adapter = new CommonAdapter<>(DeviceListActivity.this, deviceDetails, R.layout.view_device_item, DeviceItemViewHolder.class);
        }
        adapter.refreshData(roomDevices);
    }

    private ArrayList<RoomBean> getDefaultRooms() {
        List<String> nameList = Arrays.asList(getResources().getStringArray(R.array.room_selects));
        ArrayList<RoomBean> roomList = new ArrayList<>();
        for (int i = 0; i < nameList.size(); i++) {
            RoomBean roomBean = new RoomBean();
            roomBean.setRoom_id(i + 1 + "a");
            roomBean.setRoom_nickname(nameList.get(i));
            roomList.add(roomBean);
        }
        return roomList;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * @param missions 场景编辑界面传来的任务列表
     */
    public static void actionStartActivity(Context context, List<MissionBean> missions) {
        Intent intent = new Intent(context, DeviceListActivity.class);
        intent.putExtra("missions", (Serializable) missions);
        ((Activity) context).startActivityForResult(intent, DeviceListActivity.RESULTCODE);
    }

    @Override
    @OnClick(R.id.iv_goback)
    public void onGoback() {
        Intent intent = new Intent();
        intent.putExtra("missions", (Serializable) missions);
        setResult(DeviceListActivity.RESULTCODE, intent);
        super.onGoback();
    }

    /**
     * 获得指定房间已有任务的插口信息
     */
    public List<SwitchBean> getSwitchBean(String roomName) {
        if (missions == null || missions.size() == 0) {
            return null;
        }
        List<SwitchBean> switchBeanList = new ArrayList<>();
        for (MissionBean mission : missions) {
            if (roomName.equals(mission.getRoom_name())) {
                switchBeanList.add(new SwitchBean(mission.getSocket_name(), mission.getTask_act(), true));
            }
        }
        return switchBeanList;
    }

    public boolean isSelectRoom() {
        return isSelectRoom;
    }

    public void setSelectRoom(boolean selectRoom) {
        isSelectRoom = selectRoom;
    }
}
