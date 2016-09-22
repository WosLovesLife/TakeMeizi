package com.wosloveslife.takemeizi.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;


/**
 * Created by YesingBeijing on 2016/9/14.
 */
public abstract class BaseRefreshRecyclerView extends SwipeRefreshLayout {

    private RecyclerView mRecyclerView;

    private RecyclerView.LayoutManager mLayoutManager;

    /** 监听器，下拉刷新或上拉加载时回调对应方法 */
    private OnRefreshListener mOnRefreshListener;
    /** 监听器, 当布局尺寸发生变化时回调相关方法 */
    private OnSizeChangeListener mOnSizeChangeListener;

    /** 为true时, 滑动到最后一条时加载更多 */
    private boolean mLoadMoreEnable;

    public BaseRefreshRecyclerView(Context context) {
        this(context, null);
    }

    public BaseRefreshRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mOnSizeChangeListener != null) {
            mOnSizeChangeListener.onSizeChanged(w, h, oldw, oldh);
        }
    }

    /**
     * 初始化
     */
    private void init() {
        mRecyclerView = new RecyclerView(getContext());
        addView(mRecyclerView);

        initRefreshLayout();
        initRecyclerView();
    }

    /**
     * 初始化刷新控件
     */
    private void initRefreshLayout() {
        setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mOnRefreshListener != null) {
                    mOnRefreshListener.onRefresh();
                }
            }
        });
    }

    private static final String TAG = "StaggerGridRefresh";

    /**
     * 初始化列表控件
     */
    private void initRecyclerView() {
        mLayoutManager = initLayoutManager();
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState != RecyclerView.SCROLL_STATE_IDLE) return;
                if (mLayoutManager.getItemCount() < 1) return;

                if (isLast() && mLoadMoreEnable) {
                    startRefreshing();

                    if (mOnRefreshListener != null) {
                        mOnRefreshListener.onLoadMore();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    protected abstract RecyclerView.LayoutManager initLayoutManager();

    protected abstract boolean isLast();

    /**
     * 设置RecyclerView数据适配器
     *
     * @param adapter 适配器
     */
    public void setAdapter(RecyclerView.Adapter adapter) {
        mRecyclerView.setAdapter(adapter);
    }

    /**
     * 开启刷新
     */
    public void startRefreshing() {
        if (!isRefreshing()) {
            setRefreshing(true);
        }
    }

    /**
     * 停止刷新
     */
    public void refreshingComplete() {
        if (isRefreshing()) {
            setRefreshing(false);
        }
    }

    /**
     * 是否启用下拉刷新
     *
     * @param enable 为true时启用, 默认为true
     */
    public void setRefreshEnable(boolean enable) {
        setEnabled(enable);
    }

    /**
     * 给条目加修饰
     *
     * @param itemDecoration 条目装饰物
     */
    public void addItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        mRecyclerView.addItemDecoration(itemDecoration);
    }

    /**
     * 给条目加修饰
     *
     * @param itemDecoration 条目装饰物
     * @param index          不知道干嘛的...
     */
    public void addItemDecoration(RecyclerView.ItemDecoration itemDecoration, int index) {
        mRecyclerView.addItemDecoration(itemDecoration, index);
    }

    public void setItemAnimator(RecyclerView.ItemAnimator animator) {
        mRecyclerView.setItemAnimator(animator);
    }

    public RecyclerView getRecyclerView(){
        return mRecyclerView;
    }

    //=======================================================
    //=======================================================
    //=======================================================

    /**
     * 是否启用加载更多(当滑动到最后一条时触发)
     *
     * @param enable 为true时启用, 默认为false
     */
    public void setLoadMoreEnable(boolean enable) {
        mLoadMoreEnable = enable;
    }

    public void setBothEnable(boolean enable) {
        setRefreshEnable(enable);
        setLoadMoreEnable(enable);
    }

    public interface OnRefreshListener {
        void onRefresh();

        void onLoadMore();
    }

    public void setOnRefreshListener(OnRefreshListener listener) {
        mOnRefreshListener = listener;
    }

    public abstract int getSpanCount();

    public interface OnSizeChangeListener {
        void onSizeChanged(int w, int h, int oldW, int oldH);
    }

    public void setOnSizeChangedListener(OnSizeChangeListener listener) {
        mOnSizeChangeListener = listener;
    }

    public void setListPadding(int left, int top, int right, int bottom) {
        mRecyclerView.setPadding(left, top, right, bottom);
    }

    public int getListWidth() {
        return (mRecyclerView.getMeasuredWidth()
                - mRecyclerView.getPaddingLeft()
                - mRecyclerView.getPaddingRight())
                / getSpanCount();
    }
}
