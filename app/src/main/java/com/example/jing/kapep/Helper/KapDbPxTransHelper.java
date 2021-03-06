package com.example.jing.kapep.Helper;

import android.content.Context;

/**
 * Created by jing on 2017/7/3.
 * db ps 转化类
 */

public class KapDbPxTransHelper {
    private static float scale;
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int DbToPx(Context context, float dpValue) {
        if (scale == 0) {
            scale = context.getResources().getDisplayMetrics().density;
        }
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int PxToDb(Context context, float pxValue) {
        if (scale == 0) {
            scale = context.getResources().getDisplayMetrics().density;
        }
        return (int) (pxValue / scale + 0.5f);
    }
}
