package com.example.jing.kapep.Application;

import android.app.Application;
import android.content.Context;

/**
 * Created by jing on 17/5/11.
 */

public class KapApplication extends Application{
    private static Context context;

    private static String  userToken;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }

    public static String getUserToken() {
        return userToken;
    }
}
