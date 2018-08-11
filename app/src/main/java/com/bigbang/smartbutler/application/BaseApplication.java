package com.bigbang.smartbutler.application;

import android.app.Application;

import com.bigbang.smartbutler.utils.StaticClass;
import com.tencent.bugly.crashreport.CrashReport;

import cn.bmob.v3.Bmob;

/**
 * Author: litte
 * Created on 2018/8/2  16:12
 * Project Name:Smartbutler
 * Package Name:com.bigbang.smartbutler.application
 * Description: application 的基类
 * Copyright (c) 2018, gwl All Rights Reserved.
 */
public class BaseApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        //tencent Bugly 初始化
        CrashReport.initCrashReport(getApplicationContext(), StaticClass.BUGLY_KEY, true);
        // Bmob初始化
        Bmob.initialize(this, StaticClass.BMOB_KEY);
    }
}
