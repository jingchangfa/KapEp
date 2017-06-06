package com.example.jing.kapep.Helper;

import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * Created by jing on 2017/6/5.
 */
// 这个观察者是为了解决 直接view.getWidth 获取为0的问题
public class KapViewGetWidthAndHeightHelper {
    public static void GetViewSizeMethod(final View theView,final View.OnClickListener listener){
        theView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    theView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    theView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
                listener.onClick(theView);
            }
        });
    }
}
