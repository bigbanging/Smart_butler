package com.bigbang.smartbutler.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bigbang.smartbutler.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

/**
 * Author: litte
 * Created on 2018/8/8  15:21
 * Project Name:Smartbutler
 * Package Name:com.bigbang.smartbutler.utils
 * Description: Picasso工具类
 * Copyright (c) 2018, gwl All Rights Reserved.
 */
public class PicassoUtils {
    //默认加载方式无任何处理
    public static void loadImge(Context context,String url, ImageView imageView) {
        Picasso.with(context).load(url).into(imageView);
    }

    //IMAGE TRANSFORMATIONS Transform images to better fit into layouts and to reduce memory size.
    //添加裁剪的图片加载方式
    public static void cropImage(Context context,String url,int width,int height,ImageView imageView) {
        Picasso.with(context).load(url).resize(width, height).centerCrop().into(imageView);
    }

    //PLACE HOLDERS 选择有替代图片的加载方式
    public static void placeHolder(Context context,String url,int placeholder,int errorHolder,ImageView imageView) {
        Picasso.with(context).load(url).placeholder(placeholder).error(errorHolder).into(imageView);
    }
    public static void transformation(Context context,String url,ImageView imageView) {
        Picasso.with(context).load(url).transform(new CropSquareTransformation()).into(imageView);
    }
    //裁剪  更高级的效果指定自定义转换
    public static class CropSquareTransformation implements Transformation {
        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;
            Bitmap result = Bitmap.createBitmap(source, x, y, size, size);
            if (result != source) {
                source.recycle();
            }
            return result;
        }

        @Override
        public String key() {
            return "Picasso";
        }
    }
}
