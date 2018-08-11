package com.bigbang.smartbutler.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.bigbang.smartbutler.R;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: litte
 * Created on 2018/8/8  13:43
 * Project Name:Smartbutler
 * Package Name:com.bigbang.smartbutler.ui
 * Description: 文章界面
 * Copyright (c) 2018, gwl All Rights Reserved.
 */
public class ArticleActivity extends BaseActivity {
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.webView)
    WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        initView();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initView() {
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        final String url = intent.getStringExtra("url");
        Objects.requireNonNull(getSupportActionBar()).setTitle(title);
        //加载网页设计的逻辑
        webView.getSettings().setJavaScriptEnabled(true);
        //支持缩放
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        //接口回调
        webView.setWebChromeClient(new WebViewClient());
        //加载
        webView.loadUrl(url);
        //本地显示
        webView.setWebViewClient(new android.webkit.WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(url);
                // 表示接受
                return true;
            }
        });
    }

    private class WebViewClient extends WebChromeClient {
        // 网页加载 进度条的变化
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                progressBar.setVisibility(View.GONE);
            }
            super.onProgressChanged(view, newProgress);
        }
    }
}
