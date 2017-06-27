package com.example.jing.kapep.Activitys.LoginActivity;

import android.content.Context;
import android.content.res.TypedArray;
import android.media.Image;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.jing.kapep.Helper.AttributeSetingHelper;
import com.example.jing.kapep.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jing on 17/5/10.
 */

public class LoginEditTextView extends LinearLayout {
    @BindView(R.id.login_edittext_image) ImageView imageView;
    @BindView(R.id.login_edittext_lineview) View lineView;
    @BindView(R.id.login_edittext_text) EditText editText;
    public LoginEditTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.login_edittext,this);
        ButterKnife.bind(this,view);
        if (attrs == null) return;
        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.LoginEditTextView);
        AttributeSetingHelper.setHint(editText,a,R.styleable.LoginEditTextView_custom_placeholdText);
        AttributeSetingHelper.setText(editText,a,R.styleable.LoginEditTextView_custom_text);
        AttributeSetingHelper.setImageResource(imageView,a,R.styleable.LoginEditTextView_custom_image);
        AttributeSetingHelper.setTextColor(context,editText,a,R.styleable.LoginEditTextView_custom_textColor);
        AttributeSetingHelper.setHintTextColor(context,editText,a,R.styleable.LoginEditTextView_custom_hintColor);
        // 背景色
        AttributeSetingHelper.setBackgroundColor(context,imageView,a,R.styleable.LoginEditTextView_custom_backColor);
        AttributeSetingHelper.setBackgroundColor(context,editText,a,R.styleable.LoginEditTextView_custom_backColor);
        // 线隐藏显示
        lineView.setVisibility(AttributeSetingHelper.getVisibility(a,R.styleable.LoginEditTextView_custom_lineIsShow));
        a.recycle();
    }

    public ImageView getImageView() {
        return imageView;
    }

    public View getLineView() {
        return lineView;
    }

    public EditText getEditText() {
        return editText;
    }
}
