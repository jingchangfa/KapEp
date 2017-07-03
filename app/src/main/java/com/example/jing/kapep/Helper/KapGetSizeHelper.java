package com.example.jing.kapep.Helper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by jing on 2017/6/23.
 * 获取尺寸的类
 */
public class KapGetSizeHelper {
    public static int GetStatusBarHeight(Context context){
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {//根据资源ID获取响应的尺寸值
            return context.getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }
}
