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
    @BindView(R.id.big_change_button) Button button;
    public KapBigChangeButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.big_change_button,this);
        ButterKnife.bind(this,view);
        if (attrs != null){
            TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.KapBigChangeButton);
            String normalText = a.getString(R.styleable.KapBigChangeButton_custom_text_normal);
            String selectText = a.getString(R.styleable.KapBigChangeButton_custom_text_select);
            String disableText =  a.getString(R.styleable.KapBigChangeButton_custom_text_disable);

            int normalBackColor = a.getResourceId(R.styleable.KapBigChangeButton_custom_backColor_normal,0);
            int selectBackColor = a.getResourceId(R.styleable.KapBigChangeButton_custom_backColor_select,0);
            int disableBackColor = a.getResourceId(R.styleable.KapBigChangeButton_custom_backColor_disable,0);

            int normalTextColor = a.getResourceId(R.styleable.KapBigChangeButton_custom_textColor_normal,0);
            int selectTextColor = a.getResourceId(R.styleable.KapBigChangeButton_custom_textColor_select,0);
            int disableTextColor = a.getResourceId(R.styleable.KapBigChangeButton_custom_textColor_disable,0);

            AttributeSetingHelper.setText(button,a,R.styleable.KapBigChangeButton_custom_text_normal);
            AttributeSetingHelper.setBackgroundColor(context,button,a,R.styleable.KapBigChangeButton_custom_backColor_normal);
            AttributeSetingHelper.setTextColor(context,button,a,R.styleable.KapBigChangeButton_custom_textColor_normal);
            a.recycle();
        }
    }
    public Button getButton() {
        return button;
    }
}
