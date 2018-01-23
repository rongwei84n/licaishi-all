package com.auts.lcssv.views.recyclerview;

import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * item可拖动的列表的适配器基类。
 * Created by xiaolei.yang on 2017/5/22.
 */

public abstract class BasePullUpDownItemDraggableAdapter<T, K extends BaseViewHolder>
        extends BaseItemDraggableAdapter<T, K> {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RefreshLoadListener mRefreshListener;

    public BasePullUpDownItemDraggableAdapter(List<T> data) {
        super(data);
    }

    public BasePullUpDownItemDraggableAdapter(int layoutResId, List<T> data) {
        super(layoutResId, data);
    }

    /**
     * 是否允许下拉刷新
     *
     * @param enabled true - 允许，false - 禁止
     */
    public BasePullUpDownItemDraggableAdapter setOnRefreshEnabled(boolean enabled) {
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
    public BasePullUpDownItemDraggableAdapter setRefreshing(boolean refreshing) {
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
    public BasePullUpDownItemDraggableAdapter bind(@NonNull SwipeRefreshLayout swipeRefreshLayout, @NonNull RecyclerView recyclerView, RefreshLoadListener refreshLoadListener) {
        this.mSwipeRefreshLayout = swipeRefreshLayout;
        this.mRefreshListener = refreshLoadListener;

        this.mSwipeRefreshLayout.setEnabled(true);
        this.mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setEnableLoadMore(false);
                mRefreshListener.onRefresh();
            }
        });
        this.setOnLoadMoreListener(new RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                setOnRefreshEnabled(false);
                mRefreshListener.onLoadMore();
            }
        }, recyclerView);
        this.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        return this;
    }
}
