package com.wosloveslife.takemeizi.baserecyclerviewadapter.adapter;

import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.wosloveslife.takemeizi.baserecyclerviewadapter.viewHolder.BaseRecyclerViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 基础Adapter,继承该Adapter, 实现相关方法
 * Created by WosLovesLife on 2016/7/13.
 */
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewHolder<T>> {

    private static final int BASE_ITEM_TYPE_HEADER = 100000;
    private static final int BASE_ITEM_TYPE_FOOTER = 200000;

    protected List<T> mData;

    /**
     * Header集合
     * SparseArrayCompat类似于Map，只不过在某些情况下比Map的性能要好，
     * 并且只能存储key为int的情况。
     */
    private SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat<>();
    /** Footer集合 */
    private SparseArrayCompat<View> mFooterViews = new SparseArrayCompat<>();

    public BaseRecyclerViewAdapter() {
        this(new ArrayList<T>());
    }

    public BaseRecyclerViewAdapter(List<T> data) {
        mData = new ArrayList<>();

        setData(data);
    }

    public boolean isHeaderViewPos(int position) {
        return position < getHeadersCount();
    }

    public boolean isFooterViewPos(int position) {
        return position >= getHeadersCount() + getRealItemCount();
    }

    public void addHeaderView(View v) {
        mHeaderViews.put(mHeaderViews.size() + BASE_ITEM_TYPE_HEADER, v);
    }

    public void addFooterView(View v) {
        mFooterViews.put(mFooterViews.size() + BASE_ITEM_TYPE_FOOTER, v);
    }

    public int getHeadersCount() {
        return mHeaderViews.size();
    }

    public int getFootersCount() {
        return mFooterViews.size();
    }

    @Override
    public BaseRecyclerViewHolder<T> onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderViews.get(viewType) != null) {
            return new BaseRecyclerViewHolder<T>(mHeaderViews.get(viewType)) {
                @Override
                public void onBind(T data, int position) {
                }
            };
        } else if (mFooterViews.get(viewType) != null) {
            return new BaseRecyclerViewHolder<T>(mFooterViews.get(viewType)) {
                @Override
                public void onBind(T data, int position) {
                }
            };
        }
        return onCreateItemViewHolder(parent);
    }

    /** 重写此方法 创建一般条目的ViewHolder时调用 */
    protected abstract BaseRecyclerViewHolder<T> onCreateItemViewHolder(ViewGroup parent);

    @Override
    public int getItemViewType(int position) {
        if (isHeaderViewPos(position)) {
            return mHeaderViews.keyAt(position);
        } else if (isFooterViewPos(position)) {
            return mFooterViews.keyAt(position - getHeadersCount() - getRealItemCount());
        }
        return position - getHeadersCount();
    }

    private int getRealItemCount() {
        return mData.size();
    }

    /** 在该方法中对不同的条目类型进行区分, 并算出每种类型对应的数据数据position */
    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder<T> holder, int position) {
        if (isHeaderViewPos(position)) return;
        if (isFooterViewPos(position)) return;

        holder.onBind(mData.get(position - getHeadersCount()), position);
    }

    @Override
    public int getItemCount() {
        return getHeadersCount() + getFootersCount() + getRealItemCount();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            final GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();

            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {

                @Override
                public int getSpanSize(int position) {
                    int itemViewType = getItemViewType(position);
                    if (mHeaderViews.get(itemViewType) != null) {
                        return gridLayoutManager.getSpanCount();
                    } else if (mFooterViews.get(itemViewType) != null) {
                        return gridLayoutManager.getSpanCount();
                    }
                    if (spanSizeLookup != null) {
                        return spanSizeLookup.getSpanSize(position);
                    }
                    return 1;
                }
            });
        }
    }

    @Override
    public void onViewAttachedToWindow(BaseRecyclerViewHolder holder) {
        int position = holder.getLayoutPosition();
        ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
        if (params != null && params instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) params;
            if (isHeaderViewPos(position) || isFooterViewPos(position)) {
                p.setFullSpan(true);
            }
        }
    }

    public void setData(List<T> data) {
        clearData();
        if (data != null) {
            mData.addAll(data);
        }
        notifyDataSetChanged();
    }

    public void addData(List<T> data) {
        if (data != null) {
            mData.addAll(data);
            notifyDataSetChanged();
        }
    }

    public void clearData() {
        mData.clear();
    }

    public T getData(int position) {
        if (position >= mData.size()) return null;

        return mData.get(position);
    }
}