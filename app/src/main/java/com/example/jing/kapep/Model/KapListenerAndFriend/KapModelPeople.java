package com.example.jing.kapep.Model.KapListenerAndFriend;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jing on 17/5/22.
 */

public class KapModelPeople extends  KapModelContentBase{
    // 关系枚举
    public static final int people_relationship_stranger = 0;
    public static final int people_relationship_audience = 1;
    public static final int people_relationship_friends = 2;

    //
    @SerializedName("relationship")
    protected int relationship;
    @SerializedName("unread")
    protected int unread;
    @SerializedName("last_post")
    protected int lastPost;
//    @SerializedName("areadyApplied")
//    protected int areadyApplied;
    @SerializedName("refered")
    protected int refered;
    @SerializedName("is_friend")
    protected int isFriend;

}
