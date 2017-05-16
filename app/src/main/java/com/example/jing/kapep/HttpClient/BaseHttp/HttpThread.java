package com.example.jing.kapep.HttpClient.BaseHttp;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by jing on 17/5/16.
 */

public class HttpThread {
    private Handler mainHandler = new Handler(Looper.getMainLooper());
    // 单利模式
    public static HttpThread getStaticHttpThread() {
        return  httpThread;
    }
    private static HttpThread httpThread = new HttpThread();
    /**
     * 回到主线程
     * 本身在主线程 直接回调
     * 回到主线程
     */
    public void runOnUIthread(Runnable runnable){
        if (Looper.myLooper() == Looper.getMainLooper()){
            runnable.run();
            return;
        }
        mainHandler.post(runnable);
    }
}
