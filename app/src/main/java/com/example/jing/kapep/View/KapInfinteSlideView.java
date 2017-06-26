package com.example.jing.kapep.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.jing.kapep.R;

/**
 * Created by jing on 2017/6/26.
 */
// recycle的封装
public class KapInfinteSlideView extends RecyclerView {
    public interface SlideViewListener {
        void onChange(View v, float offset,boolean left);
    }

    public static final String SLIDE_MODE_HORIZONTAL = "horizontal";//horizontal
    public static final String SLIDE_MODE_VERTICAL = "vertical";//vertical
    private String view_orientation = SLIDE_MODE_HORIZONTAL;
    int center_x;
    int center_y;
    int width;
    int height;

    public KapInfinteSlideView(Context context) {
        super(context);
    }

    public KapInfinteSlideView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public KapInfinteSlideView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    public void init(AttributeSet attrs, int defStyle) {
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.InfiniteSlideView, defStyle, 0);
        String s = a.getString(R.styleable.InfiniteSlideView_view_orientation);
        if (SLIDE_MODE_VERTICAL.equals(s)) {
            view_orientation = s;
        } else {
            view_orientation = SLIDE_MODE_HORIZONTAL;
        }

    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        scrollerChange(dx, dy);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = getWidth();
        height = getHeight();
        center_x = (getLeft() + getRight());
        center_y = (getTop() + getBottom());
        log("width==" + width);
        log("height==" + height);
        log("center_x==" + center_x);
        log("center_y==" + center_y);
    }

    private void scrollerChange(int dx, int dy) {
        for (int i = 0; i < getChildCount(); i++) {
            View v = getChildAt(i);
            float dis = 0;
            float f = 0;
            boolean isLeft = true;
            switch (view_orientation) {
                case SLIDE_MODE_HORIZONTAL:
                    dis = Math.abs((v.getLeft() + v.getRight()) - center_x);
                    f = dis / width;
                    isLeft = (v.getLeft() + v.getRight() - center_x) < 0;
                    break;
                case SLIDE_MODE_VERTICAL:
                    dis = Math.abs((v.getTop() + v.getBottom()) - center_y);
                    f = dis / height;
                    isLeft = (v.getLeft() + v.getRight() - center_x) > 0;
                    break;
            }
            if (slideViewListener == null) return;
            slideViewListener.onChange(v, f, isLeft);
        }
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        switch (state) {
            case SCROLL_STATE_IDLE:
                int neardis = 0;
                int needgo = 0;
                switch (view_orientation) {
                    case SLIDE_MODE_HORIZONTAL:
                        neardis = width;
                        needgo = 0;
                        for (int i = 0; i < getChildCount(); i++) {
                            View v = getChildAt(i);
                            int dis = Math.abs((v.getLeft() + v.getRight()) - center_x);
                            if (neardis > dis) {
                                neardis = dis;
                                needgo = ((v.getLeft() + v.getRight()) - center_x) / 2;
                            }
                        }
                        smoothScrollBy(needgo, 0);
                        break;
                    case SLIDE_MODE_VERTICAL:
                        neardis = height;
                        needgo = 0;
                        for (int i = 0; i < getChildCount(); i++) {
                            View v = getChildAt(i);
                            int dis = Math.abs((v.getTop() + v.getBottom()) - center_y);
                            if (neardis > dis) {
                                neardis = dis;
                                needgo = ((v.getTop() + v.getBottom()) - center_y) / 2;
                            }
                        }
                        smoothScrollBy(0, needgo);
                        break;
                }
                break;
        }
    }


    private void log(String s) {
        Log.d("InfiniteSlideView", s);
    }

    private SlideViewListener slideViewListener;

    public void setSlideViewListener(SlideViewListener slideViewListener) {
        this.slideViewListener = slideViewListener;
    }

    public void setView_orientation(String view_orientation) {
        this.view_orientation = view_orientation;
    }
}
