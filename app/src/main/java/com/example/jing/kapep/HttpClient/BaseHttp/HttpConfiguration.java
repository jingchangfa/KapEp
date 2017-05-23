package com.example.jing.kapep.HttpClient.BaseHttp;

import com.example.jing.kapep.Application.KapApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by jing on 17/5/10.
 */
// 单利
public class HttpConfiguration {
    private String hostString = "http://app1.kap-ep.com:18888";
    public String getHostString() {
        return hostString;
    }
    // 单利模式
    private static HttpConfiguration apiConfiguration = new HttpConfiguration();
    public  static HttpConfiguration getStaticApiConfiguration() {
        return  apiConfiguration;
    }
    // 获取token
    public String token(){
        return KapApplication.getUserToken();
    }
    // 公共字断
    public HashMap<String, String> publicWordBreaks(){
        HashMap<String,String> publicWordBreaks = new HashMap<String,String>();
        String token = KapApplication.getUserToken();
        if (token != null) publicWordBreaks.put("token", token);
        return publicWordBreaks;
    }
    // 结果是否正确
    public boolean resultIsSuccessWithResult(JSONObject jsonObject) throws JSONException {
        int a = jsonObject.getInt("success");
        boolean b = a==0 ? false : true;
        return b;
    }
    // 返回错误code
    public int resultErrorCodeWithResult(JSONObject jsonObject) throws JSONException {
        int errcode = jsonObject.getInt("code");
        if (jsonObject.has("error_code"))errcode = jsonObject.getInt("error_code");
        return  errcode;
    }
    // 返回错误信息
    public String resultErrorMsgWithResult(JSONObject jsonObject) throws JSONException {
        String errmsg = "error_unknown";
        if (jsonObject.has("msg"))errmsg = jsonObject.getString("msg");
        return errmsg;
    }
    private String complateURLWithAPIString(String apiString){
        return hostString+"/"+apiString;
    }
    /**
     * 接口API
     */
    public String authLoginPath(){
        return complateURLWithAPIString("api/login");
    }
    public String needInvitationStringPath(){
        return complateURLWithAPIString("api/need_invite");
    }
    public String invitationStringPath(){
        return complateURLWithAPIString("api/verify_invitation");
    }
    public String verificationPath(){
        return complateURLWithAPIString("api/apply");
    }
    public String noVerificationPath(){
        return complateURLWithAPIString("api/register");
    }
    public String passWordSetPath(){
        return complateURLWithAPIString("api/setup_password");
    }

    public String selectedOccupationPath(){
        return complateURLWithAPIString("api/");
    }
    public String mineOtherDataPath(){
        return complateURLWithAPIString("api/update_profile");
    }
    public String emailStringPath(){
        return complateURLWithAPIString("api/");
    }

    public String contactsStringPath(){
        return complateURLWithAPIString("api/upload_contacts");
    }
    public String verificationFoundPassWordPath(){
        return complateURLWithAPIString("api/reset_password");
    }

    public String userImagePath(){
        return complateURLWithAPIString("api/image");
    }
    public String mineDetailPath(){
        return complateURLWithAPIString("api/me");
    }
    public String mineFriensPath(){
        return complateURLWithAPIString("api/friends");
    }
    public String recommendFriensPath(){
        return complateURLWithAPIString("api/possible_friends");
    }
    public String messagePath(){
        return complateURLWithAPIString("api/messages");
    }
    public String countryPath(){
        return complateURLWithAPIString("api/regions");
    }

    public String searchUserPath(){
        return complateURLWithAPIString("api/search_users");
    }

    public String addFriensPath(){
        return complateURLWithAPIString("api/add_friend");
    }
    public String userFriensQueryPath(){
        return complateURLWithAPIString("api/friend_requests");
    }
    public String mineFriensQueryPath(){
        return complateURLWithAPIString("api/my_friend_requests");
    }
    public String acceptFriensPath(){
        return complateURLWithAPIString("api/accept_friend_request");
    }
    public String refuseFriensPath(){
        return complateURLWithAPIString("api/refuse_friend_request");
    }
    public String deleteFriensPath(){
        return complateURLWithAPIString("api/delete_friend");
    }

    public String audiencesPath(){
        return complateURLWithAPIString("api/audiences");
    }
    public String addAudiencesPath(){
        return complateURLWithAPIString("api/add_audiences");
    }
    public String deleteListenPath(){
        return complateURLWithAPIString("api/quit_audience");
    }

    public String peoplePath(){
        return complateURLWithAPIString("api/people");
    }
    public String mySrtreamPath(){
        return complateURLWithAPIString("api/stream");
    }

    public String addContentPath(){
        return complateURLWithAPIString("api/add_post");
    }
    public String contentListPath(){
        return complateURLWithAPIString("api/posts");
    }
    public String contentLikeListPath(){
        return complateURLWithAPIString("api/posts_liked");
    }
    public String contentDetailPath(){
        return complateURLWithAPIString("api/post");
    }
    public String postLikePath(){
        return complateURLWithAPIString("api/post_like");
    }
    public String postUnLikePath(){
        return complateURLWithAPIString("api/post_unlike");
    }
    public String deleteContentPath(){
        return complateURLWithAPIString("api/delete_post");
    }

    public String commentListPath(){
        return complateURLWithAPIString("api/comments");
    }
    public String addCommentPath(){
        return complateURLWithAPIString("api/add_comments");
    }
    public String removeCommentPath(){
        return complateURLWithAPIString("api/rem_comments");
    }
}
