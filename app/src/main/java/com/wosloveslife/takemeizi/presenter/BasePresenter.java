package com.wosloveslife.takemeizi.presenter;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by YesingBeijing on 2016/9/12.
 */
public class BasePresenter {
    private CompositeSubscription mCompositeSubscription;

    public void addSubscription(Subscription subscription){
        if (mCompositeSubscription == null){
            mCompositeSubscription = new CompositeSubscription();
        }

        mCompositeSubscription.add(subscription);
    }

    public void unsubscrible(){
        if (mCompositeSubscription!=null){
            mCompositeSubscription.unsubscribe();
        }
    }
}
