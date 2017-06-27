package com.example.jing.kapep.Application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.example.jing.kapep.Activitys.HomePageActivity.KapHomePageActivity;
import com.example.jing.kapep.Activitys.LoginActivity.KapLoginActivity;
import com.example.jing.kapep.Manager.KapCreameManager;
import com.example.jing.kapep.Manager.KapGsonManager;
import com.example.jing.kapep.Manager.KapSharePreferenceManager;
import com.example.jing.kapep.Model.KapListenerAndFriend.KapModelUserDetail;
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
    private static KapModelUserDetail mineUserDetail;
    private static String codeKeyString;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        instance = this;
        shared = KapSharePreferenceManager.getSharedPreferences();
        registerActivityLifecycleCallbacks(activityLifecycleCallbacks);
    }
    /**
     * currentActivity 获取当前activity
     * logInActivityChangeAction 切换根视图登录
     * homeActivityChangeAction 切换根视图主页
     * */
    public static Activity currentActivity(){
        KapApplicationActivitysQueue activitysQueue = getInstance().activitysQueue;
        return activitysQueue.currentActivity();
    }
    public static void logInActivityChangeAction(){
        Class openClass = KapLoginActivity.class;
        changeRootActivityByClass(openClass);
    }
    public static void homeActivityChangeAction(){
        Class openClass = KapHomePageActivity.class;
        changeRootActivityByClass(openClass);
    }
    private static void changeRootActivityByClass(Class openClass){
        KapApplicationActivitysQueue activitysQueue = getInstance().activitysQueue;
        Activity currentActivity = activitysQueue.currentActivity();
        currentActivity.startActivity(new Intent(currentActivity, openClass));
        activitysQueue.finishExcludeActivityAllActivity(openClass);
    }
    /**
     * activity栈管理
     * */
    private KapApplicationActivitysQueue activitysQueue = KapApplicationActivitysQueue.ShareActivityQueue();
    private ActivityLifecycleCallbacks activityLifecycleCallbacks = new ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {
            activitysQueue.addActivity(activity);//创建
        }
        @Override
        public void onActivityDestroyed(Activity activity) {
            activitysQueue.popCurrentActivity();// 推出
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

    private static final String KEY_ACTIVE_USER_DETAIL = "active_user_mine_detail_";
    private static SharedPreferences shared = null;

    public static KapModelUserDetail getMineUserDetail() {
        if (mineUserDetail != null) return mineUserDetail;
        // 持久化
        String userKey =  KEY_ACTIVE_USER_DETAIL + userAccount.ID;
        String jsonString = shared.getString(userKey,null);
        if (jsonString != null) return KapGsonManager.KapJsonToModel(jsonString,KapModelUserDetail.class);
        // 创建一个
        mineUserDetail = new KapModelUserDetail();
        mineUserDetail.setID(userAccount.ID);
        return mineUserDetail;
    }

    public static void setMineUserDetail(KapModelUserDetail mineUserDetail) {
        if (mineUserDetail == null) return;
        KapApplication.mineUserDetail = mineUserDetail;
        // 持久化
        String userKey =  KEY_ACTIVE_USER_DETAIL + userAccount.ID;
        String jsonString = KapGsonManager.KapModelToJson(mineUserDetail);
        SharedPreferences.Editor editor = shared.edit();
        editor.putString(userKey,jsonString);
        editor.commit();
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
