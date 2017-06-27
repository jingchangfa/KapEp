package com.example.jing.kapep.Application;

import android.app.Activity;

import java.util.Stack;

/**
 * Created by jing on 17/5/24.
 */
// 管理 Activity 的 queue
// Application 使用
public class KapApplicationActivitysQueue {
    private KapApplicationActivitysQueue(){}
    private static KapApplicationActivitysQueue queue = new KapApplicationActivitysQueue();
    public static KapApplicationActivitysQueue ShareActivityQueue(){
        return queue;
    }
    private Stack<Activity> activityStack = new Stack<Activity>();
    /**
     * addActivity 添加Activity到堆栈
     * popCurrentActivity 结束当前Activity
     * 只有这俩方法 操作 activityStack 不能手动调用（都是自动添加删除的）
     * currentActivity 获取当前的activity，不做任何操作
     */
    public void addActivity(Activity activity) {
        activityStack.push(activity);
    }
    public  void popCurrentActivity() {
        activityStack.pop();
    }
    public  Activity currentActivity() {
        return activityStack.lastElement();
    }


    /**
     * 辅助方法
     * 下面的这些方法都是，注意防止当前activity结束当前的导致crash
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    /**
     * finishOneActivity 结束指定的Activity
     * activity
     * Class<?> cls  activity的class
     */
    public  void finishOneActivity(Activity activity) {
        if (activity != null) {
            if(!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
    public  void finishOneActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (!activity.getClass().equals(cls)) continue;
            finishOneActivity(activity);
            return;
        }
    }

    /**
     * 结束除指定类名的所有Activity
     */
    public  void finishExcludeActivityAllActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity == null) continue;
            if (activity.getClass().equals(cls)) continue;
            finishOneActivity(activity);
        }
    }
    /**
     * 结束所有Activity
     */
    public  void finishAllActivity() {
        for (Activity activity : activityStack) {
            if (activity == null) continue;
            finishOneActivity(activity);
        }
    }
//    /**
//     * 退出应用程序
//     */
//    public static void AppExit(Context context) {
//        try {
//            finishAllActivity();
//            ActivityManager manager = (ActivityManager) context
//                    .getSystemService(Context.ACTIVITY_SERVICE);
//            manager.killBackgroundProcesses(context.getPackageName());
//            System.exit(0);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
