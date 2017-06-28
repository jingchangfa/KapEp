package com.example.jing.kapep.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jing.kapep.Helper.AttributeSetingHelper;
import com.example.jing.kapep.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jing on 2017/6/28.
 */

public class KapCompleteShowButton extends LinearLayout{
    @BindView(R.id.complete_image)
    ImageView imageView;
    @BindView(R.id.complete_text)
    TextView textView;
    public KapCompleteShowButton(Context context) {
        super(context);
    }

    public KapCompleteShowButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.view_complete_button,this);
        ButterKnife.bind(this,view);
        if (attrs == null) return;
        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.KapCompleteShowButton);
        AttributeSetingHelper.setImageResource(imageView,a,R.styleable.KapCompleteShowButton_completeShowButton_image);
        a.recycle();
    }


    public ImageView getImageView() {
        return imageView;
    }

    public TextView getTextView() {
        return textView;
    }
}
