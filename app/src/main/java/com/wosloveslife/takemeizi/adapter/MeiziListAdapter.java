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
import com.wosloveslife.takemeizi.bean.MeizhiData;
import com.wosloveslife.takemeizi.utils.ImageViewUtils;

/**
 * Created by YesingBeijing on 2016/9/13.
 */
public class MeiziListAdapter extends BaseRecyclerViewAdapter<MeizhiData.Meizi> {
    int mRootWidth;

    @Override
    protected BaseRecyclerViewHolder<MeizhiData.Meizi> onCreateItemViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_images, null);

        return new BaseRecyclerViewHolder<MeizhiData.Meizi>(view) {

            private CardView mCardView;
            private ImageView mIvMeizi;

            @Override
            public void onCreateView(View view) {
                mCardView = (CardView) view.findViewById(R.id.card_view);
                mIvMeizi = (ImageView) view.findViewById(R.id.iv_meizi);
            }

            @Override
            public void onBind(final MeizhiData.Meizi data,int position) {

                Glide.with(mView.getContext())
                        .load(data.getUrl())
                        .into(new ImageViewTarget<GlideDrawable>(mIvMeizi) {
                            @Override
                            protected void setResource(GlideDrawable resource) {
                                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mCardView.getLayoutParams();

                                ImageViewUtils.autoFit(mCardView, mRootWidth - params.leftMargin - params.rightMargin,
                                        resource.getIntrinsicWidth(), resource.getIntrinsicHeight());

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
