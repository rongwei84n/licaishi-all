package com.auts.lcssv.views.recyclerview;

import android.view.ViewGroup;


import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.SectionEntity;

import java.util.List;

/**
 * 段落式列表的适配器基类。（即每个item分为一个头部，一个内容部的样式。）
 * Created by xiaolei.yang on 2017/5/22.
 */

public abstract class BasePullUpDownSectionAdapter<T extends SectionEntity, K extends BaseViewHolder> extends BasePullUpDownAdapter<T, K> {


    protected int mSectionHeadResId;
    protected static final int SECTION_HEADER_VIEW = 0x00000444;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param sectionHeadResId The section head layout id for each item
     * @param layoutResId      The layout resource id of each item.
     * @param data             A hasnew list is created out of this one to avoid mutable list
     */
    public BasePullUpDownSectionAdapter(int layoutResId, int sectionHeadResId, List<T> data) {
        super(layoutResId, data);
        this.mSectionHeadResId = sectionHeadResId;
    }

    @Override
    protected int getDefItemViewType(int position) {
        return mData.get(position).isHeader ? SECTION_HEADER_VIEW : 0;
    }

    @Override
    protected K onCreateDefViewHolder(ViewGroup parent, int viewType) {
        if (viewType == SECTION_HEADER_VIEW) {
            return createBaseViewHolder(getItemView(mSectionHeadResId, parent));
        }

        return super.onCreateDefViewHolder(parent, viewType);
    }

    @Override
    protected boolean isFixedViewType(int type) {
        return super.isFixedViewType(type) || type == SECTION_HEADER_VIEW;
    }

    @Override
    public void onBindViewHolder(K holder, int positions) {
        switch (holder.getItemViewType()) {
            case SECTION_HEADER_VIEW:
                setFullSpan(holder);
                convertHead(holder, mData.get(holder.getLayoutPosition() - getHeaderLayoutCount()));
                break;
            default:
                super.onBindViewHolder(holder, positions);
                break;
        }
    }

    protected abstract void convertHead(K helper, T item);

}
