package com.example.jing.kapep.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
 * Created by jing on 2017/6/2.
 */
// 消息页面 button的样式
    // 同意
    // 拒绝
    // 查看
public class KapPendingButton extends FrameLayout{
    public KapPendingButton(@NonNull Context context) {
        super(context);
    }
    @BindView(R.id.pending_button)
    Button button;
    public KapPendingButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View v = LayoutInflater.from(context).inflate(R.layout.view_pending_button,this);
        ButterKnife.bind(this);
        // 背景色
        if (attrs == null) return;
        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.KapPendingButton);
        AttributeSetingHelper.setText(button,a,R.styleable.KapPendingButton_pendingButton_button_text);
        AttributeSetingHelper.setBackgroundColor(context,button,a,R.styleable.KapPendingButton_pendingButton_backcolor);
        a.recycle();
    }

    public Button getButton() {
        return button;
    }
}
