package com.example.jing.kapep.HttpClient.BaseHttp;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by jing on 17/5/10.
 */

public class HttpClickBase {
    // 基类
    /**
     * 回调intface
     */
    public interface HTTPAPICallBack{
        void HTTPAPIFinishedBlock(String jsonString, IOException e);
    }
    public interface HTTPAPIFinishBack{
        boolean finishedBlock(String jsonString);//throws JSONException
    }
    public interface HTTPAPIDefaultFailureBack{
        void defaultFailureBlock(long errorCode,String errorMsg);
    }
    /**
     * 进度回调接口
     * */
    public interface HTTPAPIProgressCallBack{
        void onProgress(long total, long current ,float progress);
    }
    /**
     * 工具
     * {@link HttpEngine} 引擎
     * {@link HttpConfiguration} 配置
     */
    protected HttpEngine httpEngine = new HttpEngine();
    protected HttpConfiguration httpConfiguration = HttpConfiguration.getStaticApiConfiguration();
    protected HttpErrorShow httpErrorShow = HttpErrorShow.getStaticHttpErrorShow();
    protected HttpThread httpThread = HttpThread.getStaticHttpThread();
    /**
     * HashMap添加公共属性
     * 子类继承的
     * */
    protected HashMap<String,String> urlParametersDictionary(HashMap<String,String>hashMap){
        HashMap<String, String> userMap = new HashMap<String, String>();
        if (hashMap != null){
            userMap.putAll(hashMap);
        }
        userMap.putAll(httpConfiguration.publicWordBreaks());
        return userMap;
    }
    /**
     *处理返回结果
     * 1.子类继承的
     * 2.内部使用的
     * */
    protected HTTPAPICallBack customFinishedBlock(final HTTPAPIFinishBack finishBack,
                                                  final HTTPAPIDefaultFailureBack defaultFailureBack){
        return new HTTPAPICallBack() {
            @Override
            public void HTTPAPIFinishedBlock(String jsonString, IOException e) {
                //主线程处理回调和显示
                runInUIThread(jsonString,e,finishBack,defaultFailureBack);
            }
        };
    }
    void runInUIThread(final String jsonString,
                       final IOException e,
                       final HTTPAPIFinishBack finishBack,
                       final HTTPAPIDefaultFailureBack defaultFailureBack){
        httpThread.runOnUIthread(new Runnable() {
            @Override
            public void run() {
                boolean handleNetworkBo = handleNetworkError(e,defaultFailureBack);
                if (handleNetworkBo) return;//网络问题导致请求失败
                boolean NetworkBo = handleCommonErrorFromResponse(jsonString,defaultFailureBack);
                if (NetworkBo) return;//服务端问题导致请求失败
                boolean ResponseBo = finishBack.finishedBlock(jsonString);// 解析数据
                if (ResponseBo) return;//数据解析成功
                //未知的错误
                defaultFailureBack.defaultFailureBlock(-1,"unknown api failure");//未知错误
            }
        });
    }
    /**
     * 网络错误处理
     */
    private boolean handleNetworkError(IOException e,HTTPAPIDefaultFailureBack defaultFailureBack){
        if (e == null) return  false;
        long errCode = e.hashCode();
        String errmsg = "网络错误";
        defaultFailureBack.defaultFailureBlock(errCode,errmsg);
        httpErrorShow.httpErrorWithCode(errCode,errmsg);
        return true;
    }
    /**
     * 服务端错误处理1
     */
    private boolean handleCommonErrorFromResponse(String jsonString,HTTPAPIDefaultFailureBack defaultFailureBack){
        if (jsonString == null) return false;
        JSONObject jsonObject = null;
        long errCode = -1;
        String errmsg = "";
        try {
            jsonObject =  new JSONObject(jsonString);
            if (httpConfiguration.resultIsSuccessWithResult(jsonObject)) return false;
            errCode = httpConfiguration.resultErrorCodeWithResult(jsonObject);
            errmsg = httpConfiguration.resultErrorMsgWithResult(jsonObject);
        }catch (JSONException e){
            Log.d("", "handleCommonErrorFromResponse: 返回的不是json格式的数据～"+e.toString());
        }
        defaultFailureBack.defaultFailureBlock(errCode,errmsg);
        httpErrorShow.serverErrorWithCode(errCode,errmsg);
        Log.d("服务端错误",jsonString);
        return true;
    }
}
