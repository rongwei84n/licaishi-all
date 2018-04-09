package com.auts.lcscli.views.recyclerview;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 网格布局设置上边距。
 * Created by xiaolei.yang on 2017/8/7.
 */

public class MySpaceItemDecoration extends RecyclerView.ItemDecoration {
    private int topSpace;
    private int leftSpace;
    private int count;

    public MySpaceItemDecoration(int count, int topSpace, int leftSpace) {
        this.count = count;
        this.topSpace = topSpace;
        this.leftSpace = leftSpace;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.top = topSpace;
        if (parent.getChildLayoutPosition(view) % count != 2) {
            outRect.right = leftSpace;
        }
    }
}
