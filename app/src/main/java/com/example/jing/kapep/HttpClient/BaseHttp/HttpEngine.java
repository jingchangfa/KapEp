package com.example.jing.kapep.HttpClient.BaseHttp;

import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by jing on 17/5/10.
 */

public class HttpEngine {
    //网络请求引擎
    private Call call;
    /**
     *普通 HTTP GET 请求，没有进度回调
     * 一般情况下，比如我们希望获得返回的字符串，可以通过response.body().string()获取；
     * 如果希望获得返回的二进制字节数组，则调用response.body().bytes()；
     * 如果你想拿到返回的inputStream，则调用response.body().byteStream()
     * */
    public void httpGetRequest(String urlString, final HttpClickBase.HTTPAPICallBack finished){
        OkHttpClient mOkHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(urlString)
                .build();
        call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                finished.HTTPAPIFinishedBlock(null,e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String reString = response.body().string();
                finished.HTTPAPIFinishedBlock(reString,null);
            }
        });
    }
    /**
     *普通 HTTP POST 请求，没有进度回调
     * pareDictionary 指的是post的参数
     *
     * Hashmap遍历的一种方式
     * Map map = new HashMap();
     *Iterator iter = map.entrySet().iterator();
     *while (iter.hasNext()) {
     * Map.Entry entry = (Map.Entry) iter.next();
     *Object key = entry.getKey();
     *Object val = entry.getValue();
     }
     * */
    public void httpPostRequest(String urlString, HashMap pareDictionary,
                                final HttpClickBase.HTTPAPICallBack finished){
        /**
         * HASMAP value 也可能是json（array，hasmap）类型的字符串
         * */
        FormBody.Builder builder = new FormBody.Builder();
        //遍历添加key value
        Iterator iterator = pareDictionary.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry entry = (Map.Entry)iterator.next();
            String key = (String)entry.getKey();
            String value = String.valueOf(entry.getValue());
            if (key == null||value == null) continue;
            //添加键值对
            builder.add(key,value);
        }
        RequestBody requestBody = builder.build();
        final Request request = new Request.Builder()
                .url(urlString)
                .post(requestBody)
                .build();
        //创建并加入调度
        OkHttpClient mOkHttpClient = new OkHttpClient();
        call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("post失败原因是是：",String.valueOf(e.hashCode()));
                e.printStackTrace();
                finished.HTTPAPIFinishedBlock(null,e);
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String reString = response.body().string();
                Log.e("post请求结果是：",reString);
                finished.HTTPAPIFinishedBlock(reString,null);
            }
        });
    }

    /**
     *通过 HTTP POST 上传数据到服务器，bodyWithBlock 参数内拼接要上传的内容
     * */

    /**
     *下载文件到本地，urlString 为下载地址，
     * toPath 为本地存储路径（含文件名），
     * finished block中，如果成功，返回最终文件保存的地址
     * */

    /**
     *取消当前请求
     * */
    public void cancelRequest(){
        call.cancel();
    }
}
