package com.example.jing.kapep.View;

import android.content.Context;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by jing on 2017/7/11.
 * TODO 实现等比拉伸，居上显示的imageview
 */

public class KapTopCropImageView extends ImageView {
    public KapTopCropImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setScaleType(ScaleType.MATRIX);
    }

    public KapTopCropImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setScaleType(ScaleType.MATRIX);
    }

    public KapTopCropImageView(Context context) {
        super(context);
        setScaleType(ScaleType.MATRIX);
    }

    @Override
    protected boolean setFrame(int l, int t, int r, int b)
    {
        if (getDrawable() == null) {
            return super.setFrame(l, t, r, b);
        }
        Matrix matrix = getImageMatrix();
        float scaleWidth = getWidth()/(float)getDrawable().getIntrinsicWidth();
        float scaleHeight = getHeight()/(float)getDrawable().getIntrinsicHeight();
        float scaleFactor = (scaleWidth > scaleHeight) ? scaleWidth : scaleHeight;
        matrix.setScale(scaleFactor, scaleFactor, 0, 0);
        if (scaleFactor == scaleHeight) {
            float tanslateX = ((getDrawable().getIntrinsicWidth() * scaleFactor) - getWidth()) / 2;
            matrix.postTranslate(-tanslateX, 0);
        }
        setImageMatrix(matrix);
        return super.setFrame(l, t, r, b);
    }
}
