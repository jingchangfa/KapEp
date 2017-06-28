package com.example.jing.kapep.Manager;

/**
 * Created by jing on 2017/6/20.
 */

import android.content.Context;

import java.util.HashMap;
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
    // 一对一的关系
    public static void BindChangeModel(Context context, InfoTransferModelInterface changeModel){
        String key = keyByContext(context);
        BindAction(key,changeModel);
    }
    // 多对一
    public static void BindChangeModel(String activityName,InfoTransferModelInterface changeModel){
        BindAction(activityName,changeModel);
    }
    private static void BindAction(String key,InfoTransferModelInterface changeModel){
        if (changeModel == null) return;
        if (key == null) return;
        share.hashMap.put(key,changeModel);
    }
    // 一对一的关系
    public static<T> void PostChangeByModel(T model,Class activityClass){
        String key = keyByContext(activityClass);
        PostAction(model,key);
    }
    // 多对一
    public static<T> void PostChangeByModel(T model,String activityName) {
        PostAction(model,activityName);
    }
    private static<T> void PostAction(T model,String key){
        if (key == null) return;
        InfoTransferModelInterface infoTransferModelInterface = share.hashMap.get(key);
        if (infoTransferModelInterface == null) return;// 不存在
        try {
            infoTransferModelInterface.changeUIByModel(model);
        }catch (Exception e){// 野指针
            share.hashMap.remove(infoTransferModelInterface);
        }
    }
//    // UnBind 取消注册,不取消static 持有这changeModel 会导致野指针
//    // 这个我解决了 加了个try 所以此方法没必要了
//    public static void UnBindChangeModel(Context context){
//        if (context == null) return;
//        String key = keyByContext(context);
//
//    }

    // 辅助方法，获取key
    private static String keyByContext(Context context){
        if (context == null) return null;
        return context.getClass().toString();
    }
    private static String keyByContext(Class activityClass){
        if (activityClass == null) return null;
        return activityClass.toString();
    }
}
