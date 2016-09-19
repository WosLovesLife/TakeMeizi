package com.wosloveslife.takemeizi.utils.assist;

import android.util.Log;

import com.wosloveslife.takemeizi.BuildConfig;

/**
 * Created by YesingBeijing on 2016/8/29.
 */
public class Logger {
    private static final String TAG = "Logger";
    private static boolean sIsDebug = BuildConfig.DEBUG;

    public static void logD(String message) {
        if (sIsDebug) {
            Log.d(TAG, "logD: Log输出 " + message);
        }
    }

    public static void logW(String message) {
        if (sIsDebug) {
            Log.w(TAG, "logD: Log输出 " + message);
        }
    }

    public static void logE(String message) {
        if (sIsDebug) {
            Log.e(TAG, "logD: Log输出 " + message);
        }
    }

    public static void logE(String message, Throwable e) {
        if (sIsDebug) {
            Log.e(TAG, "logD: Log输出 " + message, e);
        }
    }
}
