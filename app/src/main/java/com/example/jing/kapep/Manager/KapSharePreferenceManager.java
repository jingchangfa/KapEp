package com.example.jing.kapep.Manager;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.jing.kapep.Application.KapApplication;

/**
 * Created by jing on 2017/6/12.
 * SharePreference 管理
 */

public class KapSharePreferenceManager {
    private static SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(KapApplication.getContext());

    public static SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }
}
