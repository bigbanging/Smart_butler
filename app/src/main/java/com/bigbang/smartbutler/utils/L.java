package com.bigbang.smartbutler.utils;

import android.util.Log;

/**
 * Author: litte
 * Created on 2018/8/2  17:02
 * Project Name:Smartbutler
 * Package Name:com.bigbang.smartbutler.utils
 * Description: Log for Debug
 * Copyright (c) 2018, gwl All Rights Reserved.
 */
public class L {
    // debug开关
    private static final boolean DEBUG = true;
    //TAG
    private static final String TAG = "SmartButler";
    //DIWEF 五级调试模式
    public static void d(String text) {
        if (DEBUG) {
            Log.d(TAG, text);
        }
    }
    public static void i(String text) {
        if (DEBUG) {
            Log.i(TAG, text);
        }
    }
    public static void w(String text) {
        if (DEBUG) {
            Log.w(TAG, text);
        }
    }
    public static void e(String text) {
        if (DEBUG) {
            Log.e(TAG, text);
        }
    }
    public static void f(String text) {
        if (DEBUG) {
            Log.wtf(TAG, text);
        }
    }
}
