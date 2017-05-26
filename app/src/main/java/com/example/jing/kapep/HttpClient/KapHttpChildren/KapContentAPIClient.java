package com.example.jing.kapep.HttpClient.KapHttpChildren;

import android.util.Log;

import com.example.jing.kapep.HttpClient.BaseHttp.HttpClickBase;
import com.example.jing.kapep.Manager.KapGsonManager;
import com.example.jing.kapep.Model.KapListenerAndFriend.KapModelPeople;
import com.example.jing.kapep.Model.KapModelComment;
import com.example.jing.kapep.Model.KapModelContent.KapMineContent.KapModelMineServerContonModel;
import com.example.jing.kapep.Model.KapModelContent.KapOtherContent.KapModelOtherConton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jing on 17/5/11.
 */

public class KapContentAPIClient extends HttpClickBase {
    public interface KapContentListInterface{
        void successResult(List modelList,int total,int offset);
    }
    public interface KapContentNoParticipationInterface{
        void successResult();
    }
    public interface KapContentModelMineServerContonInterface{
        void successResult(KapModelMineServerContonModel model);
    }
    public interface KapContentModelOtherContonContonInterface{
        void successResult(KapModelOtherConton model);
    }
    public interface KapContentModelCommentInterface{
        void successResult(KapModelComment model);
    }
    //主页 people
    public void homePagePeopleList(int pageSize,
                                   int offset,
                                   final KapContentListInterface success,
                                   final HTTPAPIDefaultFailureBack failure){
        String urlString = this.httpConfiguration.peoplePath();
        HashMap parameters = this.urlParametersDictionary(null);
        parameters.put("page_size",pageSize);
        parameters.put("offset",offset);
        Log.d("主页","url:"+urlString+"\n"+parameters.toString());
        HTTPAPICallBack finishBlock = this.customFinishedBlock(new HTTPAPIFinishBack() {
            @Override
            public boolean finishedBlock(String jsonString) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    String modelString = jsonObject.getString("people");
                    List<KapModelPeople> modelList = KapGsonManager.KapJsonToModels(modelString,KapModelPeople.class);
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
    /**
     * 发帖子 (未实现,待研究)
     * 1.穿文件
     * 2.穿帖子
     */
    public void uploadContent(HashMap hashMap,
                              final KapContentModelMineServerContonInterface success,
                              final HTTPAPIDefaultFailureBack failure){
        String urlString = this.httpConfiguration.addContentPath();
        HashMap parameters = this.urlParametersDictionary(hashMap);

        Log.d("发帖子","url:"+urlString+"\n"+parameters.toString());
        HTTPAPICallBack finishBlock = this.customFinishedBlock(new HTTPAPIFinishBack() {
            @Override
            public boolean finishedBlock(String jsonString) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    String modelString = jsonObject.getString("post");
                    KapModelMineServerContonModel model = KapGsonManager.KapJsonToModel(modelString,KapModelMineServerContonModel.class);
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


    //列表
    public void userContentList(int pageSize,
                                int offset,
                                int userID,
                                final KapContentListInterface success,
                                final HTTPAPIDefaultFailureBack failure){
        String urlString = this.httpConfiguration.contentListPath();
        HashMap parameters = this.urlParametersDictionary(null);
        parameters.put("poster_id",userID);
        parameters.put("page_size",pageSize);
        parameters.put("offset",offset);

        Log.d("列表","url:"+urlString+"\n"+parameters.toString());
        HTTPAPICallBack finishBlock = this.customFinishedBlock(new HTTPAPIFinishBack() {
            @Override
            public boolean finishedBlock(String jsonString) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    String modelString = jsonObject.getString("posts");
                    List<KapModelOtherConton> modelList = KapGsonManager.KapJsonToModels(modelString,KapModelOtherConton.class);
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
    //添加了喜欢的帖子
    public void userLikeContentList(int pageSize,
                                    int offset,
                                    int userID,
                                    final KapContentListInterface success,
                                    final HTTPAPIDefaultFailureBack failure){
        String urlString = this.httpConfiguration.contentLikeListPath();
        HashMap parameters = this.urlParametersDictionary(null);
        parameters.put("poster_id",userID);
        parameters.put("page_size",pageSize);
        parameters.put("offset",offset);

        Log.d("喜欢的帖子","url:"+urlString+"\n"+parameters.toString());
        HTTPAPICallBack finishBlock = this.customFinishedBlock(new HTTPAPIFinishBack() {
            @Override
            public boolean finishedBlock(String jsonString) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    String modelString = jsonObject.getString("posts");
                    List<KapModelOtherConton> modelList = KapGsonManager.KapJsonToModels(modelString,KapModelOtherConton.class);
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
    //详情
    public void contentDetail(int postID,
                              int posterID,
                              final KapContentModelOtherContonContonInterface success,
                              final HTTPAPIDefaultFailureBack failure){
        String urlString = this.httpConfiguration.contentDetailPath();
        HashMap parameters = this.urlParametersDictionary(null);
        parameters.put("poster_id",posterID);
        parameters.put("post_id",postID);

        Log.d("帖子详情","url:"+urlString+"\n"+parameters.toString());
        HTTPAPICallBack finishBlock = this.customFinishedBlock(new HTTPAPIFinishBack() {
            @Override
            public boolean finishedBlock(String jsonString) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    String modelString = jsonObject.getString("post");
                    KapModelOtherConton model = KapGsonManager.KapJsonToModel(modelString,KapModelOtherConton.class);
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
    //添加/移除喜爱
    public void contentLike(int postID,
                            int posterID,
                            final KapContentNoParticipationInterface success,
                            final HTTPAPIDefaultFailureBack failure){
        String urlString = this.httpConfiguration.postLikePath();
        HashMap parameters = this.urlParametersDictionary(null);
        parameters.put("poster_id",posterID);
        parameters.put("post_id",postID);

        Log.d("添加喜爱","url:"+urlString+"\n"+parameters.toString());
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
    public void contentUnLike(int postID,
                              int posterID,
                              final KapContentNoParticipationInterface success,
                              final HTTPAPIDefaultFailureBack failure){
        String urlString = this.httpConfiguration.postUnLikePath();
        HashMap parameters = this.urlParametersDictionary(null);
        parameters.put("poster_id",posterID);
        parameters.put("post_id",postID);

        Log.d("移除喜爱","url:"+urlString+"\n"+parameters.toString());
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
    //删除
    public void deleteContent(int contentID,
                              final KapContentNoParticipationInterface success,
                              final HTTPAPIDefaultFailureBack failure){
        String urlString = this.httpConfiguration.deleteContentPath();
        HashMap parameters = this.urlParametersDictionary(null);
        parameters.put("post_id",contentID);

        Log.d("删除帖子","url:"+urlString+"\n"+parameters.toString());
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
    /**
     * 评论
     * commentListWithPostID 评论列表
     * addComment 添加
     * removeComment 删除
     */
    public void commentList(int postID,
                            int posterID,
                            final KapContentListInterface success,
                            final HTTPAPIDefaultFailureBack failure){
        String urlString = this.httpConfiguration.commentListPath();
        HashMap parameters = this.urlParametersDictionary(null);
        parameters.put("post_id",postID);
        parameters.put("poster_id",posterID);

        Log.d("评论列表","url:"+urlString+"\n"+parameters.toString());
        HTTPAPICallBack finishBlock = this.customFinishedBlock(new HTTPAPIFinishBack() {
            @Override
            public boolean finishedBlock(String jsonString) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    String modelString = jsonObject.getString("comments");
                    List<KapModelOtherConton> modelList = KapGsonManager.KapJsonToModels(modelString,KapModelOtherConton.class);
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
    public void addComment(int postID,
                           int posterID,
                           int commentID,
                           String content,
                           final KapContentModelCommentInterface success,
                           final HTTPAPIDefaultFailureBack failure){
        String urlString = this.httpConfiguration.addCommentPath();
        HashMap parameters = this.urlParametersDictionary(null);
        parameters.put("post_id",postID);
        parameters.put("message",content);
        parameters.put("poster_id",posterID);
        if (commentID != -1) parameters.put("reply_to",commentID);

        Log.d("添加评论","url:"+urlString+"\n"+parameters.toString());
        HTTPAPICallBack finishBlock = this.customFinishedBlock(new HTTPAPIFinishBack() {
            @Override
            public boolean finishedBlock(String jsonString) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    String modelString = jsonObject.getString("comment");
                    KapModelComment model = KapGsonManager.KapJsonToModel(modelString,KapModelComment.class);
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
    public void removeComment(int postID,
                              final KapContentNoParticipationInterface success,
                              final HTTPAPIDefaultFailureBack failure){
        String urlString = this.httpConfiguration.removeCommentPath();
        HashMap parameters = this.urlParametersDictionary(null);
        parameters.put("comment_id",postID);

        Log.d("删除评论","url:"+urlString+"\n"+parameters.toString());
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
