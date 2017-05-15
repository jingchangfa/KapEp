package com.example.jing.kapep.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jing.kapep.Helper.AttributeSetingHelper;
import com.example.jing.kapep.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jing on 17/5/12.
 */

public class KapAlertBankShowView extends ViewGroup {
    private View mHeader,mFooter;
    @BindView(R.id.big_change_button) Button bigChangeButton;
    @BindView(R.id.alertshow_titleview) TextView titleView;
    public KapAlertBankShowView(Context context) {
        super(context);
    }
    public KapAlertBankShowView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mHeader = LayoutInflater.from(context).inflate(R.layout.view_bankalert_headerview,null);
        mFooter = LayoutInflater.from(context).inflate(R.layout.view_bankalert_footerview,null);
        addView(mHeader);
        addView(mFooter);
        ButterKnife.bind(this);
        if (attrs == null) return;
        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.KapAlertBankShowView);
        AttributeSetingHelper.setText(titleView,a,R.styleable.KapAlertBankShowView_title_text);
        AttributeSetingHelper.setText(bigChangeButton,a,R.styleable.KapAlertBankShowView_button_text);
        AttributeSetingHelper.setTextColor(context,bigChangeButton,a,R.styleable.KapAlertBankShowView_button_color);
        int headerIsShow = AttributeSetingHelper.getVisibility(a,R.styleable.KapAlertBankShowView_title_isShow);
        int footerIsShow = AttributeSetingHelper.getVisibility(a,R.styleable.KapAlertBankShowView_button_isShow);
        mHeader.setVisibility(headerIsShow);
        mFooter.setVisibility(footerIsShow);
        a.recycle();
    }
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        // 设置背景
        setBackgroundResource(R.drawable.view_alertview_bg);
        // 添加头尾
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                (LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        mHeader.setLayoutParams(params);
        mFooter.setLayoutParams(params);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // onMeasure，实现测量子View大小以及设定ViewGroup的大小：
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for (int i = 0; i < getChildCount(); i++){
            View child = getChildAt(i);
            if (child.getVisibility() == View.GONE) continue;
            // measureChild 对单个view进行测量
            measureChild(child,widthMeasureSpec,heightMeasureSpec);
        }
        // http://www.jianshu.com/p/c84693096e41  这个链接有自定义布局的相关知识
        int height = getTotleHeight();
        int width = getMaxChildWidth();
        setMeasuredDimension(width, height);
    }

    private int mLayoutContentHeight;
    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        mLayoutContentHeight = 0;
        // 头视图在顶端
        mHeader.layout(0, 0, mHeader.getMeasuredWidth(), mHeader.getMeasuredHeight());
        mLayoutContentHeight += mHeader.getMeasuredHeight();
        // 置位
        for (int j = 0; j < getChildCount(); j++){
            View child = getChildAt(j);
            if (child == mHeader||child == mFooter) continue;
            if (child.getVisibility() == View.GONE) continue;
            child.layout(0, mLayoutContentHeight, child.getMeasuredWidth(), mLayoutContentHeight + child.getMeasuredHeight());
            mLayoutContentHeight += child.getMeasuredHeight();
        }
        // 尾视图在最后
        mFooter.layout(0, mLayoutContentHeight, mFooter.getMeasuredWidth(), mLayoutContentHeight + mFooter.getMeasuredHeight());
        mLayoutContentHeight += mHeader.getMeasuredHeight();
    }
    /***
     * 获取子View中宽度最大的值
     */
    private int getMaxChildWidth() {
        int childCount = getChildCount();
        int maxWidth = 0;
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            if (childView.getVisibility() == View.GONE) continue;
            if (childView.getMeasuredWidth() > maxWidth)
                maxWidth = childView.getMeasuredWidth();
        }
        return maxWidth;
    }
    /***
     * 将所有子View的高度相加
     **/
    private int getTotleHeight() {
        int childCount = getChildCount();
        int height = 0;
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            if (childView.getVisibility() == View.GONE) continue;
            height += childView.getMeasuredHeight();
        }
        return height;
    }
}
