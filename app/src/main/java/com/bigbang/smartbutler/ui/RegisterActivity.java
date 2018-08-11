package com.bigbang.smartbutler.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bigbang.smartbutler.R;
import com.bigbang.smartbutler.entity.MyUser;
import com.bigbang.smartbutler.utils.L;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Author: litte
 * Created on 2018/8/4  9:43
 * Project Name:Smartbutler
 * Package Name:com.bigbang.smartbutler.ui
 * Description: 注册页面
 * Copyright (c) 2018, gwl All Rights Reserved.
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.et_register_username)
    EditText etRegisterUsername;
    @BindView(R.id.et_register_age)
    EditText etRegisterAge;
    @BindView(R.id.et_register_info)
    EditText etRegisterInfo;
    @BindView(R.id.rb_male)
    RadioButton rbMale;
    @BindView(R.id.rb_female)
    RadioButton rbFemale;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.et_register_password)
    EditText etRegisterPassword;
    @BindView(R.id.et_confirm_password)
    EditText etConfirmPassword;
    @BindView(R.id.et_register_email)
    EditText etRegisterEmail;
    @BindView(R.id.btn_register_register)
    Button btnRegisterRegister;
    private boolean gender = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        ButterKnife.bind(this);
        btnRegisterRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // 获取输入框的内容
        String username = etRegisterUsername.getText().toString().trim();
        String age = etRegisterAge.getText().toString().trim();
        String info = etRegisterInfo.getText().toString().trim();
        String password = etRegisterPassword.getText().toString().trim();
        String confirm_password = etConfirmPassword.getText().toString().trim();
        String email = etRegisterEmail.getText().toString();
        //空值判断
        if (!TextUtils.isEmpty(username) & !TextUtils.isEmpty(age) & !TextUtils.isEmpty(password) &
                !TextUtils.isEmpty(confirm_password)) {
            if (password.equals(confirm_password)) {
                if (TextUtils.isEmpty(info)) {
                    info = "这个人很懒，什么也没留下";
                }
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if (checkedId == R.id.rb_male) {
                            gender = true;
                        } else if (checkedId == R.id.rb_female) {
                            gender = false;
                        }
                    }
                });
                MyUser myUser = new MyUser();
                myUser.setUsername(username);
                myUser.setPassword(password);
                myUser.setAge(Integer.parseInt(age));
                myUser.setInfo(info);
                myUser.setGender(gender);
                if (email.equals("")) {
                    myUser.setEmail("");
                } else {
                    myUser.setEmail(email);
                }
                myUser.signUp(new SaveListener<MyUser>() {
                    @Override
                    public void done(MyUser myUser, BmobException e) {
                        if (e == null) {
                            Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            L.i("注册失败："+ e.getErrorCode()+":"+e.getMessage());
                            Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                Toast.makeText(this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "请填写完整信息", Toast.LENGTH_SHORT).show();
        }
    }
}
