package com.example.jing.kapep.HttpClient.BaseHttp;

import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

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
        OkHttpClient mOkHttpClient = createdClick();
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
        OkHttpClient mOkHttpClient = createdClick();
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
     * 通过 HTTP POST 上传数据到服务器，带参数 带进度的上传
     * urlString 请求的url
     * pareDictionary 参数列表
     * files 文件列表
     * 进度回调
     * finished 完成的回调
     * */
    //mdiatype 这个需要和服务端保持一致 你需要看下你们服务器设置的ContentType 是不是这个，他们设置的是哪个 我们要和他们保持一致
    private static final MediaType MEDIA_OBJECT_STREAM = MediaType.parse("application/octet-stream");
    public void httpPostRequest(String urlString,
                                HashMap pareDictionary,
                                HashMap <String,File> filesMap,
                                final HttpClickBase.HTTPAPIProgressCallBack progressCallBack,
                                final HttpClickBase.HTTPAPICallBack finished){
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        //遍历添加key value
        Iterator iterator = pareDictionary.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry entry = (Map.Entry)iterator.next();
            String key = (String)entry.getKey();
            String value = String.valueOf(entry.getValue());
            if (key == null||value == null) continue;
            //添加键值对
            builder.addFormDataPart(key,value);
        }
        if (filesMap != null){// 添加文件
            for (String key : filesMap.keySet()) {
                File file = filesMap.get(key);
                builder.addFormDataPart(key, file.getName(), createProgressRequestBody(MEDIA_OBJECT_STREAM, file, progressCallBack));
            }
        }
        //创建RequestBody
        RequestBody body = builder.build();
        //创建Request
        OkHttpClient mOkHttpClient = createdClick();
        final Request request = new Request.Builder().url(urlString).post(body).build();
        final Call call = mOkHttpClient.newBuilder().writeTimeout(50, TimeUnit.SECONDS).build().newCall(request);
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
     *下载文件到本地，urlString 为下载地址，
     * toPath 为本地存储路径（含文件名），
     * finished block中，如果成功，返回最终文件保存的地址
     * */
    // 没用到所以没写，参考文件最下方

    /**
     * 创建 click
     * */
    OkHttpClient createdClick(){
        OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
//                .readTimeout(READ_TIMEOUT,TimeUnit.SECONDS)//设置读取超时时间
//                .writeTimeout(WRITE_TIMEOUT,TimeUnit.SECONDS)//设置写的超时时间
                .connectTimeout(60, TimeUnit.SECONDS)//设置连接超时时间
                .build();
        return mOkHttpClient;
    }
    /**
     *取消当前请求
     * */
    public void cancelRequest(){
        call.cancel();
    }

    /**
     * 创建带进度的RequestBody
     * @param contentType MediaType
     * @param file  准备上传的文件
     * @param progressCallBack 回调
     * @param <T>
     * @return
     */
    private  <T> RequestBody createProgressRequestBody(final MediaType contentType,
                                                       final File file,
                                                       final HttpClickBase.HTTPAPIProgressCallBack progressCallBack)
    {
        return new RequestBody() {
            @Override
            public MediaType contentType() {
                return contentType;
            }

            @Override
            public long contentLength() {
                return file.length();
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                Source source;
                try {
                    source = Okio.source(file);
                    Buffer buf = new Buffer();
                    long remaining = contentLength();
                    long current = 0;
                    for (long readCount; (readCount = source.read(buf, 2048)) != -1; ) {
                        sink.write(buf, readCount);
                        current += readCount;
                        float pro = current/remaining;
                        if (progressCallBack == null) return;
                        progressCallBack.onProgress(remaining, current, pro);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }
}
//    /**
//     * 下载文件
//     * @param fileUrl 文件url
//     * @param destFileDir 存储目标目录
//     */
//    public <T> void downLoadFile(String fileUrl, final String destFileDir, final ReqProgressCallBack<T> callBack) {
//        final String fileName = MD5.encode(fileUrl);
//        final File file = new File(destFileDir, fileName);
//        if (file.exists()) {
//            successCallBack((T) file, callBack);
//            return;
//        }
//        final Request request = new Request.Builder().url(fileUrl).build();
//        final Call call = mOkHttpClient.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.e(TAG, e.toString());
//                failedCallBack("下载失败", callBack);
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                InputStream is = null;
//                byte[] buf = new byte[2048];
//                int len = 0;
//                FileOutputStream fos = null;
//                try {
//                    long total = response.body().contentLength();
//                    Log.e(TAG, "total------>" + total);
//                    long current = 0;
//                    is = response.body().byteStream();
//                    fos = new FileOutputStream(file);
//                    while ((len = is.read(buf)) != -1) {
//                        current += len;
//                        fos.write(buf, 0, len);
//                        Log.e(TAG, "current------>" + current);
//                        progressCallBack(total, current, callBack);
//                    }
//                    fos.flush();
//                    successCallBack((T) file, callBack);
//                } catch (IOException e) {
//                    Log.e(TAG, e.toString());
//                    failedCallBack("下载失败", callBack);
//                } finally {
//                    try {
//                        if (is != null) {
//                            is.close();
//                        }
//                        if (fos != null) {
//                            fos.close();
//                        }
//                    } catch (IOException e) {
//                        Log.e(TAG, e.toString());
//                    }
//                }
//            }
//        });
//    }