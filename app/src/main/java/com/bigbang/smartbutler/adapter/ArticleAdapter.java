package com.bigbang.smartbutler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigbang.smartbutler.R;
import com.bigbang.smartbutler.entity.Article;
import com.bigbang.smartbutler.utils.PicassoUtils;

import java.util.List;

/**
 * Author: litte
 * Created on 2018/8/7  17:37
 * Project Name:Smartbutler
 * Package Name:com.bigbang.smartbutler.adapter
 * Description: 微信精选文章适配器
 * Copyright (c) 2018, gwl All Rights Reserved.
 */
public class ArticleAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<Article> mList;

    public ArticleAdapter(Context context, List<Article> list) {
        mContext = context;
        mList = list;
        mLayoutInflater = LayoutInflater.from(context);
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
            convertView = mLayoutInflater.inflate(R.layout.we_chat_article_item, null);
            holder.iv_news = convertView.findViewById(R.id.iv_news_pic);
            holder.tv_title = convertView.findViewById(R.id.tv_title);
            holder.tv_source = convertView.findViewById(R.id.tv_source);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Article article = mList.get(position);
        holder.tv_title.setText(article.getTitle());
        holder.tv_source.setText(article.getSource());
//        PicassoUtils.placeHolder(article.getFirstImg(),R.drawable.defaul,R.drawable.error,holder.iv_news);
//        PicassoUtils.placeHolder(mContext,article.getFirstImg(),R.drawable.defaul,R.drawable.error,holder.iv_news);
        return convertView;
    }

    private class ViewHolder {
        private ImageView iv_news;
        private TextView tv_title;
        private TextView tv_source;
    }
}
