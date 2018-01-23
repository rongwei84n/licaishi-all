package com.auts.lcssv.adapter.holder;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.auts.lcssv.R;
import com.auts.lcssv.activity.MainActivity;
import com.auts.lcssv.activity.SceneEditActivity;
import com.auts.lcssv.bean.SceneBean;
import com.auts.lcssv.fragment.SceneFragment;
import com.auts.lcssv.manager.imageloader.ImageLoader;
import com.auts.lcssv.util.CommonUtils;
import com.auts.lcssv.util.EditTextUtils;
import com.auts.lcssv.util.LogUtils;
import com.auts.lcssv.util.NetworkUtils;
import com.auts.lcssv.util.ToastUtil;
import com.auts.lcssv.util.UmengUtil;
import com.auts.lcssv.views.recyclerview.RefreshView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


/**
 *
 * @author weiming.zeng
 * @date 2017/9/6
 */

public class SceneViewHolder extends CommonViewHolder {
    private TextView text;
    private RefreshView icon;
    private ImageView edit;
    private SceneBean sceneBean;
    public static boolean hasRunning = false;

    public SceneViewHolder(Context context, final View itemview) {
        super(context, itemview);
        edit = (ImageView) itemview.findViewById(R.id.iv_edit);
        icon = (RefreshView) itemview.findViewById(R.id.iv_scene);
        text = (TextView) itemview.findViewById(R.id.tv_scene);
        text.setFilters(new InputFilter[]{EditTextUtils.getMaskLenthFilter(10, "...")});//处理房间名称的掩码
    }

    @Override
    public void bindView(RecyclerView.Adapter adapter, Object o) {
        sceneBean = (SceneBean) o;
        text.setText(sceneBean.getScence_nickname());
        if (sceneBean.getScence_pic_group() != null) {
            ImageLoader.getLoader(context).load(CommonUtils.getPicUrlFromList(sceneBean.getScence_pic_group().getGroup_pics(), "home")).into(icon);
        }
        final SceneFragment fragment = (SceneFragment) ((MainActivity) context).getmAdapter().getFragment(1);
        fragment.addSceneLayout(itemView);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SceneEditActivity.actionStartActivity(context, fragment, true, sceneBean, null);
            }
        });
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UmengUtil.onEvent(context, "SCENE_OPERATE");
                if (hasRunning) {
                    return;//正在运行则无法点击
                }
                if (!NetworkUtils.isNetAvailable()) {
                    ToastUtil.show("当前网络不可用，请检查网络设置");
                    return;
                }
                hasRunning = true;//开始执行置为true，其他的不能执行
                fragment.getMissions(sceneBean.getScence_id(), new SceneCallBack() {
                    @Override
                    public void callback() {
                        //执行失败的回调
                        UmengUtil.onEvent(context, "SCENE_OPERATE_FAIL");
                        Log.d("SceneViewHolder", "任务执行失败");
                        icon.stopAnimation();
                        icon.setImageResource(R.drawable.shibai);
                        reset(fragment, itemView);//1.5s后恢复原图,并让各场景layout可点击
                        ToastUtil.show(R.string.execute_fail);
                    }

                    @Override
                    public void onSuccess() {
                        UmengUtil.onEvent(context, "SCENE_OPERATE_SUCCESS");
                        icon.setImageResource(R.drawable.chenggong);
                        icon.stopAnimation();
                        icon.setTag(R.id.tag_scene, "");
                        reset(fragment, itemView);  //1.5s后恢复原图
                    }
                });
                icon.setTag(R.id.tag_scene, sceneBean.getScence_id());
                icon.setResource(R.drawable.yunxing);
                icon.startAnimation();  //成功获取数据后动画会停止，如果没返回数据。会执行上面的回调停止动画
            }
        });
    }

//    @Subscribe
//    public void onEventMainThread(MqttOnPublishEvent event) {
//        //执行成功获取数据
//        Log.d("SceneViewHolder", event.getMsg().toString());
//        SceneFragment fragment = (SceneFragment) ((MainActivity) context).getmAdapter().getFragment(1);
//        if (icon.getTag(R.id.tag_scene) == null) {
//            Log.d("SceneViewHolder", "tag为空");
//        }
//        if (icon.getTag(R.id.tag_scene) != null && sceneBean.getScence_id().equals(icon.getTag(R.id.tag_scene).toString())) {
//            LogUtils.debug("SceneViewHolder", "-----" + fragment.missionCount);
//            fragment.missionCount--;
//            if (fragment.missionCount == 0) {   //只有所有任务执行完成才算场景执行成功，如果有设备离线，则无法missionCount无法为0，则无法执行成功
//                Log.d("SceneViewHolder", "任务执行成功");
//                icon.setImageResource(R.drawable.chenggong);
//                fragment.removeClock();
//                icon.stopAnimation();
//                icon.setTag(R.id.tag_scene, "");
//                reset(fragment, itemView);  //1.5s后恢复原图
//            }
//        }
//    }

    public interface SceneCallBack {
        void callback();
        void onSuccess(); //任务执行成功的回调
    }

    /**
     * 将图标切换为正常图标，设置hasRunning标志
     */
    private void reset(final SceneFragment fragment, final View itemview) {
        icon.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (icon == null) {
                    return;
                }
                if (context instanceof Activity) {
                    Activity activity = (Activity)context;
                    if (activity.isDestroyed() || activity.isFinishing() || activity == null) {
                        return;
                    }
                }
                icon.stopAnimation();
                ImageLoader.getLoader(context).load(CommonUtils.getPicUrlFromList(sceneBean.getScence_pic_group().getGroup_pics(), "home")).into(icon);
                hasRunning = false;//执行结束
            }
        }, 1500);
    }
}
