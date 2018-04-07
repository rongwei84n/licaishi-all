package com.auts.lcscli.views.recyclerview;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 单布局时使用。多布局时不能使用这个。使用BasePullUpDownMultiItemQuickAdapter
 * 默认使用左滑动画，允许下拉操作。
 * Created by xiaolei.yang on 2017/5/18.
 */

public abstract class BasePullUpDownAdapter<T, K extends BaseViewHolder> extends BaseQuickAdapter<T, K> {
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RefreshLoadListener mRefreshListener;

    public void setMyEmptyView(View emptyView) {
        setNewData(new ArrayList<T>());
        setEmptyView(emptyView);
    }


    /**
     * 构造方法。
     *
     * @param layoutResId item的布局id
     * @param data        列表数据
     */
    public BasePullUpDownAdapter(@LayoutRes int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
    }

    public BasePullUpDownAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    public BasePullUpDownAdapter(@Nullable List<T> data) {
        super(data);
    }


    /**
     * 是否允许下拉刷新
     *
     * @param enabled true - 允许，false - 禁止
     */
    public BasePullUpDownAdapter setOnRefreshEnabled(boolean enabled) {
        if (this.mSwipeRefreshLayout != null) {
            this.mSwipeRefreshLayout.setEnabled(enabled);
        }

        return this;
    }

    public RefreshLoadListener getRefreshListener() {
        return mRefreshListener;
    }

    /**
     * 设置刷新样式。
     *
     * @param refreshing false - 隐藏刷新标识，true - 显示刷新标识
     */
    public BasePullUpDownAdapter setRefreshing(boolean refreshing) {
        if (this.mSwipeRefreshLayout != null) {
            this.mSwipeRefreshLayout.setRefreshing(refreshing);
        }
        return this;
    }

    /**
     * 将RecyclerView和外层SwipeRefreshLayout及上下拉监听绑定起来。
     *
     * @param swipeRefreshLayout  RecyclerView外层包裹的对象，用于实现下拉刷新
     * @param recyclerView        RecyclerView对象
     * @param refreshLoadListener 实现下拉和上拉操作。因上/下拉时禁用了下/上拉，所以当上/下拉操作完成后需要调用对应方法允许下/上拉。
     * @return 返回当前对象。可直接点属性调用openLoadAnimation()等对已经定义的部分做补充或修改。
     */
    public BasePullUpDownAdapter bind(@NonNull SwipeRefreshLayout swipeRefreshLayout, @NonNull RecyclerView recyclerView, RefreshLoadListener refreshLoadListener) {
        this.mSwipeRefreshLayout = swipeRefreshLayout;
        this.mRefreshListener = refreshLoadListener;

        this.mSwipeRefreshLayout.setEnabled(true);
        this.mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setEnableLoadMore(false);
                if (mRefreshListener != null) {
                    mRefreshListener.onRefresh();
                }
            }
        });
        this.setOnLoadMoreListener(new RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                setOnRefreshEnabled(false);
                if (mRefreshListener != null) {
                    mRefreshListener.onLoadMore();
                }
            }
        }, recyclerView);
//        this.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        return this;
    }

}
