package com.bigbang.smartbutler.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigbang.smartbutler.R;
import com.bigbang.smartbutler.utils.L;
import com.bigbang.smartbutler.utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.http.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: litte
 * Created on 2018/8/6  14:59
 * Project Name:Smartbutler
 * Package Name:com.bigbang.smartbutler.ui
 * Description: 归属地的查询
 * Copyright (c) 2018, gwl All Rights Reserved.
 */
public class PhoneActivity extends BaseActivity {
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.iv_operator)
    ImageView ivOperator;
    @BindView(R.id.tv_phone_info)
    TextView tvPhoneInfo;
    @BindView(R.id.btn_1)
    Button btn1;
    @BindView(R.id.btn_2)
    Button btn2;
    @BindView(R.id.btn_3)
    Button btn3;
    @BindView(R.id.btn_del)
    Button btnDel;
    @BindView(R.id.btn_4)
    Button btn4;
    @BindView(R.id.btn_5)
    Button btn5;
    @BindView(R.id.btn_6)
    Button btn6;
    @BindView(R.id.btn_0)
    Button btn0;
    @BindView(R.id.btn_7)
    Button btn7;
    @BindView(R.id.btn_8)
    Button btn8;
    @BindView(R.id.btn_9)
    Button btn9;
    @BindView(R.id.btn_query)
    Button btnQuery;

    private boolean flag = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        initView();
    }

    private void initView() {
        ButterKnife.bind(this);
        // 长摁del清除输入框内的所有内容
        btnDel.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                etPhone.setText("");
                return true;
            }
        });
    }

    @SuppressLint("SetTextI18n")
    @OnClick({R.id.tv_phone_info, R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_del, R.id.btn_4, R.id.btn_5, R.id.btn_6, R.id.btn_0, R.id.btn_7, R.id.btn_8, R.id.btn_9, R.id.btn_query})
    public void onViewClicked(View view) {
        String phone = etPhone.getText().toString().trim();
        switch (view.getId()) {
            case R.id.btn_1:
            case R.id.btn_2:
            case R.id.btn_3:
            case R.id.btn_4:
            case R.id.btn_5:
            case R.id.btn_6:
            case R.id.btn_0:
            case R.id.btn_7:
            case R.id.btn_8:
            case R.id.btn_9:
                if (flag) {
                    flag = false;
                    phone = "";
                    etPhone.setText("");
                }
                etPhone.setText(phone + ((Button) view).getText());
                //移动光标
                etPhone.setSelection(phone.length()+1);
                break;
            // 查询
            case R.id.btn_query:
                String url = "http://apis.juhe.cn/mobile/get?phone="+phone+"&key="+ StaticClass.PHONE_KEY;
                RxVolley.get(url, new HttpCallback() {
                    @Override
                    public void onSuccess(String t) {
                        parserJson(t);
                    }
                    @Override
                    public void onFailure(VolleyError error) {
                        L.i("归属地查询请求失败："+error);
                    }
                });
                flag = true;
                break;
            case R.id.btn_del:
                if (!TextUtils.isEmpty("") && phone.length() > 0) {
                    // 每点击一次清除一个
                    etPhone.setText(phone.substring(0, phone.length() - 1));
                    etPhone.setSelection(phone.length()-1);
                }
                break;
                default:break;
        }
    }

    @SuppressLint("SetTextI18n")
    private void parserJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            String resultcode = jsonObject.getString("resultcode");
            if (resultcode.equals("200")) {
                JSONObject result = jsonObject.getJSONObject("result");
                String province = result.getString("province");
                String city = result.getString("city");
                String areacode = result.getString("areacode");
                String zip = result.getString("zip");
                String company = result.getString("company");
                String card = result.getString("card");
                tvPhoneInfo.setText("归属地：" + province + "," + city + "\n" +
                        "区号：" + areacode + "\n" +
                        "邮编：" + zip + "\n" +
                        "运营商：" + company + "\n" +
                        "类型：" + card);
                if (company.equals("移动")) {
                    ivOperator.setBackgroundResource(R.drawable.china_mobile);
                }
                if (company.equals("联通")) {
                    ivOperator.setBackgroundResource(R.drawable.china_unicom);
                }
                if (company.equals("电信")) {
                    ivOperator.setBackgroundResource(R.drawable.china_telecom);
                }
            } else {
                Toast.makeText(PhoneActivity.this, "归属地查询失败："+
                        resultcode+":"+jsonObject.getString("reason"), Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
