package com.example.jing.kapep.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.jing.kapep.Helper.AttributeSetingHelper;
import com.example.jing.kapep.Helper.KapViewGetWidthAndHeightHelper;
import com.example.jing.kapep.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jing on 2017/6/5.
 */
// 圆点view
public class KapImageRedDotsView extends FrameLayout{
    boolean showRedView = false;
    @BindView(R.id.reddots_image)
    ImageView imageView;
    @BindView(R.id.reddots_redview)
    View view;
    public KapImageRedDotsView(@NonNull Context context) {
        super(context);
    }
    public KapImageRedDotsView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View v = LayoutInflater.from(context).inflate(R.layout.view_reddots,this);
        ButterKnife.bind(this,v);
        // 设置view的圆角
        // 这个观察者是为了解决 直接view.getWidth 获取为0的问题
        KapViewGetWidthAndHeightHelper.GetViewSizeMethod(view, new OnClickListener() {
            @Override
            public void onClick(View view) {
                // 这个观察者是为了解决 直接view.getWidth 获取为0的问题
                float radius = view.getWidth()/2;
                GradientDrawable gd = new GradientDrawable();//创建drawable
                gd.setColor(getResources().getColor(R.color.red));
                gd.setCornerRadius(radius);
                view.setBackgroundDrawable(gd);
            }
        });
        if (attrs != null){
            TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.KapImageRedDotsView);
            AttributeSetingHelper.setImageResource(imageView,a,R.styleable.KapImageRedDotsView_imageview_image);
        }
    }

    // set
    public void setShowRedView(boolean showRedView) {
        this.showRedView = showRedView;
        if (showRedView){
            view.setVisibility(View.VISIBLE);
        }else {
            view.setVisibility(View.INVISIBLE);
        }
    }

    //get
    public ImageView getImageView() {
        return imageView;
    }

}
