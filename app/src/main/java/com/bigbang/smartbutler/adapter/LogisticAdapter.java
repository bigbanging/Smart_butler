package com.bigbang.smartbutler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bigbang.smartbutler.R;
import com.bigbang.smartbutler.entity.LogisticInformation;

import java.util.List;

/**
 * Author: litte
 * Created on 2018/8/6  11:39
 * Project Name:Smartbutler
 * Package Name:com.bigbang.smartbutler.adapter
 * Description: 物流信息适配器
 * Copyright (c) 2018, gwl All Rights Reserved.
 */
public class LogisticAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<LogisticInformation> mList;
    private LogisticInformation data;

    public LogisticAdapter(Context context, List<LogisticInformation> list) {
        mContext = context;
        mList = list;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
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
            convertView = mLayoutInflater.inflate(R.layout.logistic_info, parent, false);
            holder.tvDatetime = convertView.findViewById(R.id.tv_datetime);
            holder.tvZone = convertView.findViewById(R.id.tv_zone);
            holder.tvRemark = convertView.findViewById(R.id.tv_remark);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        data = mList.get(position);
        holder.tvDatetime.setText(data.getDatetime());
        holder.tvRemark.setText(data.getRemark());
        holder.tvZone.setText(data.getZone());
        return convertView;
    }
    private class ViewHolder {
        private TextView tvRemark;
        private TextView tvZone;
        private TextView tvDatetime;

    }
}
