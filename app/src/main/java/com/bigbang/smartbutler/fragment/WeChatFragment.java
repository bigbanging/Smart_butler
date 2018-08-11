package com.bigbang.smartbutler.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.bigbang.smartbutler.R;
import com.bigbang.smartbutler.adapter.ArticleAdapter;
import com.bigbang.smartbutler.adapter.ChatAdapter;
import com.bigbang.smartbutler.entity.Article;
import com.bigbang.smartbutler.ui.ArticleActivity;
import com.bigbang.smartbutler.utils.L;
import com.bigbang.smartbutler.utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
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
public class WeChatFragment extends Fragment {


    @BindView(R.id.WeChatListView)
    ListView WeChatListView;
    Unbinder unbinder;
    private List<Article> mArticles = new ArrayList<>();
    private ArticleAdapter mAdapter;
    //存储title
    private List<String> titles = new ArrayList<>();
    //存储链接url
    private List<String> urls = new ArrayList<>();

    public WeChatFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_we_chat, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
        String url = "http://v.juhe.cn/weixin/query?key=" + StaticClass.ARTICLE_KEY + "&ps=50";
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                L.i("微信精选文章数据请求" + t);
                parserJson(t);
            }
        });
        WeChatListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),ArticleActivity.class);
                intent.putExtra("title", titles.get(position));
                intent.putExtra("url", urls.get(position));
                startActivity(intent);
            }
        });
    }

    private void parserJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            String reason = jsonObject.getString("reason");
            if (reason.equals("请求成功")) {
                JSONObject result = jsonObject.getJSONObject("result");
                JSONArray jsonArray = result.getJSONArray("list");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject json = (JSONObject) jsonArray.get(i);
                    String title = json.getString("title");
                    String source = json.getString("source");
                    String firstImg = json.getString("firstImg");
                    String url = json.getString("url");
                    Article mArticle = new Article();
                    mArticle.setTitle(title);
                    mArticle.setSource(source);
                    mArticle.setFirstImg(firstImg);
                    mArticle.setUrl(url);
                    mArticles.add(mArticle);

                    titles.add(title);
                    urls.add(url);
                }
                mAdapter = new ArticleAdapter(getActivity(), mArticles);
                WeChatListView.setAdapter(mAdapter);
            } else {
                Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT).show();
                L.i("微信精选文章请求失败,error_code:"+jsonObject.getString("error_code")+"reason:"+reason);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
