package com.bigbang.smartbutler.entity;

/**
 * Author: litte
 * Created on 2018/8/8  16:37
 * Project Name:Smartbutler
 * Package Name:com.bigbang.smartbutler.entity
 * Description: 妹子图片实体类
 * Copyright (c) 2018, gwl All Rights Reserved.
 */
public class Beauty {

    /**
     * _id : 56cc6d26421aa95caa707fd5
     * createdAt : 2015-12-14T03:20:25.944Z
     * desc : 12.16
     * publishedAt : 2015-12-14T04:19:59.761Z
     * type : 福利
     * url : http://ww3.sinaimg.cn/large/7a8aed7bgw1eyz0qixq0wj20hr0qoaek.jpg
     * used : true
     * who : 张涵宇
     */

    private String _id;
    private String createdAt;
    private String desc;
    private String publishedAt;
    private String type;
    private String url;
    private boolean used;
    private String who;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }
}
