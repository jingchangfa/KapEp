package com.example.jing.kapep.Helper;

import android.os.Handler;

/**
 * Created by jing on 2017/6/27.
 * 实时搜索的策略
 */

public class KapSearchRunnableHelper implements Runnable {
    private Runnable runnable = null;
    public KapSearchRunnableHelper(Runnable able){
        runnable = able;
    }
    private KapSearchRunnableHelper(){}//私有化
    Handler handler = new Handler();
    public void pushKeyWord(){
        handler.removeCallbacks(this);// 移除请求
        handler.postDelayed(this,500);// 添加请求
    }
    @Override
    public void run() {
        //此处发起Http请求
        runnable.run();
    }
}
