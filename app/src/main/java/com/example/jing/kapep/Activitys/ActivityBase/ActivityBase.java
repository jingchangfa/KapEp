package com.example.jing.kapep.Activitys.ActivityBase;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jing.kapep.Application.KapApplication;
import com.example.jing.kapep.Helper.KapGlideHelper;
import com.example.jing.kapep.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jing on 17/5/9.
 */

abstract public class ActivityBase extends AppCompatActivity {
    // 注意默认说有的 ActivityBase 都有 nav_custom_titlebar 这个布局，否则会运行失败，提示找不到控件
    @BindView(R.id.bar_button_left) protected Button leftButton;
    @BindView(R.id.bar_button_title) protected TextView titleTextView;
    @BindView(R.id.bar_button_right) protected Button rightButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewLayoutID());
        ButterKnife.bind(this);
        // 下面是透明状态栏
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//        // 导航栏设置
        setingShowTitle();
        setingButtonAction();
        // 子类自定义实现
        setController();
        // 背景颜色的设置在androidmanifest.xml application的appthream的style文件里面
        getView();
        getModel();
    }

    // 设置导航栏标题
    private void setingShowTitle(){
        String title = navShowTitle();
        if (title != null) titleTextView.setText(title);
    }
    abstract protected String navShowTitle();
    // 设置导航栏按钮点击事件
    private void setingButtonAction(){
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                leftButtonAction();
            }
        });
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rightButtonAction();
            }
        });
    }
    protected void leftButtonAction(){
        KapApplication.backActivityChangeAction();
    }
    protected void rightButtonAction(){
    }
    // 子类实现 返回布局ID
    abstract protected int getContentViewLayoutID();
    abstract protected void setController();
    abstract protected void getView ();
    protected void getModel (){
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("页面销毁",this.getClass().toString()+" delloc");
    }

    @Override
    public void finish() {
        // 注意～  不要直接调用 finish 要通过application调用
        super.finish();
    }
    // 重写返回按钮,使其归入自己的栈管理
    @Override
    public void onBackPressed() {
        KapApplication.backActivityChangeAction();
    }
}
