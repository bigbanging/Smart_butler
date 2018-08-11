package com.bigbang.smartbutler.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.bigbang.smartbutler.R;
import com.bigbang.smartbutler.adapter.LogisticAdapter;
import com.bigbang.smartbutler.entity.LogisticInformation;
import com.bigbang.smartbutler.utils.L;
import com.bigbang.smartbutler.utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.http.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: litte
 * Created on 2018/8/6  11:01
 * Project Name:Smartbutler
 * Package Name:com.bigbang.smartbutler.ui
 * Description: 物流查询
 * Copyright (c) 2018, gwl All Rights Reserved.
 */
public class LogisticActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.et_company)
    EditText etCompany;
    @BindView(R.id.et_number)
    EditText etNumber;
    @BindView(R.id.btn_inquire)
    Button btnInquire;
    @BindView(R.id.listView)
    ListView listView;

    private List<LogisticInformation> data = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logistic);
        initView();
    }

    private void initView() {
        ButterKnife.bind(this);
        btnInquire.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_inquire:
                String company = etCompany.getText().toString();
                String number = etNumber.getText().toString().trim();
                if (!TextUtils.isEmpty(company) && !TextUtils.isEmpty(number)) {
                    //请求示例：http://v.juhe.cn/exp/index?key=key&com=sf&no=575677355677
                    String url = "http://v.juhe.cn/exp/index?key="+ StaticClass.LOGISTIC+"&com="+company+"&no="+number;
                    L.i("请求物流信息的url："+url);
                    RxVolley.get(url, new HttpCallback() {
                        @Override
                        public void onSuccess(String t) {
                            L.i("请求到的物流信息："+t);
                            //解析JSON数据
                            parseJson(t);
                        }
                        @Override
                        public void onFailure(VolleyError error) {
                            L.i("请求物流信息失败："+error);
                        }
                    });
                } else {
                    Toast.makeText(this, "请填写完整信息", Toast.LENGTH_SHORT).show();
                }
                break;
                default:break;
        }
    }

    //解析数据
    private void parseJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            String resultcode = jsonObject.getString("resultcode");
            if (resultcode.equals("200")) {
                JSONObject result = jsonObject.getJSONObject("result");
                JSONArray jsonArray = result.getJSONArray("list");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonResult = (JSONObject) jsonArray.get(i);
                    String datetime = jsonResult.getString("datetime");
                    L.i("dateTime:"+datetime);
                    String remark = jsonResult.getString("remark");
                    L.i("remark:"+remark);
                    String zone = jsonResult.getString("zone");
                    L.i("zone:"+zone);
                    LogisticInformation information = new LogisticInformation();
                    information.setDatetime(datetime);
                    information.setRemark(remark);
                    information.setZone(zone);
                    data.add(information);
                }
                Collections.reverse(data);
                LogisticAdapter adapter = new LogisticAdapter(LogisticActivity.this, data);
                listView.setAdapter(adapter);
            } else {
                Toast.makeText(this, "resultCode:"+resultcode+":"+jsonObject.getString("reason"), Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
