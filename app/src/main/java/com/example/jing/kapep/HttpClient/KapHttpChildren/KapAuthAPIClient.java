package com.example.jing.kapep.HttpClient.KapHttpChildren;

import android.util.Log;

import com.example.jing.kapep.Helper.JSONObjToJavaClassHelper;
import com.example.jing.kapep.Helper.StringMD5SHAHelper;
import com.example.jing.kapep.HttpClient.BaseHttp.HttpClickBase;
import com.example.jing.kapep.Model.KapModelUserDetail;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jing on 17/5/11.
 */

public class KapAuthAPIClient extends HttpClickBase {
    public interface KapAuthLoginInterface{
         void successResult(String token, int userID, Map herd, boolean isFirstLogin);
    }
    public interface KapAuthInvitationCodeInterface{
         void successResult(String codeKeyString);
    }
    public interface KapAuthBooleanInterface{
         void successResult(Boolean need);
    }
    public interface KapAuthNoParticipationInterface{
         void successResult();
    }
    public interface KapAuthUserDetailInterface{
         void successResult(KapModelUserDetail userDetail);
    }

    //登录
    public void authLogin(String numberString,
                          String passWordString,
                          final KapAuthLoginInterface success,
                          final HTTPAPIDefaultFailureBack failure){
        String urlString = this.httpConfiguration.authLoginPath();
        String shaString = StringMD5SHAHelper.getSHA(passWordString);
        HashMap parameters = this.urlParametersDictionary(null);
        parameters.put("mobile",numberString);
        parameters.put("password",shaString);
        Log.d("帐号密码登录","url:"+urlString+"\n"+parameters.toString());
        HTTPAPICallBack finishBlock = this.customFinishedBlock(new HTTPAPIFinishBack() {
            @Override
            public boolean finishedBlock(String jsonString) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    String token = jsonObject.getString("token");
                    int userID = jsonObject.getInt("user_id");
                    Map herd = JSONObjToJavaClassHelper.jsonToMap(jsonObject.getJSONObject("herd"));
                    success.successResult(token,userID,herd,false);
                    return true;
                }catch (JSONException e){
                    Log.d("数据解析失败",e.toString());
                }
                return false;
            }
        },failure);
        this.httpEngine.httpPostRequest(urlString,parameters,finishBlock);
    }
    //验证邀请码
    public void invitationCode(String invitationString,
            final KapAuthInvitationCodeInterface success,
            final HTTPAPIDefaultFailureBack failure){
        String urlString = this.httpConfiguration.invitationStringPath();
        HashMap parameters = this.urlParametersDictionary(null);
        parameters.put("code",invitationString);

        Log.d("验证邀请码","url:"+urlString+"\n"+parameters.toString());
        HTTPAPICallBack finishBlock = this.customFinishedBlock(new HTTPAPIFinishBack() {
            @Override
            public boolean finishedBlock(String jsonString) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    String codeKeyString = jsonObject.getString("key");
                    success.successResult(codeKeyString);
                    return true;
                }catch (JSONException e){
                    Log.d("数据解析失败",e.toString());
                }
                return false;
            }
        },failure);
        this.httpEngine.httpPostRequest(urlString,parameters,finishBlock);
    }

    //是否需要邀请码
    public void needInvitationCode(final KapAuthBooleanInterface success,
                                   final HTTPAPIDefaultFailureBack failure){
        String urlString = this.httpConfiguration.needInvitationStringPath();
        HashMap parameters = null;

        Log.d("是否需要邀请码","url:"+urlString+"\n"+parameters.toString());
        HTTPAPICallBack finishBlock = this.customFinishedBlock(new HTTPAPIFinishBack() {
            @Override
            public boolean finishedBlock(String jsonString) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    boolean needInvite = jsonObject.getBoolean("need_invite");
                    success.successResult(needInvite);
                    return true;
                }catch (JSONException e){
                    Log.d("数据解析失败",e.toString());
                }
                return false;
            }
        },failure);
        this.httpEngine.httpPostRequest(urlString,parameters,finishBlock);
    }
    //通过验证码和激活码注册并且登录
    public void verification(String numberString,
                             String invitationCodeKeyString,
                             final KapAuthNoParticipationInterface success,
                             final HTTPAPIDefaultFailureBack failure){
        String urlString = this.httpConfiguration.verificationPath();
        HashMap parameters = this.urlParametersDictionary(null);
        parameters.put("mobile",numberString);
        parameters.put("invitation_key",invitationCodeKeyString);

        Log.d("获取验证码相关","url:"+urlString+"\n"+parameters.toString());
        HTTPAPICallBack finishBlock = this.customFinishedBlock(new HTTPAPIFinishBack() {
            @Override
            public boolean finishedBlock(String jsonString) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    if (jsonObject.getBoolean("success")) success.successResult();
                    return true;
                }catch (JSONException e){
                    Log.d("数据解析失败",e.toString());
                }
                return false;
            }
        },failure);
        this.httpEngine.httpPostRequest(urlString,parameters,finishBlock);
    }
    public void verification(String numberString,
                             final KapAuthNoParticipationInterface success,
                             final HTTPAPIDefaultFailureBack failure){
        String urlString = this.httpConfiguration.noVerificationPath();
        HashMap parameters = this.urlParametersDictionary(null);
        parameters.put("mobile",numberString);

        Log.d("获取验证码相关","url:"+urlString+"\n"+parameters.toString());
        HTTPAPICallBack finishBlock = this.customFinishedBlock(new HTTPAPIFinishBack() {
            @Override
            public boolean finishedBlock(String jsonString) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    if (jsonObject.getBoolean("success")) success.successResult();
                    return true;
                }catch (JSONException e){
                    Log.d("数据解析失败",e.toString());
                }
                return false;
            }
        },failure);
        this.httpEngine.httpPostRequest(urlString,parameters,finishBlock);
    }
    public void authLoginverification(String numberString,
                                      String verificationString,
                                      String invitationCodeKeyString,
                                      String countryString,
                                      final KapAuthLoginInterface success,
                                      final HTTPAPIDefaultFailureBack failure){
        String urlString = this.httpConfiguration.verificationPath();
        HashMap parameters = this.urlParametersDictionary(null);
        parameters.put("mobile",numberString);
        parameters.put("activation_code",verificationString);
        parameters.put("invitation_key",invitationCodeKeyString);
        parameters.put("area_code",countryString);

        Log.d("验证码登录","url:"+urlString+"\n"+parameters.toString());
        HTTPAPICallBack finishBlock = this.customFinishedBlock(new HTTPAPIFinishBack() {
            @Override
            public boolean finishedBlock(String jsonString) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    String token = jsonObject.getString("token");
                    int userID = jsonObject.getInt("user_id");
                    Map herd = JSONObjToJavaClassHelper.jsonToMap(jsonObject.getJSONObject("herd"));
                    success.successResult(token,userID,herd,false);
                    return true;
                }catch (JSONException e){
                    Log.d("数据解析失败",e.toString());
                }
                return false;
            }
        },failure);
        this.httpEngine.httpPostRequest(urlString,parameters,finishBlock);
    }
    public void authLoginverification(String numberString,
                                      String verificationString,
                                      String countryString,
                                      final KapAuthLoginInterface success,
                                      final HTTPAPIDefaultFailureBack failure){
        String urlString = this.httpConfiguration.noVerificationPath();
        HashMap parameters = this.urlParametersDictionary(null);
        parameters.put("mobile",numberString);
        parameters.put("activation_code",verificationString);
        parameters.put("area_code",countryString);

        Log.d("验证码登录","url:"+urlString+"\n"+parameters.toString());
        HTTPAPICallBack finishBlock = this.customFinishedBlock(new HTTPAPIFinishBack() {
            @Override
            public boolean finishedBlock(String jsonString) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    String token = jsonObject.getString("token");
                    int userID = jsonObject.getInt("user_id");
                    Map herd = JSONObjToJavaClassHelper.jsonToMap(jsonObject.getJSONObject("herd"));
                    success.successResult(token,userID,herd,false);
                    return true;
                }catch (JSONException e){
                    Log.d("数据解析失败",e.toString());
                }
                return false;
            }
        },failure);
        this.httpEngine.httpPostRequest(urlString,parameters,finishBlock);
    }
    //通过验证码重置密码的登录
    public void  authLoginNoPassWord(String numberString,
                                     final KapAuthNoParticipationInterface success,
                                     final HTTPAPIDefaultFailureBack failure){
        String urlString = this.httpConfiguration.verificationFoundPassWordPath();
        HashMap parameters = this.urlParametersDictionary(null);
        parameters.put("mobile",numberString);

        Log.d("获取验证码相关","url:"+urlString+"\n"+parameters.toString());
        HTTPAPICallBack finishBlock = this.customFinishedBlock(new HTTPAPIFinishBack() {
            @Override
            public boolean finishedBlock(String jsonString) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    if (jsonObject.getBoolean("success")) success.successResult();
                    return true;
                }catch (JSONException e){
                    Log.d("数据解析失败",e.toString());
                }
                return false;
            }
        },failure);
        this.httpEngine.httpPostRequest(urlString,parameters,finishBlock);
    }
    public void  authLoginNoPassWordVerification(String numberString,
                                                 String verificationString,
                                                 final KapAuthLoginInterface success,
                                                 final HTTPAPIDefaultFailureBack failure){
        String urlString = this.httpConfiguration.verificationFoundPassWordPath();
        HashMap parameters = this.urlParametersDictionary(null);
        parameters.put("mobile",numberString);
        parameters.put("activation_code",verificationString);

        Log.d("验证码登录","url:"+urlString+"\n"+parameters.toString());
        HTTPAPICallBack finishBlock = this.customFinishedBlock(new HTTPAPIFinishBack() {
            @Override
            public boolean finishedBlock(String jsonString) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    String token = jsonObject.getString("token");
                    int userID = jsonObject.getInt("user_id");
                    Map herd = JSONObjToJavaClassHelper.jsonToMap(jsonObject.getJSONObject("herd"));
                    success.successResult(token,userID,herd,false);
                    return true;
                }catch (JSONException e){
                    Log.d("数据解析失败",e.toString());
                }
                return false;
            }
        },failure);
        this.httpEngine.httpPostRequest(urlString,parameters,finishBlock);
    }
    //设置密码
    public void authSetPassWord(String passWordString,
            final KapAuthNoParticipationInterface success,
            final HTTPAPIDefaultFailureBack failure){
        String urlString = this.httpConfiguration.passWordSetPath();
        String shaString = StringMD5SHAHelper.getSHA(passWordString);

        HashMap parameters = this.urlParametersDictionary(null);
        parameters.put("password",passWordString);

        Log.d("设置密码登录","url:"+urlString+"\n"+parameters.toString());
        HTTPAPICallBack finishBlock = this.customFinishedBlock(new HTTPAPIFinishBack() {
            @Override
            public boolean finishedBlock(String jsonString) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    if (jsonObject.getBoolean("success")) success.successResult();
                    return true;
                }catch (JSONException e){
                    Log.d("数据解析失败",e.toString());
                }
                return false;
            }
        },failure);
        this.httpEngine.httpPostRequest(urlString,parameters,finishBlock);
    }
    //选择职业
    public void selectedOccupation(String OccupationString,
            final KapAuthNoParticipationInterface success,
            final HTTPAPIDefaultFailureBack failure){
        String urlString = this.httpConfiguration.selectedOccupationPath();
        HashMap parameters = this.urlParametersDictionary(null);
        parameters.put("",OccupationString);

        Log.d("选择职业","url:"+urlString+"\n"+parameters.toString());
        HTTPAPICallBack finishBlock = this.customFinishedBlock(new HTTPAPIFinishBack() {
            @Override
            public boolean finishedBlock(String jsonString) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    if (jsonObject.getBoolean("success")) success.successResult();
                    return true;
                }catch (JSONException e){
                    Log.d("数据解析失败",e.toString());
                }
                return false;
            }
        },failure);
        this.httpEngine.httpPostRequest(urlString,parameters,finishBlock);
    }
    /**
     * 完善资料
     * 1.传头像
     * 2.传资料
     * */
    public void mineOtherString(HashMap pareDictionary,
            final KapAuthUserDetailInterface success,
            final HTTPAPIDefaultFailureBack failure){
        String urlString = this.httpConfiguration.authLoginPath();
        HashMap parameters = this.urlParametersDictionary(null);
        parameters.put("","");

        Log.d("验证邀请码","url:"+urlString+"\n"+parameters.toString());
        HTTPAPICallBack finishBlock = this.customFinishedBlock(new HTTPAPIFinishBack() {
            @Override
            public boolean finishedBlock(String jsonString) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    return true;
                }catch (JSONException e){
                    Log.d("数据解析失败",e.toString());
                }
                return false;
            }
        },failure);
        this.httpEngine.httpPostRequest(urlString,parameters,finishBlock);
    }
    //绑定邮箱
    public void bindEmail(String emailString,
                          Boolean canSend,
            final KapAuthNoParticipationInterface success,
            final HTTPAPIDefaultFailureBack failure){
        String urlString = this.httpConfiguration.emailStringPath();
        HashMap parameters = this.urlParametersDictionary(null);
        parameters.put("email",emailString);
        parameters.put("canSend",canSend);

        Log.d("绑定邮箱","url:"+urlString+"\n"+parameters.toString());
        HTTPAPICallBack finishBlock = this.customFinishedBlock(new HTTPAPIFinishBack() {
            @Override
            public boolean finishedBlock(String jsonString) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    if (jsonObject.getBoolean("success")) success.successResult();
                    return true;
                }catch (JSONException e){
                    Log.d("数据解析失败",e.toString());
                }
                return false;
            }
        },failure);
        this.httpEngine.httpPostRequest(urlString,parameters,finishBlock);
    }
    //上传通讯录
    public void uploadContacts(List contactsArray,
            final KapAuthNoParticipationInterface success,
            final HTTPAPIDefaultFailureBack failure){
        String urlString = this.httpConfiguration.contactsStringPath();
        HashMap parameters = this.urlParametersDictionary(null);
        parameters.put("contacts",contactsArray);

        Log.d("上传通讯录","url:"+urlString+"\n"+parameters.toString());
        HTTPAPICallBack finishBlock = this.customFinishedBlock(new HTTPAPIFinishBack() {
            @Override
            public boolean finishedBlock(String jsonString) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    if (jsonObject.getBoolean("success")) success.successResult();
                    return true;
                }catch (JSONException e){
                    Log.d("数据解析失败",e.toString());
                }
                return false;
            }
        },failure);
        this.httpEngine.httpPostRequest(urlString,parameters,finishBlock);
    }
}
