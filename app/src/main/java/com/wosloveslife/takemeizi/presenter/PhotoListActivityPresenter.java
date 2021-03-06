package com.wosloveslife.takemeizi.presenter;

import android.content.Context;

import com.wosloveslife.takemeizi.bean.BaiduPhotoData;
import com.wosloveslife.takemeizi.bean.MeizhiData;
import com.wosloveslife.takemeizi.interfaces.IDataUpdate;
import com.wosloveslife.takemeizi.net.ApiManager;

import java.util.HashMap;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by YesingBeijing on 2016/9/12.
 */
public class PhotoListActivityPresenter extends ListDataPresenter<BaiduPhotoData> {
    private static final String TAG = "MainActivityPresenter";

    private int mPn;

    public PhotoListActivityPresenter(Context context, IDataUpdate<BaiduPhotoData> iDataUpdate) {
        super(context, iDataUpdate);
    }

    private Observable<MeizhiData> getGankMeiziApi(int page) {
        return ApiManager.getInstance()
                .getAPiMeizi()
                .getMeizhiData(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
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
