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
     * 错误类型
     */
    // http 网络错误
    private static final int API_NET_ERROR_UNKNOWN = 0;
    private static final int API_NET_ERROR_DownLoad_EXPIRED= -999;
    private static final int API_NET_ERROR_TIMEOUT = -1001;
    private static final int API_NET_ERROR_NONETWORK = -1009;
    //server 服务端错误
    private static final int API_SERVER_ERROR_UNKNOWN = 0;
    /**
     * 回调intface
     */
    public interface HTTPAPICallBack{
        void HTTPAPIFinishedBlock(Object object, IOException e);
    }
    public interface HTTPAPIFinishBack{
        boolean finishedBlock(Object object);
    }
    public interface HTTPAPIDefaultFailureBack{
        void defaultFailureBlock(int errorCode,String errorMsg);
    }
    /**
     * 工具
     * {@link HttpEngine} 引擎
     * {@link HttpConfiguration} 配置
     */
    protected HttpEngine httpEngine = new HttpEngine();
    protected HttpConfiguration httpConfiguration = HttpConfiguration.getStaticApiConfiguration();
    protected HttpErrorShow httpErrorShow = HttpErrorShow.getStaticHttpErrorShow();
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
            public void HTTPAPIFinishedBlock(Object object, IOException e) {
                boolean handleNetworkBo = handleNetworkError(e,defaultFailureBack);
                if (handleNetworkBo) return;//处理网络请求失败的错误
                boolean NetworkBo = handleCommonErrorFromResponse(object,defaultFailureBack);
                if (NetworkBo) return;//处理请求成功，服务端错误
                boolean ResponseBo = finishBack.finishedBlock(object);
                if (ResponseBo) return;//请求成功
                //未知的错误
                defaultFailureBack.defaultFailureBlock(-1,"unknown api failure");//未知错误
            }
        };
    }
    /**
     * 网络错误处理
     */
    private boolean handleNetworkError(IOException e,HTTPAPIDefaultFailureBack defaultFailureBack){
        if (e != null) return  false;
        int errCode = e.hashCode();
        String errmsg = "网络错误";
        defaultFailureBack.defaultFailureBlock(errCode,errmsg);
        httpErrorShow.httpErrorWithCode(errCode,errmsg);
        return true;
    }
    /**
     * 服务端错误处理1
     */
    private boolean handleCommonErrorFromResponse(Object object,HTTPAPIDefaultFailureBack defaultFailureBack){
        if (object == null) return false;
        JSONObject jsonObject = null;
        int errCode = -1;
        String errmsg = "";
        try {
            jsonObject =  new JSONObject(((String) object));
            if (httpConfiguration.resultIsSuccessWithResult(jsonObject)) return false;
            errCode = httpConfiguration.resultErrorCodeWithResult(jsonObject);
            errmsg = httpConfiguration.resultErrorMsgWithResult(jsonObject);
        }catch (JSONException e){
            Log.d("", "handleCommonErrorFromResponse: 返回的不是json格式的数据～");
        }
        defaultFailureBack.defaultFailureBlock(errCode,errmsg);
        httpErrorShow.serverErrorWithCode(errCode,errmsg);
        return true;
    }
}
