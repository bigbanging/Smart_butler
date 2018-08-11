package com.bigbang.smartbutler.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Author: litte
 * Created on 2018/8/3  11:43
 * Project Name:Smartbutler
 * Package Name:com.bigbang.smartbutler.utils
 * Description: 工具类
 * Copyright (c) 2018, gwl All Rights Reserved.
 */
public class UtilTools {
    public static void fontType(Context context, TextView textView) {
        // 设置字体
        Typeface fontType = Typeface.createFromAsset(context.getAssets(), "font/FONT.TTF");
        textView.setTypeface(fontType);
    }


    public static void setHeaderImg(Context context, ImageView imageView) {
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        // 第一步：将bitmap压缩成字节输出流
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        //第二步：利用base64将字节输出流转化成String
        byte[] bytes = byteArrayOutputStream.toByteArray();
        String imageString = Base64.encodeToString(bytes, Base64.DEFAULT);
        //第三步：将转换的String 保存起来
        SharedPreferenceUtils.putString(context, StaticClass.IMAGE_HEADER, imageString);
    }

    public static void getHeaderFromShared(Context context,ImageView imageView) {
        String headerImg = SharedPreferenceUtils.getString(context, StaticClass.IMAGE_HEADER, "");
        if (headerImg != null) {
            byte[] decode = Base64.decode(headerImg, Base64.DEFAULT);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(decode);
            //生成bitmap
            Bitmap bitmap = BitmapFactory.decodeStream(byteArrayInputStream);
            imageView.setImageBitmap(bitmap);
        }
    }
}
