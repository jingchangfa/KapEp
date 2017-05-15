package com.example.jing.kapep.HttpClient.BaseHttp;

/**
 * Created by jing on 17/5/10.
 */
// 单利
public class HttpErrorShow {
    // 单利模式
    private static HttpErrorShow httpErrorShow = new HttpErrorShow();
    public  static HttpErrorShow getStaticHttpErrorShow() {
        return  httpErrorShow;
    }

    // 网络请求错误展示
    public void httpErrorWithCode(int httpCode,String msg){

    }
    public void serverErrorWithCode(int serverCode,String msg){

    }
}
