package com.wosloveslife.takemeizi.baserecyclerviewadapter.adapter;

import android.support.annotation.Nullable;
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

    /** Header条目的viewType的起始值 */
    private static final int BASE_ITEM_TYPE_HEADER = 100000;
    /** Footer条目的viewType的起始值 */
    private static final int BASE_ITEM_TYPE_FOOTER = 200000;

    /** 普通数据的数据集合 */
    protected List<T> mData;

    /**
     * Header集合
     * SparseArrayCompat类似于Map，只不过在某些情况下比Map的性能要好，
     * 并且只能存储key为int的情况。
     */
    private SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat<>();
    /** Footer集合 */
    private SparseArrayCompat<View> mFooterViews = new SparseArrayCompat<>();

    //============================构造-start===========================
    public BaseRecyclerViewAdapter() {
        this(new ArrayList<T>());
    }

    public BaseRecyclerViewAdapter(List<T> data) {
        mData = new ArrayList<>();

        setData(data);
    }
    //============================构造-end===========================

    /**
     * 检查传入的position是否属于header类型
     * @param position 总数据下标
     * @return 是否属于header集合
     */
    private boolean isHeaderViewPos(int position) {
        return position < getHeadersCount();
    }

    /**
     * 检查传入的position是否属于footer类型
     * @param position 总数据下标
     * @return 是否属于footer集合
     */
    private boolean isFooterViewPos(int position) {
        return position >= getHeadersCount() + getRealItemCount();
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

    /**
     * 重写此方法 创建一般条目的ViewHolder时调用
     *
     * @param parent 父控件
     * @return ViewHolder
     */
    protected abstract BaseRecyclerViewHolder<T> onCreateItemViewHolder(ViewGroup parent);

    /**
     * 根据判断position所在的集合 从而返回对应的ViewType
     * 这样在{@link BaseRecyclerViewAdapter#onCreateViewHolder(ViewGroup, int)}
     * 中就可以根据viewType获取到对应的条目数据
     *
     * @return position对应的ViewType
     */
    @Override
    public int getItemViewType(int position) {
        if (isHeaderViewPos(position)) {
            return mHeaderViews.keyAt(position);
        } else if (isFooterViewPos(position)) {
            return mFooterViews.keyAt(position - getHeadersCount() - getRealItemCount());
        }
        return position - getHeadersCount();
    }

    /**
     * 在该方法中对不同的条目类型进行区分, 并算出每种类型对应的数据数据position
     */
    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder<T> holder, int position) {
        if (isHeaderViewPos(position)) return;
        if (isFooterViewPos(position)) return;

        holder.onBind(mData.get(position - getHeadersCount()), position);
    }

    /**
     * 所有数据的数量总和 = 头部view数量 + 尾部view数量 + 普通条目数量
     *
     * @return 所有数据的数量总和
     */
    @Override
    public int getItemCount() {
        return getHeadersCount() + getFootersCount() + getRealItemCount();
    }

    //===============================让头尾布局独占一行-start================================
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
            } else {
                int spanIndex = p.getSpanIndex();
            }
        }
    }
    //===============================让头尾布局独占一行-end================================

    //====================================================================
    //=============================对数据的操作===========================
    //====================================================================

    /**
     * 添加HeaderView
     *
     * @param v HeaderView控件对象
     */
    public void addHeaderView(View v) {
        mHeaderViews.put(mHeaderViews.size() + BASE_ITEM_TYPE_HEADER, v);
    }

    /**
     * 获取HeaderView的数量
     *
     * @return HeaderView的数量
     */
    public int getHeadersCount() {
        return mHeaderViews.size();
    }

    /**
     * 添加FooterView 独占一行
     *
     * @param v FooterView控件对象
     */
    public void addFooterView(View v) {
        mFooterViews.put(mFooterViews.size() + BASE_ITEM_TYPE_FOOTER, v);
    }

    /**
     * 获取FooterView的数量
     *
     * @return FooterView的数量
     */
    public int getFootersCount() {
        return mFooterViews.size();
    }

    /**
     * 设置普通条目数据集合, 调用该方法会先清空原有的数据,再装入新的数据
     *
     * @param t 数据集合
     */
    public void setData(List<T> t) {
        mData.clear();
        if (t != null) {
            mData.addAll(t);
        }
        notifyDataSetChanged();
    }

    /**
     * 在原有数据的基础上追加新的数据,默认追加在末尾
     *
     * @param t 数据集合
     */
    public void addData(List<T> t) {
        if (t != null) {
            mData.addAll(t);
            notifyDataSetChanged();
        }
    }

    /**
     * 普通条目的数量 = 用户传入的数据集合的大小
     *
     * @return 普通条目的数量
     */
    protected int getRealItemCount() {
        return mData.size();
    }

    /**
     * 添加单条普通数据, 插入到指定位置
     *
     * @param t        要插入的数据
     * @param position 插入到的位置(普通数据集合中的下标)
     */
    public void addItem(T t, int position) {
        if (position > getRealItemCount()) {
            position = getRealItemCount();
        } else if (position < getRealItemCount()) {
            position = 0;
        }

        mData.add(position, t);
        notifyItemInserted(position); //Attention!
    }

    /**
     * 根据对象,将对象从数据集合中移除
     *
     * @param t 要移除的数据对象
     * @return 是否移除成功
     */
    public boolean removeItem(T t) {
        int position = mData.indexOf(t);
        return removeItem(position);
    }

    /**
     * 根据下标,将对象从数据集合中移除
     *
     * @param position 要移除的数据在数据集合中的下标
     * @return 是否移除成功
     */
    public boolean removeItem(int position) {
        if (position > -1 && position < getRealItemCount()) {
            mData.remove(position);
            notifyItemRemoved(position + getHeadersCount());//Attention!
            return true;
        }
        return false;
    }

    /**
     * 根据position 获取普通数据集合中的单条数据
     * @param position 数据在集合中的下标
     * @return 如果下标越界,则返回 null
     */
    public @Nullable T getNormalData(int position) {
        if (position >= mData.size()) return null;

        return mData.get(position);
    }
}