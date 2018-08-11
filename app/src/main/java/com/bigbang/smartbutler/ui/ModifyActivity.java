package com.bigbang.smartbutler.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bigbang.smartbutler.R;
import com.bigbang.smartbutler.entity.MyUser;
import com.bigbang.smartbutler.utils.L;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Author: litte
 * Created on 2018/8/4  9:47
 * Project Name:Smartbutler
 * Package Name:com.bigbang.smartbutler.ui
 * Description: 修改密码页面
 * Copyright (c) 2018, gwl All Rights Reserved.
 */
public class ModifyActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.et_modify_email)
    EditText etModifyEmail;
    @BindView(R.id.btn_send_email)
    Button btnSendEmail;
    @BindView(R.id.et_old_password)
    EditText etOldPassword;
    @BindView(R.id.et_new_password)
    EditText etNewPassword;
    @BindView(R.id.et_confirm_newPassword)
    EditText etConfirmNewPassword;
    @BindView(R.id.btn_modify_password)
    Button btnModifyPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
        intView();
    }

    private void intView() {
        ButterKnife.bind(this);
        btnSendEmail.setOnClickListener(this);
        btnModifyPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send_email:
                // 需要开启Bmob邮箱服务才可以使用
                final String email = etModifyEmail.getText().toString().trim();
                MyUser.resetPasswordByEmail(email, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Toast.makeText(ModifyActivity.this, "重置密码请求成功，请到" + email +
                                    "邮箱进行密码重置操作", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ModifyActivity.this, "重置密码失败，请稍后再试", Toast.LENGTH_SHORT).show();
                            L.i("重置密码失败"+e.getErrorCode()+":"+e.getMessage());
                        } 
                    }
                });
                break;
            case R.id.btn_modify_password:
                // 修改密码
                String oldPassword = etOldPassword.getText().toString().trim();
                String newPassword = etNewPassword.getText().toString().trim();
                String newConfirmPassword = etConfirmNewPassword.getText().toString().trim();
                if (!TextUtils.isEmpty(oldPassword) & !TextUtils.isEmpty(newPassword) & !TextUtils.isEmpty(newConfirmPassword)) {
                    if (newConfirmPassword.equals(newPassword)) {
                        MyUser.updateCurrentUserPassword(oldPassword, newPassword, new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    Toast.makeText(ModifyActivity.this, "密码修改成功，可以使用新密码进行登录啦", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(ModifyActivity.this, "密码修改失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(this, "请确认修改的新密码", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "请填写修改信息", Toast.LENGTH_SHORT).show();
                }
                break;
                default:break;
        }
    }
}
