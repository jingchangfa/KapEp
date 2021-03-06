package com.example.jing.kapep.Model;

import java.util.Map;

import com.example.jing.kapep.Model.KapListenerAndFriend.KapModelUserDetail;
import com.google.gson.annotations.SerializedName;

/**
 * Created by jing on 17/5/22.
 */

public class KapModelMessage extends KapModelBase{
    // 消息类型
    public static final int MESSAGE_TYPE_SYSTEM_MESSAGE = 0;//系统消息
    public static final int MESSAGE_TYPE_FRIEND_REQUEST_ACCEPTED = 1;//我发出去的交友请求被接受
    public static final int MESSAGE_TYPE_FRIEND_REQUEST_REFUSED = 2;//我发出去的交友请求被拒绝
    public static final int MESSAGE_TYPE_FRIEND_REQUEST_RECEIVED = 3;//我收到了交友请求
    public static final int MESSAGE_TYPE_FRIEND_MADE_BY_CONTACT = 4;//其它人通过上传通讯录添加了我
    public static final int MESSAGE_TYPE_COMMENTS_RECEIVED = 5;//我发布的内容有评论
    public static final int MESSAGE_TYPE_COMMENTS_REPLIED = 6;//我发送的评论被回复
    // 消息状态
    public static final int ACK_TYPE_UNREAD = 0;//未读
    public static final int ACK_TYPE_READ = 1;//已读受
    public static final int ACK_TYPE_ACCEPTED = 2;//已同意
    public static final int ACK_TYPE_REFUSED = 3;//已拒绝

    @SerializedName("id")
    private int ID;

    @SerializedName("msg_type")
    private int msgType;

    @SerializedName("ack")
    private int ack;
    @SerializedName("content")
    private String content;

    @SerializedName("relate_id")
    private int relateID;

    @SerializedName("relate_user")
    private KapModelUserDetail relateUser;

    @SerializedName("relate_herd")
    private int relateHerdID;

    @SerializedName("relate_comment")
    private KapModelComment relateComment;

    @SerializedName("relate_post")
    private Map relatePost;

    @SerializedName("created_at")
    private long ts;


    public int getID() {
        return ID;
    }

    public int getMsgType() {
        return msgType;
    }

    public int getAck() {
        return ack;
    }

    public String getContent() {
        return content;
    }

    public int getRelateID() {
        return relateID;
    }

    public KapModelUserDetail getRelateUser() {
        return relateUser;
    }

    public int getRelateHerdID() {
        return relateHerdID;
    }

    public KapModelComment getRelateComment() {
        return relateComment;
    }

    public Map getRelatePost() {
        return relatePost;
    }

    public long getTs() {
        return ts;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public void setAck(int ack) {
        this.ack = ack;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setRelateID(int relateID) {
        this.relateID = relateID;
    }

    public void setRelateUser(KapModelUserDetail relateUser) {
        this.relateUser = relateUser;
    }

    public void setRelateHerdID(int relateHerdID) {
        this.relateHerdID = relateHerdID;
    }

    public void setRelateComment(KapModelComment relateComment) {
        this.relateComment = relateComment;
    }

    public void setRelatePost(Map relatePost) {
        this.relatePost = relatePost;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }
}
