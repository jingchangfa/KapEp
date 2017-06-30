package com.example.jing.kapep.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.jing.kapep.Helper.AttributeSetingHelper;
import com.example.jing.kapep.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jing on 2017/6/29.
 */

public class ViewSexButton extends LinearLayout {
    private boolean selected = false;
    private int normalImage = 0;
    private int selectedImage = 0;
    private int normalStrokeColor = 0;
    private int selectedStrokeColor = 0;
    private int normalFillColor = 0;
    private int selectedFillColor = 0;

    private Context mContext = null;
    @BindView(R.id.sexbutton_backview)
    LinearLayout backView;
    @BindView(R.id.sexbutton_image)
    ImageView imageView;
    public ViewSexButton(Context context) {
        super(context);
    }

    public ViewSexButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.view_sex_button,this);
        ButterKnife.bind(this,view);
        if (attrs == null) return;
        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.ViewSexButton);
        mContext = context;
        normalImage = AttributeSetingHelper.getReResourceId(a,R.styleable.ViewSexButton_sexButton_imgae_n);
        selectedImage = AttributeSetingHelper.getReResourceId(a,R.styleable.ViewSexButton_sexButton_imgae_s);
        normalStrokeColor = AttributeSetingHelper.getReResourceId(a,R.styleable.ViewSexButton_sexButton_stroke_color_n);
        selectedStrokeColor = AttributeSetingHelper.getReResourceId(a,R.styleable.ViewSexButton_sexButton_stroke_color_s);
        normalFillColor = AttributeSetingHelper.getReResourceId(a,R.styleable.ViewSexButton_sexButton_fill_color_n);
        selectedFillColor = AttributeSetingHelper.getReResourceId(a,R.styleable.ViewSexButton_sexButton_fill_color_s);
        a.recycle();
        AttributeSetingHelper.setImageResource(imageView,normalImage);
        AttributeSetingHelper.setShapeBackgroundStrokeColor(mContext,backView,normalStrokeColor);
        AttributeSetingHelper.setShapeBackgroundFillColor(mContext,backView,normalFillColor);
    }

    @Override
    public boolean isSelected() {
        return selected;
    }

    @Override
    public void setSelected(boolean selected) {
        this.selected = selected;
        // 更新UI
        if (selected){
            AttributeSetingHelper.setImageResource(imageView,selectedImage);
            AttributeSetingHelper.setShapeBackgroundStrokeColor(mContext,backView,selectedStrokeColor);
            AttributeSetingHelper.setShapeBackgroundFillColor(mContext,backView,selectedFillColor);
            return;
        }
        AttributeSetingHelper.setImageResource(imageView,normalImage);
        AttributeSetingHelper.setShapeBackgroundStrokeColor(mContext,backView,normalStrokeColor);
        AttributeSetingHelper.setShapeBackgroundFillColor(mContext,backView,normalFillColor);
    }
}
