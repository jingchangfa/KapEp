package com.example.jing.kapep.Model.KapModelContent;

import com.example.jing.kapep.Model.KapListenerAndFriend.KapModelUserDetail;
import com.example.jing.kapep.Model.KapModelBase;
import com.google.gson.annotations.SerializedName;

/**
 * Created by jing on 17/5/22.
 */

public abstract class KapModelContonBase extends KapModelBase {
    @SerializedName("id")
    protected int ID;

    @SerializedName("created_at")
    protected int createdTime;

    @SerializedName("user")
    protected KapModelUserDetail userModel;

    @SerializedName("comments_count")
    protected int commentsCount;

    @SerializedName("likes_count")
    protected int likesCount;

    @SerializedName("is_like")
    protected int isLike;
}
