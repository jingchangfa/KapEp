package com.example.jing.kapep.Model;

import com.example.jing.kapep.Model.KapListenerAndFriend.KapModelContentBase;
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
    private KapModelContentBase author;

    @SerializedName("reply_to")
    private KapModelComment replyTo;

    @SerializedName("created_at")
    private int ts;
}
