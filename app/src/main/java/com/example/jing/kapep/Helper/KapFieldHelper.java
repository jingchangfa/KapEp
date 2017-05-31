package com.example.jing.kapep.Helper;

import android.util.Log;

import java.lang.reflect.Field;

/**
 * Created by jing on 17/5/31.
 */
// 反射的帮助类
public class KapFieldHelper {
    public static boolean kapGetPrivateValue(Object model,String fieldName){
        Class<?> personType = model.getClass();
        boolean bo = false;
        try {
            //访问私有属性
            Field field = personType.getDeclaredField(fieldName);
            field.setAccessible(true);
            bo = field.getBoolean(model);
        }catch (Exception e){
            Log.d("获取私有属性失败-value",e.toString() + "-" + bo);
        }
        return bo;
    }


}
