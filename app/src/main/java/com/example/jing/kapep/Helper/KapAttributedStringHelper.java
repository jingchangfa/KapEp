package com.example.jing.kapep.Helper;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import com.example.jing.kapep.Application.KapApplication;
import com.example.jing.kapep.R;

/**
 * Created by jing on 2017/6/16.
 */
// 富文本生成器
public class KapAttributedStringHelper {
    public static SpannableStringBuilder AttributedStringByConfi(String allString
            , int normalColor, int selectedColor, String[] selectedStrings){
        if (allString.length() == 0) return null;
        SpannableStringBuilder sb = new SpannableStringBuilder(allString);
        ForegroundColorSpan normalColorSpan = new ForegroundColorSpan(KapApplication.getContext().getResources().getColor(normalColor));
        ForegroundColorSpan selectedColorSpan = new ForegroundColorSpan(KapApplication.getContext().getResources().getColor(selectedColor));
        sb.setSpan(normalColorSpan, 0, allString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        for(int i=0;i<selectedStrings.length;i++){
            String selectedString = selectedStrings[i];
            int startLocation = allString.indexOf(selectedString);
            int endLocation = startLocation+selectedString.length()+1;
            sb.setSpan(selectedColorSpan, startLocation, endLocation, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return sb;
    }
}
