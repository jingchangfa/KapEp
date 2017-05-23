package com.example.jing.kapep.HttpClient.KapHttpChildren;

import android.util.Log;

import com.example.jing.kapep.Helper.JSONObjToJavaClassHelper;
import com.example.jing.kapep.HttpClient.BaseHttp.HttpClickBase;
import com.example.jing.kapep.Manager.KapGsonManager;
import com.example.jing.kapep.Model.KapListenerAndFriend.KapModelPeople;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * Created by jing on 17/5/11.
 */

public class KapFriendAPIClient extends HttpClickBase {
    public interface KapFriendListInterface{
        void successResult(List modelList, int total, int offset);
    }
    public interface KapFriendNoParticipationInterface{
        void successResult();
    }
    //我的朋友 和 我关注的听众
    public void mineFriend(String searchString,
                           final KapFriendListInterface success,
                           final HTTPAPIDefaultFailureBack failure){
        String urlString = this.httpConfiguration.mineFriensPath();
        HashMap parameters = this.urlParametersDictionary(null);
        parameters.put("query",searchString);

        Log.d("查找朋友","url:"+urlString+"\n"+parameters.toString());
        HTTPAPICallBack finishBlock = this.customFinishedBlock(new HTTPAPIFinishBack() {
            @Override
            public boolean finishedBlock(String jsonString) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    //得过滤一次(不好调试，等回来继续调试)
//                    JSONObjToJavaClassHelper.jsonToMap(jsonObject.getJSONObject("friends"));
//                    List<KapModelPeople> modelList = KapGsonManager.KapJsonToModels(modelString);
//                    int total = -1;
//                    int offset = -1;
//                    success.successResult(modelList,total,offset);
                    return true;
                }catch (JSONException e){
                    Log.d("数据解析失败",e.toString());
                }
                return false;
            }
        },failure);
        this.httpEngine.httpPostRequest(urlString,parameters,finishBlock);
    }
    //添加用户为好友
    public void addUserFriends(int userID,
                               final KapFriendNoParticipationInterface success,
                               final HTTPAPIDefaultFailureBack failure){
        String urlString = this.httpConfiguration.addFriensPath();
        HashMap parameters = this.urlParametersDictionary(null);
        parameters.put("target_id",userID);

        Log.d("添加用户为好友","url:"+urlString+"\n"+parameters.toString());
        HTTPAPICallBack finishBlock = this.customFinishedBlock(new HTTPAPIFinishBack() {
            @Override
            public boolean finishedBlock(String jsonString) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    success.successResult();
                    return true;
                }catch (JSONException e){
                    Log.d("数据解析失败",e.toString());
                }
                return false;
            }
        },failure);
        this.httpEngine.httpPostRequest(urlString,parameters,finishBlock);
    }
    //同意加为好友
    public void acceptUserFriends(int userID,
                                  final KapFriendNoParticipationInterface success,
                                  final HTTPAPIDefaultFailureBack failure){
        String urlString = this.httpConfiguration.acceptFriensPath();
        HashMap parameters = this.urlParametersDictionary(null);
        parameters.put("user_id",userID);

        Log.d("同意加为好友","url:"+urlString+"\n"+parameters.toString());
        HTTPAPICallBack finishBlock = this.customFinishedBlock(new HTTPAPIFinishBack() {
            @Override
            public boolean finishedBlock(String jsonString) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    success.successResult();
                    return true;
                }catch (JSONException e){
                    Log.d("数据解析失败",e.toString());
                }
                return false;
            }
        },failure);
        this.httpEngine.httpPostRequest(urlString,parameters,finishBlock);
    }
    //拒绝加为好友
    public void refuseFriends(int userID,
                              final KapFriendNoParticipationInterface success,
                              final HTTPAPIDefaultFailureBack failure){
        String urlString = this.httpConfiguration.refuseFriensPath();
        HashMap parameters = this.urlParametersDictionary(null);
        parameters.put("user_id",userID);
        Log.d("拒绝加为好友","url:"+urlString+"\n"+parameters.toString());
        HTTPAPICallBack finishBlock = this.customFinishedBlock(new HTTPAPIFinishBack() {
            @Override
            public boolean finishedBlock(String jsonString) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    success.successResult();
                    return true;
                }catch (JSONException e){
                    Log.d("数据解析失败",e.toString());
                }
                return false;
            }
        },failure);
        this.httpEngine.httpPostRequest(urlString,parameters,finishBlock);
    }
    //删除好友
    public void deleteFriend(int userID,
                             final KapFriendNoParticipationInterface success,
                             final HTTPAPIDefaultFailureBack failure){
        String urlString = this.httpConfiguration.deleteFriensPath();
        HashMap parameters = this.urlParametersDictionary(null);
        parameters.put("friend_id",userID);
        Log.d("删除好友","url:"+urlString+"\n"+parameters.toString());
        HTTPAPICallBack finishBlock = this.customFinishedBlock(new HTTPAPIFinishBack() {
            @Override
            public boolean finishedBlock(String jsonString) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    success.successResult();
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
