package com.wosloveslife.takemeizi.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

import java.util.Arrays;

/**
 * Created by YesingBeijing on 2016/9/13.
 */
public class StaggerGridRefreshRecyclerView extends BaseRefreshRecyclerView {

    private StaggeredGridLayoutManager mLayoutManager;

    public StaggerGridRefreshRecyclerView(Context context) {
        super(context);
    }

    public StaggerGridRefreshRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected RecyclerView.LayoutManager initLayoutManager() {
        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        return mLayoutManager;
    }

    @Override
    protected boolean isLast() {
        /* 这个数组中的position表示整个元素序列中,处于最靠近底边的元素所在的下标.如果列表有两列, 则数组length=2 */
        int[] itemPositions = mLayoutManager.findLastCompletelyVisibleItemPositions(null);
        Arrays.sort(itemPositions);
        /* 最后的-3是为了提前加载以便提升用户浏览体验 */
        return itemPositions[itemPositions.length - 1] >= mLayoutManager.getItemCount() - itemPositions.length - 3;
    }

    @Override
    public int getSpanCount() {
        return mLayoutManager.getSpanCount();
    }
}
