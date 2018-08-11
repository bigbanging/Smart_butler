package com.bigbang.smartbutler;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bigbang.smartbutler.fragment.BeautyFragment;
import com.bigbang.smartbutler.fragment.ButlerFragment;
import com.bigbang.smartbutler.fragment.UserFragment;
import com.bigbang.smartbutler.fragment.WeChatFragment;
import com.bigbang.smartbutler.ui.SettingActivity;
import com.bigbang.smartbutler.utils.L;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.fab_setting)
    FloatingActionButton fabSetting;
    // TabLayout的标题
    private List<String> mTitle;
    // 存储Fragment
    private List<Fragment> mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        L.w("Test");
        L.i("Test");
        L.e("Test");
        L.d("Test");
        // 去掉阴影
//        getSupportActionBar().setElevation(0);
        // 初始化数据
        initData();
        // 初始化控件
        initView();
        //测试Bugly是否可用
//        CrashReport.testJavaCrash();
    }
    private void initView() {
        ButterKnife.bind(this);
        //首页默认隐藏fabSetting
        fabSetting.setVisibility(View.GONE);
        // fabSetting的点击事件
        fabSetting.setOnClickListener(this);
        // ViewPager的监听事件 处理fabSetting按钮第一页不可见
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    fabSetting.setVisibility(View.GONE);
                } else {
                    fabSetting.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        // 预加载
        viewPager.setOffscreenPageLimit(mFragments.size());
        // 设置adapter
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }
            @Override
            public int getCount() {
                return mFragments.size();
            }
            // 设置标题
            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitle.get(position);
            }
        });
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initData() {
        mTitle = new ArrayList<>();
        mTitle.add(getString(R.string.title_first));
        mTitle.add(getString(R.string.title_second));
        mTitle.add(getString(R.string.title_third));
        mTitle.add(getString(R.string.title_four));

        mFragments = new ArrayList<>();
        mFragments.add(new ButlerFragment());
        mFragments.add(new WeChatFragment());
        mFragments.add(new BeautyFragment());
        mFragments.add(new UserFragment());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_setting:
                startActivity(new Intent(this,SettingActivity.class));
                break;
                default:
                    break;
        }
    }
}
