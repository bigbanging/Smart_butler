package com.bigbang.smartbutler.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Author: litte
 * Created on 2018/8/2  17:49
 * Project Name:Smartbutler
 * Package Name:com.bigbang.smartbutler.utils
 * Description: SharedPreference 工具类
 * Copyright (c) 2018, gwl All Rights Reserved.
 */
public class SharedPreferenceUtils {
    private static final String FILENAME = "config";

    public static void putString(Context context, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(key, value).apply();
    }
    public static String  getString(Context context,String key, String defValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key,defValue);
    }

    public static void putInt(Context context, String key, int value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt(key, value).apply();
    }
    public static int  getInt(Context context,String key, int defValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key,defValue);
    }

    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(key, value).apply();
    }
    public static boolean  getBoolean(Context context,String key, boolean defValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key,defValue);
    }

    // 删除单个
    public static void deleteSingle(Context context,String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().remove(key).apply();
    }
    // 删除所有
    public static void deleteAll(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
    }
}
