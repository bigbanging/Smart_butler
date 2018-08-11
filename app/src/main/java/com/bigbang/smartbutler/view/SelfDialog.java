package com.bigbang.smartbutler.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.bigbang.smartbutler.R;

/**
 * Author: litte
 * Created on 2018/8/5  10:32
 * Project Name:Smartbutler
 * Package Name:com.bigbang.smartbutler.view
 * Description: 自定义Dialog
 * Copyright (c) 2018, gwl All Rights Reserved.
 */
public class SelfDialog extends Dialog {
    // 定义模板
    public SelfDialog(Context context,int layout,int style) {
        this(context, WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT, layout, style, Gravity.CENTER);
    }

    // 定义属性
    public SelfDialog(Context context, int width, int height, int layout, int style, int gravity, int anim) {
        super(context,style);
        //设置属性
        setContentView(layout);
        Window window = getWindow();
        assert window != null;
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = gravity;
        window.setAttributes(layoutParams);
        window.setWindowAnimations(anim);
    }

    //实例
    public SelfDialog(Context context, int width, int height, int layout, int style, int gravity) {
        this(context,width,height,layout,style,gravity,R.style.dialogTheme);
    }
}
