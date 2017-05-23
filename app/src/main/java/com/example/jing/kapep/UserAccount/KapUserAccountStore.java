package com.example.jing.kapep.UserAccount;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.jing.kapep.Application.KapApplication;
import com.example.jing.kapep.Manager.KapGsonManager;

/**
 * Created by jing on 17/5/19.
 */
/**
 * 用户存储类
 * 此类无实例对象
 */

public class KapUserAccountStore {
    private static final String KEY_ACTIVE_USER_ID = "active_user_id";
    private static final String KEY_ACTIVE_USER = "active_user_";

    private static SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(KapApplication.getContext());
    // 存储
    public static void save(KapUserAccount kapUserAccount){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (kapUserAccount == null){
            // 注销操作
            editor.putInt(KEY_ACTIVE_USER_ID,-1);
            return;
        }
        // 保存
        int userID = kapUserAccount.ID;
        String jsonString = KapGsonManager.KapModelToJson(kapUserAccount);
        if (userID == -1 || jsonString == null) return;
        // 1. 存id ，且用id生成路径
        editor.putInt(KEY_ACTIVE_USER_ID,userID);
        // 2. 用生成的路径,存jsonString
        String userKey =  KEY_ACTIVE_USER + userID;
        editor.putString(userKey,jsonString);
        editor.commit();
    }
    // 获取
    public static KapUserAccount load(){
        int userID = sharedPreferences.getInt(KEY_ACTIVE_USER_ID,-1);
        if (userID == -1) return null;
        String userKey =  KEY_ACTIVE_USER + userID;
        String jsonString = sharedPreferences.getString(userKey,null);
        return KapGsonManager.KapJsonToModel(jsonString,KapUserAccount.class);
    }
}
