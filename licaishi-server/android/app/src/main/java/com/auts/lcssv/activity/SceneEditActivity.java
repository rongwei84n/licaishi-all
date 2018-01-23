package com.auts.lcssv.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.auts.lcssv.R;
import com.auts.lcssv.adapter.CommonAdapter;
import com.auts.lcssv.adapter.holder.MissionViewHolder;
import com.auts.lcssv.base.BaseActivity;
import com.auts.lcssv.bean.MissionBean;
import com.auts.lcssv.bean.SceneBean;
import com.auts.lcssv.bean.ScenePicBean;
import com.auts.lcssv.presenter.ScenePresenter;
import com.auts.lcssv.presenter.viewback.SceneView;
import com.auts.lcssv.util.CommonUtils;
import com.auts.lcssv.util.EditTextUtils;
import com.auts.lcssv.util.RegexUtils;
import com.auts.lcssv.util.ToastUtil;
import com.auts.lcssv.util.UmengUtil;
import com.auts.lcssv.views.CommonDialog;
import com.auts.lcssv.views.ItemBar;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.R.attr.data;

/**
 * 场景编辑
 *
 * @author weiming.zeng
 * @date 2017/09/11
 */
public class SceneEditActivity extends BaseActivity {

    @BindView(R.id.et_scene_name)
    EditText mSceneName;
    @BindView(R.id.ib_scenePic)
    ItemBar mIbScenePic;
    @BindView(R.id.ib_addmission)
    ItemBar mIbAddMission;
    @BindView(R.id.rv_mission)
    RecyclerView mRvMission;
    @BindView(R.id.ll_add)
    ViewGroup mLlAdd;
    @BindView(R.id.btn_del_scene)
    Button mBtnRemove;
    private String sceneUrl;
    public static final int RESULTCODE = 1010;
    private ScenePresenter presenter;
    private boolean isEdit;//判断是编辑还是添加
    private SceneBean sceneBean;
    private String groupId;
    private CommonDialog dialog;
    private List<MissionBean> missions;
    CommonAdapter<MissionBean, MissionViewHolder> adapter;

    @Override
    public void initLayout(Bundle savedInstanceState) {
        setContentView(R.layout.activity_scene_edit);
    }

    @Override
    public void afterInitView() {
        isEdit = getIntent().getBooleanExtra("isEdit", true);
        initDialog();
        EditTextUtils.setInputLengthAndCheckAscll(20, mSceneName);
        String defaultName = getIntent().getStringExtra("defaultName");
        if (!TextUtils.isEmpty(defaultName)) {
            mSceneName.setText(defaultName);
            mSceneName.setSelection(defaultName.length());
        }
        sceneBean = (SceneBean) getIntent().getSerializableExtra("sceneBean");
        if (sceneBean != null) {
            mSceneName.setText(sceneBean.getScence_nickname());
            mSceneName.setSelection(mSceneName.length());
            if (sceneBean.getScence_pic_group() != null) {
                groupId = sceneBean.getScence_pic_group().getPic_group_id();
                sceneUrl = CommonUtils.getPicUrlFromList(sceneBean.getScence_pic_group().getGroup_pics(), "normal");
                loadImage(sceneUrl, mIbScenePic.getmRight_img());
            } else {
                loadImage("", mIbScenePic.getmRight_img(), R.drawable.scene_default);
            }
            mSceneName.setSelection(mSceneName.length());
            presenter.getMissionData(sceneBean.getScence_id());
        } else {
            sceneBean = new SceneBean();
            //设置默认图片
            sceneBean.setScence_pic_group(new ScenePicBean("12"));
            loadImage("", mIbScenePic.getmRight_img(), R.drawable.scene_default);
        }
        if (isEdit) {
            setPageTitle(R.string.edit_scene);
            mBtnRemove.setVisibility(View.VISIBLE);
        } else {
            setPageTitle(R.string.add_scene);
            mIbAddMission.setVisibility(View.VISIBLE);
        }
        showTvMenu(R.string.save);
        setTvMenuColor(R.color.text_oringe);
    }

    @Override
    protected void initPresenter() {
        presenter = new ScenePresenter(this, new SceneView() {
            @Override
            public void modifySceneError(String msg) {
                ToastUtil.show(msg);
                UmengUtil.onEvent(SceneEditActivity.this, UmengUtil.SCENE_OPERATE_FAIL);
            }

            @Override
            public void modifySceneSuccess(String msg) {
                goBack();

            }

            @Override
            public void removeSceneError(String msg) {
                ToastUtil.show(msg);
                UmengUtil.onEvent(SceneEditActivity.this, UmengUtil.SCENE_OPERATE_FAIL);
            }

            @Override
            public void removeSceneSuccess(String msg) {
                ToastUtil.show(R.string.remove_success);
                goBack();
            }

            @Override
            public void addSceneError(String msg) {
                ToastUtil.show(msg);
                UmengUtil.onEvent(SceneEditActivity.this, UmengUtil.SCENE_OPERATE_FAIL);
            }

            @Override
            public void addSceneSuccess(String msg) {
                goBack();
            }

            @Override
            public void getMissionError(String msg) {
                ToastUtil.show(msg);
            }

            @Override
            public void getMissionSuccess(List<MissionBean> data) {
                if (SceneEditActivity.this.isFinishing() || SceneEditActivity.this.isDestroyed()) { //如果Activity被回收，则该页面的view都为空导致报错
                    return;
                }
                missions = data;
                if (data.size() == 0) {
                    mIbAddMission.setVisibility(View.VISIBLE);
                    mLlAdd.setVisibility(View.GONE);
                } else {
                    mIbAddMission.setVisibility(View.GONE);
                    mLlAdd.setVisibility(View.VISIBLE);
                }
                handleSwitchList();
                if (adapter == null) {
                    adapter = new CommonAdapter<>(SceneEditActivity.this, missions, R.layout.layout_item_mission, MissionViewHolder.class);
                    adapter.setInflateParent(false);
                    mRvMission.setAdapter(adapter);
                    mRvMission.setLayoutManager(new LinearLayoutManager(SceneEditActivity.this));
                } else {
                    adapter.refreshData(data);
                }
            }
        });
    }

