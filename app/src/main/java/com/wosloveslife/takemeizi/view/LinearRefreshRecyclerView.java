package com.wosloveslife.takemeizi.view;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by YesingBeijing on 2016/9/13.
 */
public class LinearRefreshRecyclerView extends BaseRefreshRecyclerView {

    private LinearLayoutManager mLayoutManager;

    public LinearRefreshRecyclerView(Context context) {
        this(context, null);
    }

    public LinearRefreshRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected RecyclerView.LayoutManager initLayoutManager() {
        mLayoutManager = new LinearLayoutManager(getContext());
        return mLayoutManager;
    }

    @Override
    protected boolean isLast() {
        return mLayoutManager.findLastCompletelyVisibleItemPosition() == mLayoutManager.getItemCount() - 1;
    }

    @Override
    public int getSpanCount() {
        if (mLayoutManager instanceof GridLayoutManager) {
            return ((GridLayoutManager) mLayoutManager).getSpanCount();
        } else {
            return 1;
        }
    }
}
