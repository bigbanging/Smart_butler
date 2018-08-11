package com.bigbang.smartbutler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bigbang.smartbutler.R;
import com.bigbang.smartbutler.entity.ChatData;

import java.util.List;

/**
 * Author: litte
 * Created on 2018/8/6  17:24
 * Project Name:Smartbutler
 * Package Name:com.bigbang.smartbutler.adapter
 * Description: 聊天界面的适配器
 * Copyright (c) 2018, gwl All Rights Reserved.
 */
public class ChatAdapter extends BaseAdapter {
    private Context mContext;
    private List<ChatData> mChatData;
    private LayoutInflater mLayoutInflater;

    public static final int TYPE_LEFT = 1;
    public static final int TYPE_RIGHT = 2;

    public ChatAdapter(Context context, List<ChatData> chatData) {
        mContext = context;
        mChatData = chatData;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mChatData.size();
    }

    @Override
    public Object getItem(int position) {
        return mChatData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        ChatData chatData = mChatData.get(position);
        int type = chatData.getType();
        return type;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderLeft holderLeft = null;
        ViewHolderRight holderRight = null;
        int itemViewType = getItemViewType(position);
        if (convertView == null) {
            holderLeft = new ViewHolderLeft();
            holderRight = new ViewHolderRight();
            switch (itemViewType) {
                case TYPE_LEFT:
                    convertView = mLayoutInflater.inflate(R.layout.chat_left,null);
                    holderLeft.tv_chat_left = convertView.findViewById(R.id.tv_chat_left);
                    convertView.setTag(holderLeft);
                    break;
                case TYPE_RIGHT:
                    convertView = mLayoutInflater.inflate(R.layout.chat_right, null);
                    holderRight.tv_chat_right = convertView.findViewById(R.id.tv_chat_right);
                    convertView.setTag(holderRight);
                    break;
                    default:
                        break;
            }
        } else
            switch (itemViewType) {
                case TYPE_LEFT:
                    holderLeft = (ViewHolderLeft) convertView.getTag();
                    break;
                case TYPE_RIGHT:
                    holderRight = (ViewHolderRight) convertView.getTag();
                    break;
                    default:break;
            }

        // 设置内容
        ChatData chatData = mChatData.get(position);
        switch (itemViewType) {
            case TYPE_LEFT:
                holderLeft.tv_chat_left.setText(chatData.getContent());
                break;
            case TYPE_RIGHT:
                holderRight.tv_chat_right.setText(chatData.getContent());
                break;
                default:
                    break;
        }
        return convertView;
    }

    private class ViewHolderLeft {
        private TextView tv_chat_left;
    }
    private class ViewHolderRight {
        private TextView tv_chat_right;

    }
}
