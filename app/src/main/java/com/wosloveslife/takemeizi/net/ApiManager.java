package com.wosloveslife.takemeizi.net;

import com.wosloveslife.takemeizi.App;
import com.wosloveslife.takemeizi.bean.BaiduPhotoData;
import com.wosloveslife.takemeizi.bean.MeizhiData;
import com.wosloveslife.takemeizi.utils.NetWorkUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 * Created by YesingBeijing on 2016/9/14.
 */
public class ApiManager {
    private static ApiManager sApiManager;
    //TODO:配置主机名
    private static final String URL_HOLST = "http://image.baidu.com/data/";

    private final Object mSyncBlock = new Object();

    //=================================基础网络配置 - start================================
    private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());
            if (NetWorkUtil.isNetWorkAvailable(App.getContext())) {
                int maxAge = 60; // 在线缓存在1分钟内可读取
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 28; // 离线时缓存保存4周
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        }
    };
    private static File httpCacheDirectory = new File(App.getContext().getCacheDir(), "OnlineRetailer");
    private static int cacheSize = 20 * 1024 * 1024; // 10 MiB
    private static Cache cache = new Cache(httpCacheDirectory, cacheSize);
    private static OkHttpClient sOkHttpClient = new OkHttpClient.Builder()
            .addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
            .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
            .cache(cache)
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .build();
    //=================================基础网络配置 - end================================

    //=================================APIs - start================================
    private MeizhiData.APiMeizi mAPiMeizi;
    private BaiduPhotoData.Api mApiBaiduPhoto;
    //=================================APIs - end================================

    public static ApiManager getInstance() {
        if (sApiManager == null) {
            synchronized (ApiManager.class) {
                if (sApiManager == null) {
                    sApiManager = new ApiManager();
                }
            }
        }
        return sApiManager;
    }

    private ApiManager() {
    }

    public MeizhiData.APiMeizi getAPiMeizi() {
        if (mAPiMeizi == null) {
            synchronized (mSyncBlock) {
                if (mAPiMeizi == null) {
                    mAPiMeizi = new Retrofit.Builder()
                            .baseUrl(URL_HOLST)
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .client(sOkHttpClient)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                            .create(MeizhiData.APiMeizi.class);
                }
            }
        }
        return mAPiMeizi;
    }

    public BaiduPhotoData.Api getApiBaiduPhoto() {
        if (mApiBaiduPhoto == null) {
            synchronized (mSyncBlock) {
                if (mApiBaiduPhoto == null) {
                    mApiBaiduPhoto = new Retrofit.Builder()
                            .baseUrl(URL_HOLST)
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .client(sOkHttpClient)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                            .create(BaiduPhotoData.Api.class);
                }
            }
        }
        return mApiBaiduPhoto;
    }
}
