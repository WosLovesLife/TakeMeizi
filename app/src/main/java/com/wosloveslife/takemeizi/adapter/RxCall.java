package com.wosloveslife.takemeizi.adapter;

import android.util.Log;

import rx.Subscriber;

/**
 * Created by zhangh on 2016/9/17.
 */
public abstract class RxCall<T> extends Subscriber<T> {
    private static final String TAG = "RxCall";

    @Override
    public void onCompleted() {
        Log.d(TAG, "onCompleted: 网络请求完成完成");
    }

    @Override
    public void onError(Throwable e) {
        Log.e(TAG, "onError: 发生错误", e);
    }

    @Override
    public void onNext(T t) {
        Log.d(TAG, "onNext: 收到数据: " + t);
    }
}
