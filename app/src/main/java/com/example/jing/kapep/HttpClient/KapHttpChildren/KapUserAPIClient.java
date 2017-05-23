package com.example.jing.kapep.HttpClient.KapHttpChildren;

import android.util.Log;

import com.example.jing.kapep.HttpClient.BaseHttp.HttpClickBase;
import com.example.jing.kapep.Manager.KapGsonManager;
import com.example.jing.kapep.Model.KapListenerAndFriend.KapModelPeople;
import com.example.jing.kapep.Model.KapModelCountry;
import com.example.jing.kapep.Model.KapModelMessage;
import com.example.jing.kapep.Model.KapModelUserDetail;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * Created by jing on 17/5/11.
 */

public class KapUserAPIClient extends HttpClickBase{
    public interface KapUserListInterface{
        void successResult(List modelList, int total, int offset);
    }
    public interface KapUserUnreadListInterface{
        void successResult(List modelList, int total, int offset,int unread);
    }
    public interface KapUserModelUserDetailInterface{
        void successResult(KapModelUserDetail model);
    }
    //获取我的详细信息
    public void mineDetail(final KapUserModelUserDetailInterface success,
                           final HTTPAPIDefaultFailureBack failure){
        String urlString = this.httpConfiguration.mineDetailPath();
        HashMap parameters = this.urlParametersDictionary(null);

        Log.d("获取我的详细信息","url:"+urlString+"\n"+parameters.toString());
        HTTPAPICallBack finishBlock = this.customFinishedBlock(new HTTPAPIFinishBack() {
            @Override
            public boolean finishedBlock(String jsonString) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    String modelString = jsonObject.getString("user");
                    KapModelUserDetail model = KapGsonManager.KapJsonToModel(modelString,KapModelUserDetail.class);
                    success.successResult(model);
                    return true;
                }catch (JSONException e){
                    Log.d("数据解析失败",e.toString());
                }
                return false;
            }
        },failure);
        this.httpEngine.httpPostRequest(urlString,parameters,finishBlock);
    }
    //国家列表
    public void country(final KapUserListInterface success,
                        final HTTPAPIDefaultFailureBack failure){
        String urlString = this.httpConfiguration.countryPath();
        HashMap parameters = this.urlParametersDictionary(null);

        Log.d("国家列表","url:"+urlString+"\n"+parameters.toString());
        HTTPAPICallBack finishBlock = this.customFinishedBlock(new HTTPAPIFinishBack() {
            @Override
            public boolean finishedBlock(String jsonString) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    String modelString = jsonObject.getString("regions");
                    List<KapModelCountry> modelList = KapGsonManager.KapJsonToModels(modelString);
                    int total = -1;
                    int offset = -1;
                    success.successResult(modelList,total,offset);
                    return true;
                }catch (JSONException e){
                    Log.d("数据解析失败",e.toString());
                }
                return false;
            }
        },failure);
        this.httpEngine.httpPostRequest(urlString,parameters,finishBlock);
    }
    //查找用户
    public void userSearch(int pageSize,
                           int offset,
                           String searchString,
                           final KapUserListInterface success,
                           final HTTPAPIDefaultFailureBack failure){
        String urlString = this.httpConfiguration.searchUserPath();
        HashMap parameters = this.urlParametersDictionary(null);
        parameters.put("query",searchString);
        parameters.put("page_size",pageSize);
        parameters.put("offset",offset);

        Log.d("查找用户","url:"+urlString+"\n"+parameters.toString());
        HTTPAPICallBack finishBlock = this.customFinishedBlock(new HTTPAPIFinishBack() {
            @Override
            public boolean finishedBlock(String jsonString) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    String modelString = jsonObject.getString("results");
                    List<KapModelPeople> modelList = KapGsonManager.KapJsonToModels(modelString);
                    int total = jsonObject.getInt("total");
                    int offset = jsonObject.getInt("offset");
                    success.successResult(modelList,total,offset);
                    return true;
                }catch (JSONException e){
                    Log.d("数据解析失败",e.toString());
                }
                return false;
            }
        },failure);
        this.httpEngine.httpPostRequest(urlString,parameters,finishBlock);
    }
    //推荐用户（朋友的朋友）
    public void recommendFriend(final KapUserListInterface success,
                                final HTTPAPIDefaultFailureBack failure){
        String urlString = this.httpConfiguration.recommendFriensPath();
        HashMap parameters = this.urlParametersDictionary(null);

        Log.d("推荐用户","url:"+urlString+"\n"+parameters.toString());
        HTTPAPICallBack finishBlock = this.customFinishedBlock(new HTTPAPIFinishBack() {
            @Override
            public boolean finishedBlock(String jsonString) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    String modelString = jsonObject.getString("possible_friends");
                    List<KapModelPeople> modelList = KapGsonManager.KapJsonToModels(modelString);
                    int total = -1;
                    int offset = -1;
                    success.successResult(modelList,total,offset);
                    return true;
                }catch (JSONException e){
                    Log.d("数据解析失败",e.toString());
                }
                return false;
            }
        },failure);
        this.httpEngine.httpPostRequest(urlString,parameters,finishBlock);
    }
    // 消息
    public void userMessage(int pageSize,
                            int offset,
                            final  KapUserUnreadListInterface success,
                            final  HTTPAPIDefaultFailureBack failure){
        String urlString = this.httpConfiguration.messagePath();
        HashMap parameters = this.urlParametersDictionary(null);
        parameters.put("page_size",pageSize);
        parameters.put("offset",offset);

        Log.d("用户消息","url:"+urlString+"\n"+parameters.toString());
        HTTPAPICallBack finishBlock = this.customFinishedBlock(new HTTPAPIFinishBack() {
            @Override
            public boolean finishedBlock(String jsonString) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    String modelString = jsonObject.getString("messages");
                    List<KapModelMessage> modelList = KapGsonManager.KapJsonToModels(modelString);
                    int total = jsonObject.getInt("total");
                    int offset = jsonObject.getInt("offset");
                    int unread = jsonObject.getInt("unread");
                    success.successResult(modelList,total,offset,unread);
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
