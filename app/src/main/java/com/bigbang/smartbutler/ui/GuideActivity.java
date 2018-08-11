package com.bigbang.smartbutler.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigbang.smartbutler.R;
import com.bigbang.smartbutler.utils.L;
import com.bigbang.smartbutler.utils.UtilTools;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: litte
 * Created on 2018/8/2  22:15
 * Project Name:Smartbutler
 * Package Name:com.bigbang.smartbutler.ui
 * Description: 引导页
 * Copyright (c) 2018, gwl All Rights Reserved.
 */
public class GuideActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.guide_viewPager)
    ViewPager guideViewPager;
    @BindView(R.id.iv_jump)
    ImageView ivJump;
    @BindView(R.id.iv_point1)
    ImageView ivPoint1;
    @BindView(R.id.iv_point2)
    ImageView ivPoint2;
    @BindView(R.id.iv_point3)
    ImageView ivPoint3;
    @BindView(R.id.ll_guide)
    LinearLayout llGuide;
    private List<View> mViews;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initView();
    }
    private void initView() {
        ButterKnife.bind(this);
        showPoint(true,false,false);//不能丢 否在第一页显示不出导航的点
        mViews = new ArrayList<>();
        View view1 = View.inflate(this, R.layout.guide_page_one, null);
        View view2 = View.inflate(this, R.layout.guide_page_two, null);
        View view3 = View.inflate(this, R.layout.guide_page_three, null);

        mViews.add(view1);
        mViews.add(view2);
        mViews.add(view3);

        TextView tv_poetry1 = view1.findViewById(R.id.tv_poetry1);
        UtilTools.fontType(this, tv_poetry1);
        TextView tv_poetry2 = view2.findViewById(R.id.tv_poetry2);
        UtilTools.fontType(this, tv_poetry2);

        Button buttonEnterMain = view3.findViewById(R.id.btn_enter_main);
        buttonEnterMain.setOnClickListener(this);
        ivJump.setOnClickListener(this);

        guideViewPager.setAdapter(new GuideAdapter());
        guideViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                L.i("position: "+position);
                switch (position) {
                    case 0:
                        ivJump.setVisibility(View.VISIBLE);
                        showPoint(true, false, false);
                        llGuide.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        showPoint(false, true, false);
                        ivJump.setVisibility(View.VISIBLE);
                        llGuide.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        llGuide.setVisibility(View.GONE);
                        showPoint(false, false, true);
                        ivJump.setVisibility(View.GONE);
                        break;
                    default:
                        break;
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_enter_main:
            case R.id.iv_jump:
                startActivity(new Intent(GuideActivity.this, LoginActivity.class));
                finish();
                break;
            default:
                break;
        }
    }

    private class GuideAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return mViews.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            container.addView(mViews.get(position));
            return mViews.get(position);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//            super.destroyItem(container, position, object);
            container.removeView(mViews.get(position));
        }
    }

    /*
    显示引导页小圆点
     */
    private void showPoint(boolean point1, boolean point2, boolean point3) {
        if (point1) {
            ivPoint1.setBackgroundResource(R.drawable.point_on);
        } else {
            ivPoint1.setBackgroundResource(R.drawable.point_off);
        }
        if (point2) {
            ivPoint2.setBackgroundResource(R.drawable.point_on);
        } else {
            ivPoint2.setBackgroundResource(R.drawable.point_off);
        }
        if (point3) {
            ivPoint3.setBackgroundResource(R.drawable.point_on);
        } else {
            ivPoint3.setBackgroundResource(R.drawable.point_off);
        }
    }
}
