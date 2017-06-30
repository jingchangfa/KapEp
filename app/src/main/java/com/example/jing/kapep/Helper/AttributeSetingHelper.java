package com.example.jing.kapep.Helper;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
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
    /**
     * 参数带typeArray的 用于不可变的
     * 参数不带typeArray的 需要先调用 getReResourceId 获取id然后再调用此函数设置属性
     */
    public static String getTextString(TypedArray a, int id){
        return a.getString(id);
    }
    public static void setText(TextView v, TypedArray a, int id){
        String s = getTextString(a,id);
        if (s == null) return;
        v.setText(s);
    }
    public static void setText(TextView v, String textString){
        if (textString == null) return;
        v.setText(textString);
    }
    public static void setHint(TextView v, TypedArray a, int id){
        String s = getTextString(a,id);
        if (s == null) return;
        v.setHint(s);
    }
    public static void setHint(TextView v, String textString){
        if (textString == null) return;
        v.setHint(textString);
    }
    /**
     * getReResourceId 从TypedArray获取资源的ID
     *
     * */
    public static int getReResourceId(TypedArray a,int id){
        return a.getResourceId(id,0);
    }
    public static void setImageResource(ImageView v, TypedArray a, int id){
        int resource = getReResourceId(a, id);
        if (resource == 0) return;
        v.setImageResource(resource);
    }
    public static void setImageResource(ImageView v, int resource){
        if (resource == 0) return;
        v.setImageResource(resource);
    }

    public static void setTextColor(Context context, TextView v, TypedArray a, int id){
        int resource = getReResourceId(a, id);
        if (resource == 0) return;
        v.setTextColor(ContextCompat.getColor(context,resource));
    }
    public static void setTextColor(Context context, TextView v, int resource){
        if (resource == 0) return;
        v.setTextColor(ContextCompat.getColor(context,resource));
    }

    public static void setHintTextColor(Context context, TextView v, TypedArray a, int id){
        int resource = getReResourceId(a, id);
        if (resource == 0) return;
        v.setHintTextColor(ContextCompat.getColor(context,resource));
    }
    public static void setHintTextColor(Context context, TextView v, int resource){
        if (resource == 0) return;
        v.setHintTextColor(ContextCompat.getColor(context,resource));
    }

    public static void setBackgroundColor(Context context,View v, TypedArray a, int id){
        int resource = getReResourceId(a, id);
        if (resource == 0) return;
        v.setBackgroundColor(ContextCompat.getColor(context,resource));
    }
    public static void setBackgroundColor(Context context,View v, int resource){
        if (resource == 0) return;
        v.setBackgroundColor(ContextCompat.getColor(context,resource));
    }

    public static boolean getResourceBoolean(TypedArray a,int id){
        return a.getBoolean(id,true);
    }
    public static int getVisibility(TypedArray a, int id){
        return getResourceBoolean(a,id)?View.VISIBLE:View.GONE;
    }
    public static int getVisibility(boolean isVisible){
        return isVisible?View.VISIBLE:View.GONE;
    }
    /**
     * Background 这个属性就复杂多了
     * setShapeBackgroundFillColor 只设置shape的填充色
     * setShapeBackgroundStrokeColor 只设置shape的边框色
     * setShapeBackgroundRoundRadius 只设置shape的边框角度
     * setShapeBackgroundStrokeWidth 只设置shape的边框宽度
     * */
    private static boolean isGradientDrawable(View v){
        if (v.getBackground() == null) return false;
        Class vbClass = v.getBackground().getClass();
        boolean isClass = GradientDrawable.class.isAssignableFrom(vbClass);
        return isClass;
    }
    public static void setShapeBackgroundFillColor(Context context,View v, TypedArray a, int id){
        int resource = getReResourceId(a, id);
        if (resource == 0) return;
        if (!isGradientDrawable(v)) return;// 不是GradientDrawable 类型
        GradientDrawable drawable =(GradientDrawable)v.getBackground();
        drawable.setColor(ContextCompat.getColor(context,resource));
        v.setBackgroundDrawable(drawable);
    }
    public static void setShapeBackgroundFillColor(Context context,View v, int resource){
        if (resource == 0) return;
        if (!isGradientDrawable(v)) return;// 不是GradientDrawable 类型
        GradientDrawable drawable =(GradientDrawable)v.getBackground();
        drawable.setColor(ContextCompat.getColor(context,resource));
        v.setBackgroundDrawable(drawable);
    }

    public static void setShapeBackgroundStrokeWidth(Context context,View v, TypedArray a, int id){
        if (!isGradientDrawable(v)) return;// 不是GradientDrawable 类型
        int result = a.getInt(id,0);
        if (result == 0) return;
        GradientDrawable drawable =(GradientDrawable)v.getBackground();
//        drawable.setStroke(width, ContextCompat.getColor(context,resource));
//        v.setBackgroundDrawable(drawable);
    }
    public static void setShapeBackgroundStrokeWidth(Context context,View v, int resource){
        if (!isGradientDrawable(v)) return;// 不是GradientDrawable 类型
        if (resource == 0) return;
        GradientDrawable drawable =(GradientDrawable)v.getBackground();
//        drawable.setStroke(width, ContextCompat.getColor(context,resource));
//        v.setBackgroundDrawable(drawable);
    }

    public static void setShapeBackgroundStrokeColor(Context context,View v, TypedArray a, int id){
        int resource = getReResourceId(a, id);
        if (resource == 0) return;
        if (!isGradientDrawable(v)) return;// 不是GradientDrawable 类型
        GradientDrawable drawable =(GradientDrawable)v.getBackground();
//        drawable.setStroke(strokeWidth, ContextCompat.getColor(context,resource));
//        v.setBackgroundDrawable(drawable);
    }
    public static void setShapeBackgroundStrokeColor(Context context,View v, int resource){
        if (resource == 0) return;
        if (!isGradientDrawable(v)) return;// 不是GradientDrawable 类型
        GradientDrawable drawable =(GradientDrawable)v.getBackground();
//        drawable.setStroke(strokeWidth, ContextCompat.getColor(context,resource));
//        v.setBackgroundDrawable(drawable);
    }

    public static void setShapeBackgroundRoundRadius(Context context,View v, TypedArray a, int id){
        if (!isGradientDrawable(v)) return;// 不是GradientDrawable 类型
        int result = a.getInt(id,0);
        if (result == 0) return;
        GradientDrawable drawable =(GradientDrawable)v.getBackground();
        drawable.setCornerRadius(result);
        v.setBackgroundDrawable(drawable);
    }
    public static void setShapeBackgroundRoundRadius(Context context,View v, int resource){
        if (!isGradientDrawable(v)) return;// 不是GradientDrawable 类型
        if (resource == 0) return;
        GradientDrawable drawable =(GradientDrawable)v.getBackground();
        drawable.setCornerRadius(resource);
        v.setBackgroundDrawable(drawable);
    }

}
