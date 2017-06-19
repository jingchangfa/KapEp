package com.example.jing.kapep.Model;

import com.example.jing.kapep.Model.KapListenerAndFriend.KapModelContentBase;
import com.example.jing.kapep.Model.KapListenerAndFriend.KapModelUserDetail;
import com.google.gson.annotations.SerializedName;

/**
 * Created by jing on 17/5/22.
 */

public class KapModelComment extends KapModelBase{

    @SerializedName("id")
    private int ID;

    @SerializedName("post_id")
    private int postID;

    @SerializedName("message")
    private String message;

    @SerializedName("author")
    private KapModelUserDetail author;

    @SerializedName("reply_to")
    private KapModelComment replyTo;

    @SerializedName("created_at")
    private long ts;

    public int getID() {
        return ID;
    }

    public int getPostID() {
        return postID;
    }

    public String getMessage() {
        return message;
    }

    public KapModelUserDetail getAuthor() {
        return author;
    }

    public KapModelComment getReplyTo() {
        return replyTo;
    }

    public long getTs() {
        return ts;
    }
}
