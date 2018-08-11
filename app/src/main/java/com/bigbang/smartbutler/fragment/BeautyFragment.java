package com.bigbang.smartbutler.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.bigbang.smartbutler.R;
import com.bigbang.smartbutler.adapter.BeautyAdapter;
import com.bigbang.smartbutler.entity.Beauty;
import com.bigbang.smartbutler.utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BeautyFragment extends Fragment {

    private GridView mGridView;
    private List<Beauty> mBeautyList = new ArrayList<>();
    private Beauty mBeauty;
    private BeautyAdapter mAdapter;

    public BeautyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_beauty, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mGridView = view.findViewById(R.id.gridView);
        RxVolley.get(StaticClass.BEAUTY, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                parserJson(t);
            }
        });
    }

    private void parserJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONArray results = jsonObject.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject object = (JSONObject) results.get(i);
                String publishedAt = object.getString("publishedAt");
                String url = object.getString("url");
                mBeauty = new Beauty();
                mBeauty.setPublishedAt(publishedAt);
                mBeauty.setUrl(url);
                mBeautyList.add(mBeauty);
            }
            mAdapter = new BeautyAdapter(getActivity(), mBeautyList);
            mGridView.setAdapter(mAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
