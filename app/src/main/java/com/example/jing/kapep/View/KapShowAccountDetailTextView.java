package com.example.jing.kapep.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.jing.kapep.Helper.AttributeSetingHelper;
import com.example.jing.kapep.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jing on 2017/6/6.
 */
// 个人详情页面 来展示 昵称 性别 等的view
public class KapShowAccountDetailTextView extends FrameLayout{
    @BindView(R.id.title_text)
    TextView titleTextView;
    @BindView(R.id.content_text)
    TextView contenTextView;
    public KapShowAccountDetailTextView(@NonNull Context context) {
        super(context);
    }
    public KapShowAccountDetailTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View v = LayoutInflater.from(context).inflate(R.layout.view_showaccountdetail_textstyle_view,this);
        ButterKnife.bind(this,v);
        setBackgroundResource(R.color.Bank_Black_2b2b2b);
        if (attrs == null) return;
        // 设置内容
        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.KapShowAccountDetailTextView);
        AttributeSetingHelper.setText(titleTextView,a,R.styleable.KapShowAccountDetailTextView_AccountDetail_title_text);
        AttributeSetingHelper.setTextColor(context,contenTextView,a,R.styleable.KapShowAccountDetailTextView_AccountDetail_content_textcolor);
        int style = a.getInteger(R.styleable.KapShowAccountDetailTextView_AccountDetail_style,-1);
        int sex = a.getInteger(R.styleable.KapShowAccountDetailTextView_AccountDetail_content_sex,-1);
        a.recycle();
    }

    private String contentText = null;

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        if (contentText == null) contentText = "";
        this.contentText = contentText;
        contenTextView.setText(contentText);
    }
}