    @Override
    public void onTvMenuClick(TextView view) {
        String sceneName = mSceneName.getText().toString();
        if (!RegexUtils.checkNameToast(mSceneName.getText().toString(), "场景名称")) {
            return;
        } else if (missions == null || missions.size() == 0) {
            ToastUtil.show(R.string.check_mission_name);
            return;
        } else {
            if (isEdit) {
                UmengUtil.onEvent(this, UmengUtil.SCENE_OPERATE);
                presenter.modifySceneInfo(sceneBean.getScence_id(), sceneName, groupId, missions);
            } else {
                UmengUtil.onEvent(this, UmengUtil.SCENE_ADD_SAVE);
                presenter.addSceneInfo(sceneName, groupId, missions);
            }
        }
    }


    private void handleSwitchList() {
        if (missions == null || missions.size() == 0) {
            return;
        }
        for (MissionBean bean : missions) {
            bean.setSocket_name(bean.getSocket_name().toUpperCase());
            if (bean.getSocket_name().startsWith("插口")) {
                continue;
            }
            bean.setSocket_name("插口" + bean.getSocket_name());
        }
    }

    private void initDialog() {
        dialog = new CommonDialog(this, "确定要删除该场景吗?", "取消", "删除", null, null);
        dialog.getmNegativeBtn().setTextColor(getResources().getColor(R.color.red));
        dialog.setPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setNegativetiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sceneBean != null) {
                    presenter.deleteSceneInfo(sceneBean.getScence_id());
                }
                dialog.dismiss();
            }
        });
    }

    @OnClick(R.id.ib_scenePic)
    public void chosePic() {
        ScenePicActivity.actionStartActivity(this, sceneBean);
    }

    public static void actionStartActivity(Context context, android.support.v4.app.Fragment fragment, boolean isEdit, SceneBean sceneBean, String defaultName) {
        Intent intent = new Intent(context, SceneEditActivity.class);
        intent.putExtra("isEdit", isEdit);
        intent.putExtra("sceneBean", sceneBean);
        intent.putExtra("defaultName", defaultName);
        if (fragment != null && fragment.isAdded()) {
            fragment.startActivityForResult(intent, SceneEditActivity.RESULTCODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (ScenePicActivity.REQUESTCODE == resultCode) {
            sceneUrl = data.getStringExtra("url");
            loadImage(sceneUrl, mIbScenePic.getmRight_img());
            groupId = data.getStringExtra("groupId");
            if (sceneBean != null) {
                sceneBean.getScence_pic_group().setPic_group_id(groupId);
            } else {
                sceneBean = new SceneBean();
            }
        }
        if (resultCode == SettingActivity.RESULTCODE) {
            boolean state = data.getBooleanExtra("state", false);
            int position = data.getIntExtra("position", 0);
            missions.get(position).setTask_act(state ? 1 : 0);
            adapter.refreshData(missions);
        }
        if (resultCode == DeviceListActivity.RESULTCODE) {
            missions = (List<MissionBean>) data.getSerializableExtra("missions");
            refreshMission();
        }
    }

    private void refreshMission() {
        //新增时adapter有可能为null
        if (missions == null || missions.size() == 0) {
            mIbAddMission.setVisibility(View.VISIBLE);
            mLlAdd.setVisibility(View.GONE);
            return;
        } else {
            mIbAddMission.setVisibility(View.GONE);
            mLlAdd.setVisibility(View.VISIBLE);
        }
        handleSwitchList();
        if (adapter == null) {
            adapter = new CommonAdapter<>(SceneEditActivity.this, missions, R.layout.layout_item_mission, MissionViewHolder.class);
            adapter.setInflateParent(false);
            mRvMission.setAdapter(adapter);
            mRvMission.setLayoutManager(new LinearLayoutManager(this));

        } else {
            adapter.refreshData(missions);
        }

    }

    @OnClick(R.id.btn_del_scene)
    public void removeScene() {
        dialog.show();
    }

    @OnClick({R.id.ib_addmission, R.id.tv_add_mission, R.id.iv_add_mission})
    public void addMission() {
        UmengUtil.onEvent(this, "SCHEDULE_ADD_CLICK");
        DeviceListActivity.actionStartActivity(this, missions);
    }

    public void removeMission(MissionBean mission) {
        missions.remove(mission);
        refreshMission();
    }

    private void goBack() {
        UmengUtil.onEvent(SceneEditActivity.this, UmengUtil.SCENE_OPERATE_SUCCESS);
        setResult(SceneEditActivity.RESULTCODE);
        finish();
    }

}
