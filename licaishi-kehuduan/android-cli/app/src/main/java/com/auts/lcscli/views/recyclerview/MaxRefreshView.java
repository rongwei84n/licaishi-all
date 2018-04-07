package com.auts.lcscli.views.recyclerview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.FrameLayout;

import org.xutils.common.util.DensityUtil;

/**
 * 下拉刷新容器
 * Created by weiming.zeng on 2017/9/4.
 */

public class MaxRefreshView extends FrameLayout {

    /**
     * 子View(滑动View)
     */
    private View mChildView;
    /**
     * 头Layout
     */
    private FrameLayout mHeadLayout;
    //头部显示的view,必需实现
    private RefreshAnimateView mHeadView;

    /**
     * 刷新状态
     */
    protected boolean isRefreshing;
    /**
     * 触摸获得Y的位置
     */
    private float mTouchY;

    /**
     * 当前Y的位置
     */
    private float mCurrentY;

    /**
     * 阻尼系数，数字越大阻尼越小
     */
    private float mResistance = 0.7f;

    /**
     * 头部最高允许的高度
     */
    private float mHeadHeight;
    /**
     * PullToRefreshListener
     */
    private PullToRefreshListener pullToRefreshListener;

    public MaxRefreshView(@NonNull Context context) {
        super(context);
        init();
    }

