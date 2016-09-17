package com.wosloveslife.takemeizi.adapter;

import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.wosloveslife.takemeizi.R;
import com.wosloveslife.takemeizi.baserecyclerviewadapter.adapter.BaseRecyclerViewAdapter;
import com.wosloveslife.takemeizi.baserecyclerviewadapter.viewHolder.BaseRecyclerViewHolder;
import com.wosloveslife.takemeizi.bean.BaiduPhotoData;
import com.wosloveslife.takemeizi.utils.ImageViewUtils;

/**
 * Created by YesingBeijing on 2016/9/13.
 */
public class PhotoListAdapter extends BaseRecyclerViewAdapter<BaiduPhotoData.ImgsBean> {
    int mRootWidth;

    @Override
    protected BaseRecyclerViewHolder<BaiduPhotoData.ImgsBean> onCreateItemViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_images, null);

        return new BaseRecyclerViewHolder<BaiduPhotoData.ImgsBean>(view) {

            /** 三层包裹, 根节点是为了让CardView之间产生间距 */
            private FrameLayout mRootView;
            /** 有层级和圆角的卡片效果 */
            private CardView mCardView;
            /** 显示图片的控件 */
            private ImageView mIvMeizi;

            @Override
            public void onCreateView(View view) {
                mCardView = (CardView) view.findViewById(R.id.card_view);
                mIvMeizi = (ImageView) view.findViewById(R.id.iv_meizi);
                mRootView = (FrameLayout) view.findViewById(R.id.id_root_view);
            }

            @Override
            public void onBind(final BaiduPhotoData.ImgsBean data, int position) {
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mCardView.getLayoutParams();

                ImageViewUtils.autoFit(mCardView, mRootWidth
                                - params.leftMargin
                                - params.rightMargin,
                        data.getThumbLargeWidth(), data.getThumbLargeHeight());

                Glide.with(mView.getContext())
                        .load(data.getThumbLargeUrl())
                        .into(new ImageViewTarget<GlideDrawable>(mIvMeizi) {
                            @Override
                            protected void setResource(GlideDrawable resource) {
                                mIvMeizi.setImageDrawable(resource);
                            }
                        });
            }
        };
    }

    public void setRootWidth(int rootWidth) {
        mRootWidth = rootWidth;
    }
}
