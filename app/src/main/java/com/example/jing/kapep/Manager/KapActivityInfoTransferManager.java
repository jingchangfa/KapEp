package com.example.jing.kapep.Manager;

/**
 * Created by jing on 2017/6/20.
 */

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

/**
 * 页面间消息传递
 * 采用匿名累，超微量级别的，用于页面间回调更新
 * 实时回调，startActivityForResult是等待上一个页面finish才会调用
 * */
public class KapActivityInfoTransferManager {
    public interface InfoTransferModelInterface<T>{
        void changeUIByModel(T model);
    }
    private static KapActivityInfoTransferManager share = new KapActivityInfoTransferManager();
    private KapActivityInfoTransferManager(){}//单利模式
    private Map<String,InfoTransferModelInterface> hashMap = new HashMap<>();
    /**
     * BindChangeModel 上级页面注册回调
     * PostChangeByModel 下级页面发起回调
     * */
    public static void BindChangeModel(Context context, InfoTransferModelInterface changeModel){
        String key = keyByContext(context);
        BindAction(key,changeModel);
    }
    public static void BindChangeModel(String activityName,InfoTransferModelInterface changeModel){
        BindAction(activityName,changeModel);
    }
    public static<T> void PostChangeByModel(T model,Class activityClass){
        String key = keyByContext(activityClass);
        PostAction(model,key);
    }
    public static<T> void PostChangeByModel(T model,String activityName) {
        PostAction(model,activityName);
    }
    /**
     * BindAction 上级页面注册回调
     * PostAction 下级页面发起回调
     * */
    private static void BindAction(String key,InfoTransferModelInterface changeModel){
        if (changeModel == null) return;
        if (key == null) return;
        share.hashMap.put(key,changeModel);
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
    /**
     * keyByContext 辅助方法，获取key
     */
    private static String keyByContext(Context context){
        if (context == null) return null;
        return context.getClass().toString();
    }
    private static String keyByContext(Class activityClass){
        if (activityClass == null) return null;
        return activityClass.toString();
    }
    //    // UnBind 取消注册,不取消static 持有这changeModel 会导致野指针
//    // 这个我解决了 加了个try 所以此方法没必要了
//    public static void UnBindChangeModel(Context context){
//        if (context == null) return;
//        String key = keyByContext(context);
//
//    }
}
