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
     * 添加Activity到堆栈
     */
    public  void addActivity(Activity activity) {
        activityStack.push(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public  Activity currentActivity() {
        return activityStack.lastElement();
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public  void finishCurrentActivity() {
        Activity activity = activityStack.pop();
        activity.finish();
    }

    /**
     * 结束指定的Activity
     */
    public  void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            if(!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public  void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束除指定类名的所有Activity
     */
    public  void finishExcludeActivityAllActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity != null) {
                if (activity.getClass().equals(cls)) continue;
                activity.finish();
            }
        }
        activityStack.clear();
    }
    /**
     * 结束所有Activity
     */
    public  void finishAllActivity() {
        for (Activity activity : activityStack) {
            if (activity != null) {
                activity.finish();
            }
        }
        activityStack.clear();
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
