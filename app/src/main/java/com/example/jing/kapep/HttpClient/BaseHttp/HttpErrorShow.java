package com.example.jing.kapep.HttpClient.BaseHttp;

import android.widget.Toast;

import com.example.jing.kapep.Application.KapApplication;
import com.example.jing.kapep.UserAccount.KapUserAccount;

/**
 * Created by jing on 17/5/10.
 */
// 单利
public class HttpErrorShow {
    /**
     * 错误类型
     */
    // http 网络错误
    private static final long API_NET_ERROR_UNKNOWN = 0;
    private static final long API_NET_ERROR_DownLoad_EXPIRED= -999;
    private static final long API_NET_ERROR_TIMEOUT = -1001;
    private static final long API_NET_ERROR_NONETWORK = -1009;
    //server 服务端错误
    private static final long API_ERROR_SERVER_UNKNOWN = 0;
    private static final long API_ERROR_SERVER_CODE_TOKEN_INVALID = 0x80000000;
    private static final long API_ERROR_SERVER_MOBILE_FOUND = 0x80000001;
    private static final long API_ERROR_SERVER_PASSWORD_ERROR = 0x80000002;
    private static final long API_ERROR_SERVER_MobileIsInvalid = 2147483653L;
    private static final long API_ERROR_SERVER_FriendRequestAlready = 2147483681L;
    private static final long API_ERROR_SERVER_HadBeenFriends = 2147483680L;


    // 单利模式
    private static HttpErrorShow httpErrorShow = new HttpErrorShow();
    public  static HttpErrorShow getStaticHttpErrorShow() {
        return  httpErrorShow;
    }

    // 网络请求错误展示
    public void httpErrorWithCode(long httpCode,String msg){
    }
    // 服务端错误展示
    public void serverErrorWithCode(long serverCode,String msg){
        if (serverCode == API_ERROR_SERVER_CODE_TOKEN_INVALID){
            // token 过期
            Toast.makeText(KapApplication.getContext(), "token过期", Toast.LENGTH_SHORT).show();
            // 返回登陆页
            KapApplication.getInstance().logInActivityChangeAction();
            // 清空一下存储
            KapUserAccount.saveKapUserAccount(null);
            return;
        }
        if (serverCode == API_ERROR_SERVER_MOBILE_FOUND){
            Toast.makeText(KapApplication.getContext(), "账号不存在", Toast.LENGTH_SHORT).show();
            return;
        }
        if (serverCode == API_ERROR_SERVER_PASSWORD_ERROR){
            Toast.makeText(KapApplication.getContext(), "账号密码错误", Toast.LENGTH_SHORT).show();
            return;
        }
        if (serverCode == API_ERROR_SERVER_MobileIsInvalid){
            Toast.makeText(KapApplication.getContext(), "输入正确的手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (serverCode == API_ERROR_SERVER_FriendRequestAlready){
            Toast.makeText(KapApplication.getContext(), "已申请", Toast.LENGTH_SHORT).show();
            return;
        }
        if (serverCode == API_ERROR_SERVER_HadBeenFriends){
            Toast.makeText(KapApplication.getContext(), "已添加", Toast.LENGTH_SHORT).show();
            return;
        }
        // 未知错误
        Toast.makeText(KapApplication.getContext(), "API_ERROR_SERVER_UNKNOWN", Toast.LENGTH_SHORT).show();
    }
}
