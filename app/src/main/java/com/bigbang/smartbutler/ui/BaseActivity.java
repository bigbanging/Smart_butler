package com.bigbang.smartbutler.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * Author: litte
 * Created on 2018/8/2  16:14
 * Project Name:Smartbutler
 * Package Name:com.bigbang.smartbutler.ui
 * Description: Activity的基类
 * Copyright (c) 2018, gwl All Rights Reserved.
 */
/*
主要功能：
1、统一的属性
2、统一的接口
3、统一的方法
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //显示系统默认返回键
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    /*
    菜单栏操作
     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
                default:
                    break;
        }
        return super.onOptionsItemSelected(item);
    }
}
