package com.bigbang.smartbutler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigbang.smartbutler.R;
import com.bigbang.smartbutler.entity.Beauty;
import com.bigbang.smartbutler.utils.PicassoUtils;

import java.util.List;

/**
 * Author: litte
 * Created on 2018/8/8  16:37
 * Project Name:Smartbutler
 * Package Name:com.bigbang.smartbutler.adapter
 * Description: BeautyAdapter
 * Copyright (c) 2018, gwl All Rights Reserved.
 */
public class BeautyAdapter extends BaseAdapter {
    private Context mContext;
    private List<Beauty> mBeautyList;
    private LayoutInflater mLayoutInflater;
    private int width;
    private WindowManager mWindowManager;

    public BeautyAdapter(Context context, List<Beauty> beautyList) {
        mContext = context;
        mBeautyList = beautyList;
        mLayoutInflater = LayoutInflater.from(context);
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        width = mWindowManager.getDefaultDisplay().getWidth();

    }

    @Override
    public int getCount() {
        return mBeautyList.size();
    }

    @Override
    public Object getItem(int position) {
        return mBeautyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.beauty_item, parent, false);
            holder.iv_beauty = convertView.findViewById(R.id.iv_beauty);
            holder.tv_publishTime = convertView.findViewById(R.id.tv_publishTime);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Beauty beauty = mBeautyList.get(position);
        holder.tv_publishTime.setText(beauty.getPublishedAt());
        PicassoUtils.cropImage(mContext,beauty.getUrl(),width/2,400,holder.iv_beauty);
        return convertView;
    }

    private class ViewHolder {
        private ImageView iv_beauty;
        private TextView tv_publishTime;
    }
}
