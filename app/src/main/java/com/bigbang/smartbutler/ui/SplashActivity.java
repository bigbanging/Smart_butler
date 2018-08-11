package com.bigbang.smartbutler.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.bigbang.smartbutler.MainActivity;
import com.bigbang.smartbutler.R;
import com.bigbang.smartbutler.utils.SharedPreferenceUtils;
import com.bigbang.smartbutler.utils.StaticClass;
import com.bigbang.smartbutler.utils.UtilTools;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.tv_splash)
    TextView tvSplash;

    // 判断是否是第一次启动应用 如果是第一次则进入引导页 否则进入主界面
    //这种方式处理容易造成内存泄漏
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case StaticClass.HANDLER_SPLASH:
                    if (isFirst()) {
                        startActivity(new Intent(SplashActivity.this, GuideActivity.class));
                    } else {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    }
                    finish();
                    break;
                    default:break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
        MyHandler myHandler = new MyHandler(this);
//        mHandler.sendEmptyMessageDelayed(StaticClass.HANDLER_SPLASH, 2000);
        myHandler.sendEmptyMessageDelayed(StaticClass.HANDLER_SPLASH, 2000);
    }

    private void initView() {
        ButterKnife.bind(this);
        // 设置字体
        /*Typeface fontType = Typeface.createFromAsset(getAssets(), "font/FONT.TTF");
        tvSplash.setTypeface(fontType);*/
        UtilTools.fontType(this,tvSplash);
    }
    /*
    处理handler的内存泄露问题
     */
    private class MyHandler extends Handler {
        WeakReference<SplashActivity> mWeakReference;
        // 利用构造函数进行初始化
        MyHandler(SplashActivity splashActivity) {
            mWeakReference = new WeakReference<SplashActivity>(splashActivity);
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mWeakReference.get() != null) {
                //更新UI 执行业务逻辑代码
                switch (msg.what) {
                    case StaticClass.HANDLER_SPLASH:
                        if (isFirst()) {
                            startActivity(new Intent(SplashActivity.this, GuideActivity.class));
                        } else {
                            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                        }
                        finish();
                        break;
                    default:
                        break;
                }
            }
        }
    }
    /*
        判断是否是第一次运行程序
         */
    private boolean isFirst() {
        boolean isFirst = SharedPreferenceUtils.getBoolean(this,StaticClass.ISFIRST_OPEN,true);
        if (isFirst) {
            SharedPreferenceUtils.putBoolean(this, StaticClass.ISFIRST_OPEN, false);
            return true;
        } else {
            return false;
//            return true;
        }
    }
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
