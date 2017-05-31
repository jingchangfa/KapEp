package com.example.jing.kapep.View;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.jing.kapep.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jing on 17/5/27.
 */

public class KapAddFriendButton extends FrameLayout {
    public static final int addButton_type_add = 0; // 可添加
    public static final int addButton_type_isFriends = 1; // 已经是朋友
    public static final int addButton_type_isAreadyApplied = 2; //已申请
    // 添加好友的button 三种状态，可点击，以添加，已成为好友
    private int buttonType = addButton_type_add;
    private OnClickListener onClickListener = null;
    @BindView(R.id.add_button)
    Button addButton;
    public void setButtonType(int buttonType) {
        this.buttonType = buttonType;
        if (buttonType == addButton_type_add){
            addButton.setBackgroundResource(R.mipmap.iteam_add);
            return;
        }
        String text = "已添加";
        if (buttonType == addButton_type_isAreadyApplied) text = "已申请";
        addButton.setText(text);
        addButton.setBackgroundResource(0);
    }
    @Override
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public KapAddFriendButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.view_addfriend_button,this);
        ButterKnife.bind(this,view);
        addButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (buttonType != addButton_type_add) return;
                // 回调
                onClickListener.onClick(view);
                // button 状态变化
                setButtonType(addButton_type_isAreadyApplied);
            }
        });
    }
}
