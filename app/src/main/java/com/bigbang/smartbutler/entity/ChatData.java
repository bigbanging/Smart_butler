package com.bigbang.smartbutler.entity;

/**
 * Author: litte
 * Created on 2018/8/6  17:24
 * Project Name:Smartbutler
 * Package Name:com.bigbang.smartbutler.entity
 * Description: 聊天信息实体类
 * Copyright (c) 2018, gwl All Rights Reserved.
 */
public class ChatData {
    private int type;
    private String content;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
