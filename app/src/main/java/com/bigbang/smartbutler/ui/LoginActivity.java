package com.bigbang.smartbutler.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bigbang.smartbutler.MainActivity;
import com.bigbang.smartbutler.R;
import com.bigbang.smartbutler.entity.MyUser;
import com.bigbang.smartbutler.utils.L;
import com.bigbang.smartbutler.utils.SharedPreferenceUtils;
import com.bigbang.smartbutler.utils.StaticClass;
import com.bigbang.smartbutler.view.SelfDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Author: litte
 * Created on 2018/8/3  18:07
 * Project Name:Smartbutler
 * Package Name:com.bigbang.smartbutler.ui
 * Description: 登录
 * Copyright (c) 2018, gwl All Rights Reserved.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.et_login_username)
    EditText etLoginUsername;
    @BindView(R.id.et_login_password)
    EditText etLoginPassword;
    @BindView(R.id.tv_find_password)
    TextView tvFindPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.cb_remember)
    CheckBox cbRemember;

    private SelfDialog mSelfDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();
    }
    private void initView() {
        ButterKnife.bind(this);
        tvFindPassword.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        //记住账号密码
        boolean isChecked = SharedPreferenceUtils.getBoolean(this, StaticClass.REMEMBER_PASSWORD, false);
        cbRemember.setChecked(isChecked);
        if (isChecked) {
            //设置密码
            etLoginUsername.setText(SharedPreferenceUtils.getString(this,StaticClass.USERNAME,""));
            etLoginPassword.setText(SharedPreferenceUtils.getString(this,StaticClass.PASSWORD,""));
        }
        mSelfDialog = new SelfDialog(this, 100, 100,
                R.layout.dialog_layout, R.style.theme_dialog, Gravity.CENTER,R.style.dialogTheme);
        // 加载时返回按键无效
        mSelfDialog.setCancelable(false);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register://跳转注册页面
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.btn_login://跳转登录页面
                mSelfDialog.show();
                String username = etLoginUsername.getText().toString().trim();
                String password = etLoginPassword.getText().toString().trim();
                if (!TextUtils.isEmpty(username) || !TextUtils.isEmpty(password)) {
                    MyUser myUser = new MyUser();
                    myUser.setUsername(username);
                    myUser.setPassword(password);
                    myUser.login(new SaveListener<MyUser>() {
                        @Override
                        public void done(MyUser myUser, BmobException e) {
                            mSelfDialog.dismiss();
                            if (e == null) {
                                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                                L.i("登录失败：" + e.getErrorCode() + ":" + e.getMessage());
                            }
                        }
                    });
                } else {
                    Toast.makeText(this, "登录信息不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_find_password://跳转修改密码页面
                startActivity(new Intent(this, ModifyActivity.class));
            default:
                break;

        }
    }
    /**
     * 假如输入用户名和密码之后不进行登录而是直接退出
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferenceUtils.putBoolean(this, StaticClass.REMEMBER_PASSWORD, cbRemember.isChecked());
        // cbRemember 是否被选中
        if (cbRemember.isChecked()) {
            // 选中则记住
            SharedPreferenceUtils.putString(this,StaticClass.USERNAME,etLoginUsername.getText().toString().trim());
            SharedPreferenceUtils.putString(this,StaticClass.PASSWORD,etLoginPassword.getText().toString().trim());
        } else {
            // 否则 删除
            SharedPreferenceUtils.deleteSingle(this, StaticClass.USERNAME);
            SharedPreferenceUtils.deleteSingle(this,StaticClass.PASSWORD);
        }
    }
}
