package com.bigbang.smartbutler.entity;

/**
 * Author: litte
 * Created on 2018/8/7  11:11
 * Project Name:Smartbutler
 * Package Name:com.bigbang.smartbutler.entity
 * Description: 微信精选文章实体类
 * Copyright (c) 2018, gwl All Rights Reserved.
 */
public class Article {

    /*标题 */
    private String title;
    /*出处*/
    private String source;
    /*标题*/
    private String url;
    /*封面图片*/
    private String firstImg;

    public String getFirstImg() {
        return firstImg;
    }

    public void setFirstImg(String firstImg) {
        this.firstImg = firstImg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
