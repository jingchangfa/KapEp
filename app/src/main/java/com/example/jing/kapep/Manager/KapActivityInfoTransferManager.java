package com.example.jing.kapep.Manager;

/**
 * Created by jing on 2017/6/20.
 */

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 页面间消息传递
 * 采用匿名累，超微量级别的，只能用于两页面间交互
 * */
public class KapActivityInfoTransferManager {
    public interface InfoTransferModelInterface<T>{
        void changeUIByModel(T model);
    }
    private static KapActivityInfoTransferManager share = new KapActivityInfoTransferManager();
    private KapActivityInfoTransferManager(){}//单利模式
    private Map<String,InfoTransferModelInterface> hashMap = new HashMap<>();
    /**
     * 这个太弱了～（想仿一下iOS的代理模式）
     * 对外公开的方法
     * BindChangeModel 绑定一个回调
     * ChangeByModel 回调
     * UnBind 取消注册
     **/
    // 绑定 被回调的页面
    public static void BindChangeModel(Context context, InfoTransferModelInterface changeModel){
        if (changeModel == null) return;
        String key = keyByContext(context);
        share.hashMap.put(key,changeModel);
    }
    // 触发回调的页面
    public static<T> void PostChangeByModel(T model,Class activityClass){
        if (activityClass == null) return;
        String key = keyByContext(activityClass);
        InfoTransferModelInterface infoTransferModelInterface = share.hashMap.get(key);
        try {
            infoTransferModelInterface.changeUIByModel(model);
        }catch (Exception e){
            // 野指针
//            UnBindChangeModel();
        }
    }
    // UnBind 取消注册,不取消static 持有这changeModel 会导致野指针
    public static void UnBindChangeModel(Context context){
        if (context == null) return;
        String key = keyByContext(context);

    }
    // 辅助方法，获取key
    static String keyByContext(Context context){
        return context.getClass().toString();
    }
    static String keyByContext(Class activityClass){
        return activityClass.toString();
    }
}
