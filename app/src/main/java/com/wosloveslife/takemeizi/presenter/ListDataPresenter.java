package com.wosloveslife.takemeizi.presenter;

import android.content.Context;

import com.wosloveslife.takemeizi.interfaces.IDataUpdate;
import com.wosloveslife.takemeizi.net.ApiManager;
import com.wosloveslife.takemeizi.utils.assist.Logger;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by YesingBeijing on 2016/9/19.
 */
public abstract class ListDataPresenter<T> extends BasePresenter {
    protected IDataUpdate<T> mIDataUpdate;

    public ListDataPresenter(Context context, IDataUpdate<T> iDataUpdate) {
        mIDataUpdate = iDataUpdate;
    }

    protected void getData(final boolean append) {
        Subscription subscription = getApiData(ApiManager.getInstance())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onSubscriber(append));
        addSubscription(subscription);
    }

    protected abstract Observable<T> getApiData(ApiManager instance);

    protected Subscriber<T> onSubscriber(final boolean append) {
        return new Subscriber<T>() {
            @Override
            public void onCompleted() {
                Logger.logD("onSubscriber() 请求数据完成");
            }

            @Override
            public void onError(Throwable e) {
                Logger.logE("onSubscriber() 发生错误 : ", e);
            }

            @Override
            public void onNext(T t) {
                Logger.logD("onSubscriber() 获取到数据 : " + t);
                if (mIDataUpdate == null) return;
                mIDataUpdate.onUpdateData(t, append);
            }
        };
    }
}