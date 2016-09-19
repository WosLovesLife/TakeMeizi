package com.wosloveslife.takemeizi.presenter;

import android.content.Context;

import com.wosloveslife.takemeizi.bean.BaiduPhotoData;
import com.wosloveslife.takemeizi.interfaces.IDataUpdate;
import com.wosloveslife.takemeizi.net.ApiManager;

import java.util.HashMap;

import rx.Observable;

/**
 * Created by YesingBeijing on 2016/9/19.
 */
public class PhotoListActivityPresenter1 extends ListDataPresenter<BaiduPhotoData> {
    private int mPn;

    public PhotoListActivityPresenter1(Context context, IDataUpdate<BaiduPhotoData> iDataUpdate) {
        super(context, iDataUpdate);
    }

    @Override
    protected Observable<BaiduPhotoData> getApiData(ApiManager instance) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("col", "美女");
        hashMap.put("tag", "小清新");
        hashMap.put("sort", "0");
        hashMap.put("pn", mPn + "");
        hashMap.put("rn", "10");
        hashMap.put("p", "channel");
        hashMap.put("from", "1");
        return instance
                .getApiBaiduPhoto()
                .getBaiduPhotoData(hashMap);
    }

    public void getImageUrls(int page, boolean append) {
        mPn = page;
        getData(append);
    }
}
