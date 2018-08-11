package com.bigbang.smartbutler.entity;

import cn.bmob.v3.BmobUser;

/**
 * Author: litte
 * Created on 2018/8/4  12:21
 * Project Name:Smartbutler
 * Package Name:com.bigbang.smartbutler.entity
 * Description: 用户实体类
 * Copyright (c) 2018, gwl All Rights Reserved.
 */
public class MyUser extends BmobUser {
    private int age;
    private String info;
    private boolean gender;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }
}
