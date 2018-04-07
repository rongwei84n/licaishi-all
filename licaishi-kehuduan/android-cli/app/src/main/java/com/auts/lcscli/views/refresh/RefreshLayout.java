package com.auts.lcscli.views.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * 在PTR刷新控件基础上封装的刷新控件，可上拉，下拉刷新
 * 注：本控件需要android4.0及以上（4.0以下仅包含ListView可靠）
 * <p>
 * Created by jiang.wang on 2017/10/13.
 */

public class RefreshLayout extends PtrFrameLayout {

    private boolean canPullUpRefresh;
    private boolean canPullDownRefresh = true;

    private PhiHeader header;
    private PhiHeader footer;

    /**
     * 原码githttps://github.com/captainbupt/android-Ultra-Pull-To-Refresh-With-Load-More
     */

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initSet();
    }

    /**
     * 刷新控件默认设置
     */
    private void initSet() {

        /**
         * There are 6 properties:
         * 1.Resistence
         * This is the resistence while you are moving the frame, default is: 1.7f.
         * 2.Ratio of the Height of the Header to Refresh
         * The ratio of the height of the header to trigger refresh, default is: 1.2f.
         * 3.Duration to Close
         * The duration for moving from the position you relase the view to the height of header, default is 200ms.
         * 4.Duration to Close Header
         * The default value is 1000ms
         * 5.Keep Header while Refreshing
         * The default value is true.
         * 6.Pull to Refresh / Release to Refresh
         * The default value is Release to Refresh.
         */

        setResistance(3.0f); // you can also set foot and header separately
        setRatioOfHeaderHeightToRefresh(1.2f);
        setDurationToClose(1000);  // you can also set foot and header separately
        setPullToRefresh(false); //设置手势释放时开始刷新
        setKeepHeaderWhenRefresh(true); //正在刷新时保存刷新头可见

        setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                if (null != pullUpRefreshListener) {
                    pullUpRefreshListener.onPullUpRefresh((RefreshLayout) frame);
                }
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                if (null != pullDownRefreshListener) {
                    pullDownRefreshListener.onPullDownRefresh((RefreshLayout) frame);
                }
            }

            @Override
            public boolean checkCanDoLoadMore(PtrFrameLayout frame, View content, View footer) {
                return canPullUpRefresh && super.checkCanDoLoadMore(frame, content, footer);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return canPullDownRefresh && super.checkCanDoRefresh(frame, content, header);
            }
        });
        header = getHeaer();
        footer = getHeaer();
        setHeaderView(header);
        addPtrUIHandler(header);
        setFooterView(footer);
        addPtrUIHandler(footer);
    }

    private PhiHeader getHeaer() {
        PhiHeader phiHeader = new PhiHeader(getContext());
        phiHeader.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dip2px(getContext(), 60)));
        return phiHeader;
    }

    public void setCanPullUpRefresh(boolean canPullUpRefresh) {
        this.canPullUpRefresh = canPullUpRefresh;
    }

    public void setCanPullDownRefresh(boolean canPullDownRefresh) {
        this.canPullDownRefresh = canPullDownRefresh;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 主动发起下拉刷新
     */
    public void refreshDown() {
        postDelayed(new Runnable() {
            @Override
            public void run() {
                autoRefresh();
            }
        }, 100);
    }

    /**
     *
     */
    public void refreshUp() {
        postDelayed(new Runnable() {
            @Override
            public void run() {
                autoLoadMore();
            }
        }, 100);
    }

    private PullDownRefreshListener pullDownRefreshListener;
    private PullUpRefreshListener pullUpRefreshListener;

    /**
     * 设置上拉刷新监听
     *
     * @param pullUpRefreshListener 上拉刷新监听
     */
    public void setPullUpRefreshListener(PullUpRefreshListener pullUpRefreshListener) {
        this.pullUpRefreshListener = pullUpRefreshListener;
        if (null != pullUpRefreshListener) {
            setCanPullUpRefresh(true);
        }
    }

    /**
     * 设置下拉刷新监听
     *
     * @param pullDownRefreshListener
     */
    public void setPullDownRefreshListener(PullDownRefreshListener pullDownRefreshListener) {
        this.pullDownRefreshListener = pullDownRefreshListener;
        if (null != pullDownRefreshListener) {
            setCanPullDownRefresh(true);
        }
    }

    /**
     * 下拉刷新监听
     */
    public interface PullDownRefreshListener {
        /**
         * 调用当执行下拉刷新时
         *
         * @param refreshLayout
         */
        void onPullDownRefresh(RefreshLayout refreshLayout);
    }

    /**
     * 上拉刷新监听
     */
    public interface PullUpRefreshListener {
        /**
         * 调用当执行上拉刷新时
         *
         * @param refreshLayout
         */
        void onPullUpRefresh(RefreshLayout refreshLayout);
    }
}
