package com.example.jing.kapep.HttpClient.KapHttpChildren;

import android.util.Log;

import com.example.jing.kapep.HttpClient.BaseHttp.HttpClickBase;

/**
 * Created by jing on 17/5/11.
 */

public class KapImageAPIClient extends HttpClickBase {
    public String userHeaderImageURLStringWithString(String string){
        if (string.contains("http")) return string;
        String urlString = String.format("%s%s&token=%s",this.httpConfiguration.getHostString(),string,this.httpConfiguration.token());
        Log.d("获取图片文件",String.format("url\\n%s\\n返回url%s",urlString,string));
        return urlString;
    }

    public String userHeaderListenURLStringWithString(String string){
        if (string.contains("http")) return string;
        String urlString = String.format("%s%s&token=%s",this.httpConfiguration.getHostString(),string,this.httpConfiguration.token());
        Log.d("获取音乐文件",String.format("url\\n%s\\n返回url%s",urlString,string));
        return urlString;
    }
}
