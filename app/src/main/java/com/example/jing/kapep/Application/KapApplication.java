package com.example.jing.kapep.Application;

import android.app.Application;
import android.content.Context;

import com.example.jing.kapep.UserAccount.KapUserAccount;

/**
 * Created by jing on 17/5/11.
 */

public class KapApplication extends Application{
    private static Context context;
    private static KapUserAccount userAccount;
    private static String codeKeyString;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
    public static Context getContext() {
        return context;
    }

    public static KapUserAccount getUserAccount() {
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
        return getUserAccount().token;
    }
}
