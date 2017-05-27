package com.example.jing.kapep.Helper;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.jing.kapep.HttpClient.BaseHttp.HttpThread;

/**
 * Created by jing on 17/5/19.
 */
/**
 * 线程管理～
 * */
public class MainThreadHelper {
    private Handler mainHandler = new Handler(Looper.getMainLooper());

    // 单利模式
    private static MainThreadHelper mainThreadHelper = new MainThreadHelper();
    private static MainThreadHelper getStaticHttpThread() {
        return  mainThreadHelper;
    }
    private MainThreadHelper(){}//私有构造函数

    // 对外公开
    public static void runOnUIthread(Runnable runnable){
        if (Looper.myLooper() == Looper.getMainLooper()){
            runnable.run();
            return;
        }
        getStaticHttpThread().mainHandler.post(runnable);
    }
    // 调试
    public static void logCurrentThread(){
        String string = Thread.currentThread().toString();
        Log.d("当前线程",string);
    }
}
