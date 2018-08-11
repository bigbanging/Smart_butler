package com.bigbang.smartbutler.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.bigbang.smartbutler.R;
import com.bigbang.smartbutler.adapter.ChatAdapter;
import com.bigbang.smartbutler.entity.ChatData;
import com.bigbang.smartbutler.utils.L;
import com.bigbang.smartbutler.utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ButlerFragment extends Fragment implements View.OnClickListener {


    @BindView(R.id.chat_listView)
    ListView chatListView;
    @BindView(R.id.et_message)
    EditText etMessage;
    @BindView(R.id.btn_send_msg)
    Button btnSendMsg;
    Unbinder unbinder;

    ChatAdapter mAdapter = null;
    private List<ChatData> mList = new ArrayList<>();

    public ButlerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_butler, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
        btnSendMsg.setOnClickListener(this);
        //填充数据
        mAdapter = new ChatAdapter(getActivity(), mList);
        chatListView.setAdapter(mAdapter);
        addLeftContent("图灵机器人为您服务");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send_msg:
                String content = etMessage.getText().toString().trim();
                if (!TextUtils.isEmpty(content)) {
                    //清空文本框
                    etMessage.setText("");
                    addRightContent(content);
                    HttpParams httpParams = new HttpParams();
                    httpParams.put("key", StaticClass.TULING_ROBOT);
                    httpParams.put("info", content);
                    String url = "http://www.tuling123.com/openapi/api";
                    RxVolley.post(url, httpParams, new HttpCallback() {
                        @Override
                        public void onSuccess(String t) {
                            L.i("机器人聊天返回数据:"+t);
                            parserJson(t);
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), "弄啥类", Toast.LENGTH_SHORT).show();
                }
                break;
            default:break;
        }
    }

    private void parserJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            String text = jsonObject.getString("text");
            addLeftContent(text);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void addLeftContent(String leftContent) {
        ChatData chatData = new ChatData();
        chatData.setType(ChatAdapter.TYPE_LEFT);
        chatData.setContent(leftContent);
        mList.add(chatData);
        mAdapter.notifyDataSetChanged();
        chatListView.setSelection(chatListView.getBottom());
    }

    private void addRightContent(String rightContent) {
        ChatData chatData = new ChatData();
        chatData.setType(ChatAdapter.TYPE_RIGHT);
        chatData.setContent(rightContent);
        mList.add(chatData);
        mAdapter.notifyDataSetChanged();
        chatListView.setSelection(chatListView.getBottom());
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
