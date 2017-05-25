package com.example.jing.kapep.Application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.jing.kapep.Activitys.HomePageActivity.KapHomePageActivity;
import com.example.jing.kapep.Activitys.LoginActivity.KapLoginActivity;
import com.example.jing.kapep.UserAccount.KapUserAccount;

/**
 * Created by jing on 17/5/11.
 */

public class KapApplication extends Application{
    private static KapApplication instance;
    public static KapApplication getInstance() {
        return instance;
    }
    private static Context context;
    private static KapUserAccount userAccount;
    private static String codeKeyString;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        instance = this;
        registerActivityLifecycleCallbacks(activityLifecycleCallbacks);
    }
    /**
     * logInActivityChangeAction 切换根视图登录也
     * homeActivityChangeAction 切换根视图主页
     * backActivityChangeAction 返回按钮
     * */
    public void logInActivityChangeAction(){
        Class openClass = KapLoginActivity.class;
        Activity currentActivity = activitysQueue.currentActivity();
        currentActivity.startActivity(new Intent(currentActivity, openClass));
        activitysQueue.finishExcludeActivityAllActivity(openClass);
    }
    public void homeActivityChangeAction(){
        Class openClass = KapHomePageActivity.class;
        Activity currentActivity = activitysQueue.currentActivity();
        currentActivity.startActivity(new Intent(currentActivity, openClass));
        activitysQueue.finishExcludeActivityAllActivity(openClass);
    }
    public void backActivityChangeAction(){
        activitysQueue.finishCurrentActivity();
    }
    /**
     * activity栈管理
     * */
    private KapApplicationActivitysQueue activitysQueue = KapApplicationActivitysQueue.ShareActivityQueue();
    private ActivityLifecycleCallbacks activityLifecycleCallbacks = new ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {
            //创建
            activitysQueue.addActivity(activity);
        }
        @Override
        public void onActivityDestroyed(Activity activity) {
            // 推出
        }

        @Override
        public void onActivityStarted(Activity activity) {
        }

        @Override
        public void onActivityResumed(Activity activity) {
        }

        @Override
        public void onActivityPaused(Activity activity) {
        }

        @Override
        public void onActivityStopped(Activity activity) {
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        }

    };
    // get
    public static Context getContext() {
        return context;
    }

    public static KapUserAccount getUserAccount() {
        if (userAccount == null) userAccount = KapUserAccount.loadActiveUserAccount();
        return userAccount;
    }
    public static void setUserAccount(KapUserAccount userAccount) {
        KapApplication.userAccount = userAccount;
    }

    public static String getCodeKeyString() {
        return codeKeyString;
    }

    public static void setCodeKeyString(String codeKeyString) {
        KapApplication.codeKeyString = codeKeyString;
    }

    public static String getUserToken() {
        if (getUserAccount() == null) return null;
        return getUserAccount().token;
    }
}
