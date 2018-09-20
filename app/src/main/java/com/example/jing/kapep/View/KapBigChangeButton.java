package com.example.jing.kapep.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.jing.kapep.Helper.AttributeSetingHelper;
import com.example.jing.kapep.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jing on 17/5/10.
 */

public class KapBigChangeButton extends FrameLayout {
    public static final int KapBigChangeButton_normal = 0;
    public static final int KapBigChangeButton_selected = 1;
    public static final int KapBigChangeButton_disable = 2;

    private int currentStatus = KapBigChangeButton_normal;

    private String normalText = null;
    private String selectText = null;
    private String disableText = null;

    private int normalBackColor = 0;
    private int selectBackColor = 0;
    private int disableBackColor = 0;

    private int normalTextColor = 0;
    private int selectTextColor = 0;
    private int disableTextColor = 0;

    private Context mContext = null;
    @BindView(R.id.big_change_button) Button button;
    public KapBigChangeButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.big_change_button,this);
        ButterKnife.bind(this,view);
        if (attrs == null) return;

        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.KapBigChangeButton);
        mContext = context;

        normalText = AttributeSetingHelper.getTextString(a,R.styleable.KapBigChangeButton_custom_text_normal);
        selectText = AttributeSetingHelper.getTextString(a,R.styleable.KapBigChangeButton_custom_text_select);
        disableText = AttributeSetingHelper.getTextString(a,R.styleable.KapBigChangeButton_custom_text_disable);

        normalBackColor = AttributeSetingHelper.getReResourceId(a,R.styleable.KapBigChangeButton_custom_backColor_normal);
        selectBackColor = AttributeSetingHelper.getReResourceId(a,R.styleable.KapBigChangeButton_custom_backColor_select);
        disableBackColor = AttributeSetingHelper.getReResourceId(a,R.styleable.KapBigChangeButton_custom_backColor_disable);

        normalTextColor = AttributeSetingHelper.getReResourceId(a,R.styleable.KapBigChangeButton_custom_textColor_normal);
        selectTextColor = AttributeSetingHelper.getReResourceId(a,R.styleable.KapBigChangeButton_custom_textColor_select);
        disableTextColor = AttributeSetingHelper.getReResourceId(a,R.styleable.KapBigChangeButton_custom_textColor_disable);
        a.recycle();
        setCurrentStatus(KapBigChangeButton_normal);
    }
    public Button getButton() {
        return button;
    }


    public int getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(int currentStatus) {
        this.currentStatus = currentStatus;
        if (currentStatus == KapBigChangeButton_normal){
            AttributeSetingHelper.setText(button,normalText);
            AttributeSetingHelper.setShapeBackgroundFillColor(mContext,button,normalBackColor);
            AttributeSetingHelper.setTextColor(mContext,button,normalTextColor);
            setEnabled(true);
            return;
        }
        if (currentStatus == KapBigChangeButton_selected){
            AttributeSetingHelper.setText(button,selectText);
            AttributeSetingHelper.setShapeBackgroundFillColor(mContext,button,selectBackColor);
            AttributeSetingHelper.setTextColor(mContext,button,selectTextColor);
            setEnabled(true);
            return;
        }
        if (currentStatus == KapBigChangeButton_disable){
            AttributeSetingHelper.setText(button,disableText);
            AttributeSetingHelper.setShapeBackgroundFillColor(mContext,button,disableBackColor);
            AttributeSetingHelper.setTextColor(mContext,button,disableTextColor);
            setEnabled(false);
            return;
        }
    }
}
