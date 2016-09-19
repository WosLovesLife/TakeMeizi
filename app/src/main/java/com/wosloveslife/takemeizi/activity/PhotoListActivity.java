package com.wosloveslife.takemeizi.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wosloveslife.takemeizi.R;
import com.wosloveslife.takemeizi.adapter.PhotoListAdapter;
import com.wosloveslife.takemeizi.bean.BaiduPhotoData;
import com.wosloveslife.takemeizi.interfaces.IDataUpdate;
import com.wosloveslife.takemeizi.presenter.PhotoListActivityPresenter;
import com.wosloveslife.takemeizi.utils.Dp2Px;
import com.wosloveslife.takemeizi.view.BaseRefreshRecyclerView;
import com.wosloveslife.takemeizi.view.StaggerGridRefreshRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoListActivity extends AppCompatActivity implements IDataUpdate<BaiduPhotoData> {
    @BindView(R.id.refresh_recycler_view)
    StaggerGridRefreshRecyclerView mRecyclerView;

    private PhotoListActivityPresenter mPresenter;
    private PhotoListAdapter mPhotoListAdapter;

    private int mCurrentPosition = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_list);

        ButterKnife.bind(this);

        mPresenter = new PhotoListActivityPresenter(this, this);

        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mPresenter.unsubscrible();
    }

    private void initView() {
        mPhotoListAdapter = new PhotoListAdapter();
        final int padding = Dp2Px.toPX(this, 3);
        mRecyclerView.setListPadding(padding, 0, padding, 0);
        mRecyclerView.setOnSizeChangedListener(new BaseRefreshRecyclerView.OnSizeChangeListener() {
            @Override
            public void onSizeChanged(int w, int h, int oldW, int oldH) {
                mPhotoListAdapter.setRootWidth(mRecyclerView.getListWidth());
            }
        });
        mRecyclerView.setOnRefreshListener(new StaggerGridRefreshRecyclerView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }

            @Override
            public void onLoadMore() {
                getMoreData();
            }
        });
        mRecyclerView.setAdapter(mPhotoListAdapter);
        mRecyclerView.setLoadMoreEnable(true);
        mRecyclerView.startRefreshing();
        refreshData();
    }

    public void refreshData() {
        mPresenter.getImageUrls(0,false);
    }

    public void getMoreData() {
        mPresenter.getImageUrls(mCurrentPosition,true);
    }

    @Override
    public void onUpdateData(BaiduPhotoData data, boolean appended) {
        if (data==null)return;
        List<BaiduPhotoData.ImgsBean> imgs = data.getImgs();
        if (appended) {
            mPhotoListAdapter.addData(imgs);
            if (imgs != null) {
                mCurrentPosition += imgs.size();
            }
        } else {
            mPhotoListAdapter.setData(imgs);
            mCurrentPosition = 0;
        }

        mRecyclerView.refreshingComplete();
    }
}
