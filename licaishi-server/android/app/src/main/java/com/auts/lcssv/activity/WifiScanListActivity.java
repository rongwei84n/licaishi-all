package com.auts.lcssv.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.auts.lcssv.R;
import com.auts.lcssv.adapter.WifiScanListAdapter;
import com.auts.lcssv.base.BaseActivity;
import com.auts.lcssv.bean.DeviceSsidsBean;
import com.auts.lcssv.constants.AppConstans;
import com.auts.lcssv.listener.DialogOnClickListener;
import com.auts.lcssv.presenter.SoftApDevicePresenter;
import com.auts.lcssv.presenter.viewback.ILoadingView;
import com.auts.lcssv.presenter.viewback.SoftApDeviceView;
import com.auts.lcssv.util.NetworkUtils;
import com.auts.lcssv.util.RecyclerUtils;
import com.auts.lcssv.views.ComfirmDialog;
import com.auts.lcssv.views.recyclerview.MyDecoration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 展示设备周围的wifi列表
 *
 * @author xiaolei.yang
 */
public class WifiScanListActivity extends BaseActivity implements ILoadingView {
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;


    ArrayList<DeviceSsidsBean> mList;
    WifiScanListAdapter mAdapter;

    SoftApDevicePresenter mSoftApDevicePresenter;

    private View emptyView;
    private View errorView;

    @Override
    public void initLayout(Bundle savedInstanceState) {
        setContentView(R.layout.activity_wifi_scan_list);
    }

    @Override
    public void afterInitView() {
        setPageTitle(R.string.select_wifi);
        showTvMenu(R.string.refresh);

        mList = new ArrayList<>();
        emptyView = RecyclerUtils.getEmptyView(this, R.drawable.empty_wifi_scan_list, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_menu();
            }
        });
        errorView = RecyclerUtils.getEmptyView(this, R.drawable.error_wifi_scan_list, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_menu();
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new MyDecoration(this, MyDecoration.VERTICAL_LIST));
        mAdapter = new WifiScanListAdapter(mList);
        mAdapter.bind(mSwipeRefreshLayout, mRecyclerView, null);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent();
                intent.putExtra("deviceSsidsBean", mList.get(position));
                intent.putParcelableArrayListExtra("wifi_scan_list", mList);
                setResult(RESULT_OK, intent);
                WifiScanListActivity.this.finish();
            }
        });
        mAdapter.setOnRefreshEnabled(false);
        mAdapter.enableLoadMoreEndClick(false);
        mAdapter.setEnableLoadMore(false);
        mRecyclerView.setAdapter(mAdapter);
        ArrayList<DeviceSsidsBean> tempList = getIntent().getParcelableArrayListExtra("wifi_scan_list");
        if (tempList != null && tempList.size() > 0) {
            mList.addAll(tempList);
            orderList();
            mAdapter.notifyDataSetChanged();
        } else {
            mAdapter.setEmptyView(emptyView);
            mSoftApDevicePresenter.readDeviceInfo();
        }

    }

    @Override
    protected void initPresenter() {
        mSoftApDevicePresenter = new SoftApDevicePresenter(this, new SoftApDeviceView() {
            @Override
            public void readDeviceSsidSSuccess(Map<String, String> wifiScan) {
                if (wifiScan == null) {
                    mList.clear();
                    mAdapter.setNewData(mList);
                    mAdapter.setEmptyView(errorView);
                } else if (wifiScan.size() == 0) {
                    mList.clear();
                    mAdapter.setNewData(mList);
                    mAdapter.setEmptyView(emptyView);
                } else {
                    mList.clear();
                    for (String key : wifiScan.keySet()) {
                        if (!TextUtils.isEmpty(wifiScan.get(key))) {
                            mList.add(new DeviceSsidsBean(key, wifiScan.get(key)));
                        }
                    }
                    orderList();
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void readDeviceSsidError(String code, String msg) {
                mList.clear();
                mAdapter.setNewData(mList);
                mAdapter.setEmptyView(errorView);
            }
        });
    }

    private void orderList() {
        Collections.sort(mList, new Comparator<DeviceSsidsBean>() {
            @Override
            public int compare(DeviceSsidsBean dsb1, DeviceSsidsBean dsb2) {
                return dsb2.getStrength() - dsb1.getStrength();
            }
        });
    }

    @OnClick(R.id.tv_menu)
    public void tv_menu() {
        if (isCurrentSsid()) {
            mSoftApDevicePresenter.readDeviceInfo();
        } else {
            new ComfirmDialog(WifiScanListActivity.this, null, getString(R.string.connect_wifi_and_refresh), "确定", Gravity.CENTER, new DialogOnClickListener() {
                @Override
                public void onClickListener(Dialog dialog, View v) {
                    dialog.dismiss();
                }
            }).show();
        }
    }

    /**
     * 判断当前是否已连接wifi，连接的wifi是否是目标wifi
     */
    private boolean isCurrentSsid() {
        //softAp模式下，智能排插的ssid
        String currentDeviceSsid = AppConstans.Products.INSERTS_SSID_NAME_PREFIX;
        if (NetworkUtils.isWifiConnect()) {
            ConnectivityManager connectMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            if (null == connectMgr) {
                return false;
            }
            NetworkInfo info = connectMgr.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                WifiInfo wifiInfo = NetworkUtils.getWifiInfo();
                if (wifiInfo != null && wifiInfo.getSSID() != null) {
                    return wifiInfo.getSSID().replace("\"", "").startsWith(currentDeviceSsid);
                }
            }
        }
        return false;
    }

    @Override
    public void showLoadingDialog(int resId) {
        showLoading(R.string.loading_text);
    }

    @Override
    public void hideLoadingDialog() {
        hideLoading();
    }

    @Override
    public void updateLoadingMessage(String message) {
        showLoading(message);
    }
}
