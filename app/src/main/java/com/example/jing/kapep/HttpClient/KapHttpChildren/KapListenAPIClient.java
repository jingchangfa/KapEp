package com.example.jing.kapep.HttpClient.KapHttpChildren;

import android.util.Log;

import com.example.jing.kapep.Helper.JSONObjToJavaClassHelper;
import com.example.jing.kapep.HttpClient.BaseHttp.HttpClickBase;
import com.example.jing.kapep.Manager.KapGsonManager;
import com.example.jing.kapep.Model.KapListenerAndFriend.KapModelPeople;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jing on 17/5/11.
 */

public class KapListenAPIClient extends HttpClickBase {
    public interface KapListenListInterface{
        void successResult(List modelList, int total, int offset);
    }
    public interface KapListenNoParticipationInterface{
        void successResult();
    }
    //用户的听众
    public void userListenerList(int pageSize,
                                 int offset,
                                 int userID,
                                 final KapListenListInterface success,
                                 final HTTPAPIDefaultFailureBack failure){
        String urlString = this.httpConfiguration.audiencesPath();
        HashMap parameters = this.urlParametersDictionary(null);
        parameters.put("user_id",userID);
        parameters.put("page_size",pageSize);
        parameters.put("offset",offset);


        Log.d("用户的听众","url:"+urlString+"\n"+parameters.toString());
        HTTPAPICallBack finishBlock = this.customFinishedBlock(new HTTPAPIFinishBack() {
            @Override
            public boolean finishedBlock(String jsonString) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    JSONArray audiences =  jsonObject.getJSONArray("audiences");
                    String needModelString = "[";
                    for (int i = 0; i < audiences.length(); i++) {
                        JSONObject audience = audiences.getJSONObject(i);
                        JSONObject need =  audience.getJSONObject("audience");
                        needModelString += need.toString();
                        if (i < audiences.length()-1) needModelString += ",";
                    }
                    needModelString += "]";
//                    得过滤一次(不好调试，等回来继续调试)
                    List<KapModelPeople> modelList = KapGsonManager.KapJsonToModels(needModelString,KapModelPeople.class);
                    int total = jsonObject.getInt("total");
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
    //将我的好友 推荐给 别人当听众
    public void makeFriendBecomeOtherListen(int userID,
                                            List userIDArray,
                                            final KapListenNoParticipationInterface success,
                                            final HTTPAPIDefaultFailureBack failure){
        String urlString = this.httpConfiguration.addAudiencesPath();
        HashMap parameters = this.urlParametersDictionary(null);
        parameters.put("user_id",userID);
        parameters.put("audiences",userIDArray);//这里穿个list 可能gg

        Log.d("将我的好友推荐给别人当听众","url:"+urlString+"\n"+parameters.toString());
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
    //可推荐听众的好友列表 //我的好友接口
    public void mineCanListenFriends(int userID,
                                     final KapListenListInterface success,
                                     final HTTPAPIDefaultFailureBack failure){
        String urlString = this.httpConfiguration.mineFriensPath();
        HashMap parameters = this.urlParametersDictionary(null);
        parameters.put("query","");
        parameters.put("poster_id",userID);

        Log.d("可推荐听众的好友列表","url:"+urlString+"\n"+parameters.toString());
        HTTPAPICallBack finishBlock = this.customFinishedBlock(new HTTPAPIFinishBack() {
            @Override
            public boolean finishedBlock(String jsonString) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
//                    得过滤一次(不好调试，等回来继续调试)
//                    JSONObjToJavaClassHelper.jsonToMap(jsonObject.getJSONObject("friends"));
//                    List<KapModelPeople> modelList = KapGsonManager.KapJsonToModels(modelString);
//                    int total = jsonObject.getInt("total");
//                    int offset = jsonObject.getInt("offset");
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
    //解除听众关系
    public void deleteListen(int userID,
                             boolean isDelete,
                             final KapListenNoParticipationInterface success,
                             final HTTPAPIDefaultFailureBack failure){
        String urlString = this.httpConfiguration.deleteListenPath();
        HashMap parameters = this.urlParametersDictionary(null);
        parameters.put("user_id",userID);

        Log.d("解除听众关系","url:"+urlString+"\n"+parameters.toString());
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
