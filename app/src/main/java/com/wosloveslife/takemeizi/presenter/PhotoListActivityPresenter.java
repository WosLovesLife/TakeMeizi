package com.wosloveslife.takemeizi.presenter;

import android.content.Context;

import com.wosloveslife.takemeizi.adapter.RxCall;
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
public class PhotoListActivityPresenter extends BasePresenter {
    private static final String TAG = "MainActivityPresenter";

    private Context mContext;
    private IDataUpdate<BaiduPhotoData.ImgsBean> mDataUpdate;

    public PhotoListActivityPresenter(Context context, IDataUpdate<BaiduPhotoData.ImgsBean> update) {
        mContext = context;
        mDataUpdate = update;
    }

    private Observable<MeizhiData> getGankMeiziApi(int page) {
        return ApiManager.getInstance()
                .getAPiMeizi()
                .getMeizhiData(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Observable<BaiduPhotoData> getBaiduPhotoApi(int pn) {
        /*?col=美女&tag=小清新&sort=0&pn={pn}&rn=10&p=channel&from=1*/
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("col", "美女");
        hashMap.put("tag", "小清新");
        hashMap.put("sort", "0");
        hashMap.put("pn", pn + "");
        hashMap.put("rn", "10");
        hashMap.put("p", "channel");
        hashMap.put("from", "1");
        return ApiManager.getInstance()
                .getApiBaiduPhoto()
                .getBaiduPhotoData(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void getImagesUrls() {
        getBaiduPhotoApi(0).subscribe(new RxCall<BaiduPhotoData>() {
            @Override
            public void onNext(BaiduPhotoData baiduPhotoData) {
                super.onNext(baiduPhotoData);
                if (mDataUpdate != null) {
                    mDataUpdate.updateData(baiduPhotoData.getImgs(), false);
                }
            }
        });
    }

    public void getImagesUrls(int position) {
        getBaiduPhotoApi(position).subscribe(new RxCall<BaiduPhotoData>() {
            @Override
            public void onNext(BaiduPhotoData baiduPhotoData) {
                super.onNext(baiduPhotoData);
                if (mDataUpdate != null) {
                    mDataUpdate.updateData(baiduPhotoData.getImgs(), true);
                }
            }
        });
    }
}
