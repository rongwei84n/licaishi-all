package com.auts.lcssv.fragment;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.auts.lcssv.R;
import com.auts.lcssv.activity.MainActivity;
import com.auts.lcssv.activity.SceneEditActivity;
import com.auts.lcssv.adapter.CommonAdapter;
import com.auts.lcssv.adapter.holder.SceneViewHolder;
import com.auts.lcssv.base.BaseFragment;
import com.auts.lcssv.bean.MissionBean;
import com.auts.lcssv.bean.SceneBean;
import com.auts.lcssv.presenter.ScenePresenter;
import com.auts.lcssv.presenter.viewback.SceneView;
import com.auts.lcssv.util.LogUtils;
import com.auts.lcssv.util.ReplaceViewHelper;
import com.auts.lcssv.util.ToastUtil;
import com.auts.lcssv.util.UmengUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

/**
 * 场景页
 * Created by weiming.zeng on 2017/9/6.
 */
public class SceneFragment extends BaseFragment {
    /**
     * 使用RecyclerUtils获取RecyclerView的空白页和错误页。
     * RecyclerView的空白页面图片id为R.drawable.empty_scene,
     */
    @BindView(R.id.rv_scene)
    RecyclerView mRvScene;
    private ScenePresenter presenter;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.iv_menu)
    ImageView mIvMenu;
    @BindView(R.id.head)
    View mHead;
    private List<SceneBean> sceneBeans;
    private Handler handler;
    private Runnable missionRun;
    public SceneViewHolder.SceneCallBack callBack;
    private View emptyView;
    private ReplaceViewHelper mReplaceViewHelper;
    private List<View> sceneItemViewList = new ArrayList<>();//存储管理每个场景layout，实现 场景执行任务时的互斥
    public int deviceCount;
    @BindView(R.id.ll_parent)
    LinearLayout mParentlayout;

    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_scene, null);
    }

    @Override
    public void afterInitView() {
//        EventBus.getDefault().register(this);
        mTvTitle.setText(R.string.scene);
        mIvBack.setVisibility(View.GONE);
        mIvMenu.setVisibility(View.VISIBLE);
        mReplaceViewHelper = new ReplaceViewHelper(getActivity());
        mIvMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UmengUtil.onEvent(getActivity(), "SCENE_ADD_CLICK");
                SceneEditActivity.actionStartActivity(getActivity(), SceneFragment.this, false, null, "场景" + getNewSceneNumber() + "");
//                SceneEditActivity.actionStartActivity(getActivity(), SceneFragment.this, false, null, "场景" + 1 + "");
            }
        });
        //当没有场景数据时，显示该view替代
        emptyView = LayoutInflater.from(getActivity()).inflate(R.layout.empty_view, mParentlayout, false);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (presenter == null) {
                initPresenter();
            }
            if (mReplaceViewHelper == null) {
                mReplaceViewHelper = new ReplaceViewHelper(getActivity());
            }
            presenter.getScene();
        }
    }

    @Override
    public void initPresenter() {
        presenter = new ScenePresenter((MainActivity) getActivity(), new SceneView() {
            @Override
            public void getSceneSuccess(List<SceneBean> data) {
                if (!isAdded()) {
                    return;
                }
                if (data == null || data.size() == 0) {
                    emptyView.setClickable(false);
                    ((ImageView) emptyView.findViewById(R.id.iv_empty)).setImageResource(R.drawable.empty_scene);
                    mReplaceViewHelper.toReplaceView(mRvScene, emptyView);
                } else {
                    mReplaceViewHelper.removeView();
                }
                if (mRvScene != null && mRvScene.getAdapter() != null) {
                    //说明是其他页面跳转回来的,需要更新
                    ((CommonAdapter) mRvScene.getAdapter()).refreshData(data);
                }
                sceneBeans = data;
                mRvScene.setAdapter(new CommonAdapter<>(getActivity(), sceneBeans, R.layout.view_item_scene, SceneViewHolder.class));
                mRvScene.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            }

            @Override
            public void getSceneError(String msg) {
                ToastUtil.show(msg);
                emptyView.setClickable(true);
                ((ImageView) emptyView.findViewById(R.id.iv_empty)).setImageResource(R.drawable.error_view);
                emptyView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presenter.getScene();
                    }
                });
                mReplaceViewHelper.toReplaceView(mRvScene, emptyView);
            }

            @Override
            public void getMissionError(String msg) {
                ToastUtil.show(msg);
            }

            @Override
            public void getMissionSuccess(List<MissionBean> data) {
                deviceCount = presenter.getDevice(data).size(); //获取设备数量
                //获取任务成功后执行任务
                presenter.executeMission(getActivity(), data);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == SceneEditActivity.RESULTCODE) {
            presenter.getScene();
        }
    }

    /**
     * 获取该场景的任务
     */
    public void getMissions(String sceneId, SceneViewHolder.SceneCallBack callBack) { //viewholder中回调传入callback
        LogUtils.debug("SceneViewHolder", "获取场景任务");
        this.callBack = callBack;
        setClock();
        presenter.getMissionData(sceneId);
    }

    public void removeClock() { //执行成功，去除取消失败回调
        handler.removeCallbacks(missionRun);
    }

    public void setClock() {
        handler = new Handler();
        missionRun = new Runnable() {
            @Override
            public void run() {
                callBack.callback();
            }
        };
        handler.postDelayed(missionRun, 10000);
    }

    public void addSceneLayout(View view) {
        sceneItemViewList.add(view);
    }

    /**
     * 遍历列表计算新增场景编号
     */
    private int getNewSceneNumber() {
        try {
            if (sceneBeans == null || sceneBeans.size() == 0) {
                return 1;
            }
            List<Integer> number = new ArrayList<Integer>();
            for (SceneBean bean : sceneBeans) {
                if (bean.getScence_nickname().startsWith("场景")) {
                    String nickname = bean.getScence_nickname().substring(2);
                    if (nickname.matches("[0-9]{1,}")) {    //是否为纯数字
                        number.add(Integer.valueOf(nickname));
                    }
                }
            }
            Collections.sort(number);
            Integer[] array = new Integer[number.size()];
            number.toArray(array);
            if (array.length == 0 || array[0] > 1) {
                return 1;
            }
            for (int i = 0; i < array.length; i++) {    //确定场景编号
                if (i < array.length - 1 && (array[i + 1] - array[i]) > 1) {
                    LogUtils.debug("sceneFragment:iii----", array[i] + "");
                    return array[i] + 1;
                }
            }
            return array[array.length - 1] + 1;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    @Override
    public void onDestroy() {
//        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
