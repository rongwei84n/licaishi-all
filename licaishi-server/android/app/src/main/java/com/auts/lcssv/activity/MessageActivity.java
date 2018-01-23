package com.auts.lcssv.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.auts.lcssv.R;
import com.auts.lcssv.adapter.MessageAdapter;
import com.auts.lcssv.base.BaseActivity;
import com.auts.lcssv.bean.MessagesBean;
import com.auts.lcssv.presenter.MessagePresenter;
import com.auts.lcssv.presenter.viewback.MessageView;
import com.auts.lcssv.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;

public class MessageActivity extends BaseActivity {
    @BindView(R.id.rv_message)
    RecyclerView mRecyclerView;
    private MessagePresenter presenter;
    public static final int RESULTCODE = 1029;
    private MessageAdapter adapter;

    @Override
    public void initLayout(Bundle savedInstanceState) {
        setContentView(R.layout.activity_message);
    }

    @Override
    public void afterInitView() {
        setPageTitle(R.string.message_reminding);
        showIvMenu(R.drawable.shezhi);
        setTvMenuVisible(View.GONE);
        initPresenter();
        presenter.getMessages(1, 1000, 0);
        EventBus.getDefault().register(this);
    }

    protected void initPresenter() {
        presenter = new MessagePresenter(this, new MessageView() {
            @Override
            public void getMessageSuccess(MessagesBean data) {
                Log.d("MessageActivity", "获取消息提醒成功");
                if (adapter != null) {
                    adapter.refreshData(data.getMessages());
                    return;
                }
                adapter = new MessageAdapter(MessageActivity.this, data.getMessages());
                mRecyclerView.setAdapter(adapter);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(MessageActivity.this));
            }

            @Override
            public void getMessageError(String msg) {
                ToastUtil.show(msg);
            }
        });
    }

    @Override
    public void onIvMenuClick(ImageView view) {
        startActivity(new Intent(MessageActivity.this, RemindSettingActivity.class));
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onGoback() {
        setResult(RESULTCODE, new Intent());
        super.onGoback();
    }
}
