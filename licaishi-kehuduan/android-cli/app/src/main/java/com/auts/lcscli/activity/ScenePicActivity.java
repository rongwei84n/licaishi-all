package com.auts.lcscli.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.auts.lcscli.R;
import com.auts.lcscli.adapter.CommonAdapter;
import com.auts.lcscli.adapter.holder.ScenePicViewHolder;
import com.auts.lcscli.base.BaseActivity;
import com.auts.lcscli.bean.SceneBean;
import com.auts.lcscli.bean.ScenePic;
import com.auts.lcscli.presenter.ScenePresenter;
import com.auts.lcscli.presenter.viewback.SceneView;
import com.auts.lcscli.util.ToastUtil;

import java.util.List;

import butterknife.BindView;

public class ScenePicActivity extends BaseActivity {

    @BindView(R.id.rv_scenePic)
    RecyclerView mRvScenePic;
    private SceneBean sceneBean;

    private ScenePresenter presenter;
    public static final int REQUESTCODE = 1020;

    @Override
    public void initLayout(Bundle savedInstanceState) {
        setContentView(R.layout.activity_scene_pic);
    }

    @Override
    public void afterInitView() {
        setPageTitle(R.string.scene_pic);
        presenter.getScenePicData();
        sceneBean = (SceneBean) getIntent().getSerializableExtra("sceneBean");
    }

    @Override
    protected void initPresenter() {
        presenter = new ScenePresenter(this, new SceneView() {
            @Override
            public void getPicSuccess(List<ScenePic> data) {
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(ScenePicActivity.this, 3);
                CommonAdapter adapter = new CommonAdapter<>(ScenePicActivity.this, data, R.layout.layout_item_one_image, ScenePicViewHolder.class, layoutManager);
                adapter.setInflateParent(false);
                mRvScenePic.setAdapter(adapter);
                mRvScenePic.setLayoutManager(layoutManager);
            }

            @Override
            public void getPicError(String msg) {
                ToastUtil.show(msg);
            }
        });
    }

    public static void actionStartActivity(Activity activity, SceneBean sceneBean) {
        Intent intent = new Intent(activity, ScenePicActivity.class);
        intent.putExtra("sceneBean", sceneBean);
        activity.startActivityForResult(intent, ScenePicActivity.REQUESTCODE);
    }

    public SceneBean getSceneBean() {
        return sceneBean;
    }
}
