package com.example.jing.kapep.Helper;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.jing.kapep.Application.KapApplication;

/**
 * Created by jing on 2017/6/7.
 */
// Glide 帮助类
public class KapGlideHelper {
    /**
     * 用Application context 防止出现
     * */
    // 创建gradle
    public static RequestManager CreatedGlide(){
       return Glide.with(KapApplication.getContext());
    }
    // 取消gradle请求
    public static void PaauseRequests(){
        Glide.with(KapApplication.getContext()).pauseRequests();
    }
}