    public MaxRefreshView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MaxRefreshView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //使用isInEditMode解决可视化编辑器无法识别自定义控件的问题
        if (isInEditMode()) {
            return;
        }
        if (getChildCount() > 1) {
            throw new RuntimeException("只能拥有一个子控件");
        }
        //设置可滑动高度
        setHeadHeight(DensityUtil.dip2px(80));
    }

    public void setHeadLayoutBackground(Drawable pic) {
        mHeadLayout.setBackground(pic);
        mHeadLayout.requestLayout();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        //初始化头布局
        FrameLayout headLayout = new FrameLayout(getContext());
        LayoutParams param = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        param.gravity = Gravity.TOP;

        headLayout.setLayoutParams(param);
//        headLayout.setBackground(getResources().getDrawable(R.drawable.house_example));
        headLayout.setLayoutParams(param);
        mHeadLayout = headLayout;
        this.addView(mHeadLayout);

        if (mHeadView != null) {
            mHeadLayout.addView((View) mHeadView);
        }
        //获得子控件ListView
        mChildView = getChildAt(0);
        if (mChildView == null) {
            return;
        }
        mChildView.setClickable(true);
        mChildView.animate().setInterpolator(new DecelerateInterpolator(10));//设置速率为递减
        //给滑动子View设置动画，animate()返回view的属性动画animator
        mChildView.animate().setUpdateListener(//通过addUpdateListener()方法来添加一个动画的监听器
                new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        //HeadView的高度随着子View的变化而变化
                        mHeadLayout.getLayoutParams().height = (int) mChildView.getTranslationY();//获得mChildView当前y的位置
                        mHeadLayout.requestLayout();//重绘
                    }
                });
    }

    //控件在顶端时最后的Y坐标
    float mLastY;
    //头部移动的距离
    private float mCurrentPos;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //当前正在刷新,不拦截点击事件
        if (isRefreshing) {
            return super.dispatchTouchEvent(ev);
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = ev.getY();
                mCurrentPos = 0;
                break;
            case MotionEvent.ACTION_MOVE:
                float dy = ev.getY() - mLastY;//手指移动的距离
                if (dy > 0) {
                    //下拉
//                    Log.e("canChildScrollUp()", canChildScrollUp() + "");
                    if (!canChildScrollUp()) { //是否滑动到顶部
                        //滑动到顶部后
                        mCurrentPos = dy * mResistance;//设置头部滑动距离
                        mCurrentPos = Math.max(0, mCurrentPos);
                        changeView(mCurrentPos);
                    } else {
                        //还没滑动到顶部
                        mLastY = ev.getY();
                        return super.dispatchTouchEvent(ev);
                    }

                } else {
                    //上拉
                    if (mCurrentPos != 0) {
                        //滑动过程中mLastY保持不变，只变dy。只有当手指上滑过程中，位置超过Action_donw时的点才会走上拉的逻辑。
                        // 这里的上拉不代表手指一往上就是上拉
                        mCurrentPos = dy * mResistance;
                        mCurrentPos = Math.max(0, mCurrentPos);
                        changeView(mCurrentPos);
                    } else {
                        //如果头部移动距离为0，则传递给子View
                        return super.dispatchTouchEvent(ev);
                    }
                }
                //mlgb，开发时候忘记返回true进行拦截，导致下拉的时候最终又调用super.dispatchTouchEvent(ev);
                // 滑动事件传给子view，子view产生了滑动。因此在向上滑动时候，canChildScrollUp返回true，表示控件可以上滑。导致逻辑错误。
                return true;
            case MotionEvent.ACTION_UP:
                if (mChildView != null) {
                    if (mChildView.getTranslationY() >= mHeadHeight) { //手指松开后head达到刷新高度
                        //回调中处理刷新逻辑
                        starRefresh();
                    } else if (mChildView.getTranslationY() > 0) {
                        mChildView.animate().translationY(0).start();
                    }
                }
                return super.dispatchTouchEvent(ev);
            default:
                super.dispatchTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 手指抬起，开始刷新
     */
    private void starRefresh() {
        if (mHeadView != null) {
            mHeadView.startAnimation();
        }
        mChildView.animate().translationY(mHeadHeight);
        if (pullToRefreshListener != null) {
            pullToRefreshListener.onRefresh(this);
        }
        isRefreshing = true;
        //启动头部view动画
        mHeadLayout.postDelayed(new Thread() {
            @Override
            public void run() {
                finishRefresh();
                mHeadView.stopAnimation();
            }
        }, 2000);
    }

    /**
     * 刷新结束
     */
    public void finishRefresh() {
        if (mChildView != null) {
            mChildView.animate().translationY(0);
        }
        if (mHeadView != null) {
            mHeadView.stopAnimation();
        }
        if (pullToRefreshListener != null) {
            pullToRefreshListener.onFinishRefresh();
        }
        isRefreshing = false;

    }

    /**
     * 改变ChildView和head的高度
     */
    private void changeView(float pos) {
        mChildView.setTranslationY(pos);
        mHeadLayout.getLayoutParams().height = (int) (mCurrentPos);
        mHeadLayout.requestLayout();

    }

    /**
     * 用来判断是否可以下拉
     *
     * @return boolean
     */
    public boolean canChildScrollUp() {
        if (mChildView == null) {
            return false;
        }
        if (Build.VERSION.SDK_INT < 14) {
            //ListView和RecyclerView的父类
            if (mChildView instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) mChildView;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                        .getTop() < absListView.getPaddingTop());
            } else {
                return mChildView.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(mChildView, -1);
        }
    }

    /**
     * 设置头部可滑动的最高滑动高度
     *
     * @param headHeight
     */
    public void setHeadHeight(float headHeight) {
        this.mHeadHeight = headHeight;
    }

    /**
     * 添加头部Layout
     //     */
//    public void addHeadLayout(final View header) {
//        post(new_icon Thread() {
//            @Override
//            public void run() {
//                mHeadLayout.addView(header);
//                if (mHeadView != null) {
//                    mHeadLayout.addView((View)mHeadView);
//                }
//            }
//        });
//    }

    /**
     * 添加头部Layout
     */
    public void addHeadLayout(final View header) {
        mHeadLayout.addView(header);
        if (mHeadView != null) {
            mHeadLayout.addView((View) mHeadView);
        }
    }

    /**
     * 添加头部View
     */
    public void addHeadView(RefreshAnimateView refreshView) {
        this.mHeadView = refreshView;
    }

    public void setRefreshListener(PullToRefreshListener pullToRefreshListener) {
        this.pullToRefreshListener = pullToRefreshListener;
    }

    //在该接口中实现刷新等逻辑
    public interface PullToRefreshListener {
        void onRefresh(MaxRefreshView refreshLayout);

        void onFinishRefresh();
    }

    //头部View动画接口，保证头部的view实现动画
    public interface RefreshAnimateView {
        void startAnimation();

        void stopAnimation();
    }
}
