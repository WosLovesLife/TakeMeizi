package com.wosloveslife.takemeizi;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by YesingBeijing on 2016/9/12.
 */
public class App extends Application {
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();

        sContext = getApplicationContext();

        initFresco();
    }

    public static Context getContext(){
        return sContext;
    }

    private void initFresco() {
        Fresco.initialize(this);
    }
}
