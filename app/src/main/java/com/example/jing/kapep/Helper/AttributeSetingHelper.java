package com.example.jing.kapep.Helper;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by jing on 17/5/15.
 */
// 属性设置的帮助类，非空才设置，否则停止
// 防止崩溃
public class AttributeSetingHelper {
    public static void setText(TextView v, TypedArray a, int id){
        String s = a.getString(id);
        if (s == null) return;
        v.setText(s);
    }
    public static void setHint(TextView v, TypedArray a, int id){
        String s = a.getString(id);
        if (s == null) return;
        v.setHint(s);
    }
    public static void setImageResource(ImageView v, TypedArray a, int id){
        int resource = a.getResourceId(id,0);
        if (resource == 0) return;
        v.setImageResource(resource);
    }
    public static void setTextColor(Context context, TextView v, TypedArray a, int id){
        int resource = a.getResourceId(id,0);
        if (resource == 0) return;
        v.setTextColor(ContextCompat.getColor(context,resource));
    }
    public static void setHintTextColor(Context context, TextView v, TypedArray a, int id){
        int resource = a.getResourceId(id,0);
        if (resource == 0) return;
        v.setHintTextColor(ContextCompat.getColor(context,resource));
    }
    public static void setBackgroundColor(Context context,View v, TypedArray a, int id){
        int resource = a.getResourceId(id,0);
        if (resource == 0) return;
        v.setBackgroundColor(ContextCompat.getColor(context,resource));
    }
    public static int getVisibility(TypedArray a, int id){
        return a.getBoolean(id,true)?View.VISIBLE:View.GONE;
    }
}
