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
    protected long lastPost;
    @SerializedName("areadyApplied")
    protected int areadyApplied;//已申请
    @SerializedName("refered")
    protected int refered;
    @SerializedName("is_friend")
    protected int isFriend;

    public int getRelationship() {
        return relationship;
    }

    public void setRelationship(int relationship) {
        this.relationship = relationship;
    }

    public int getUnread() {
        return unread;
    }

    public void setUnread(int unread) {
        this.unread = unread;
    }

    public long getLastPost() {
        return lastPost;
    }

    public void setLastPost(long lastPost) {
        this.lastPost = lastPost;
    }

    public int getAreadyApplied() {
        return areadyApplied;
    }

    public void setAreadyApplied(int areadyApplied) {
        this.areadyApplied = areadyApplied;
    }

    public int getRefered() {
        return refered;
    }

    public void setRefered(int refered) {
        this.refered = refered;
    }

    public int getIsFriend() {
        return isFriend;
    }

    public void setIsFriend(int isFriend) {
        this.isFriend = isFriend;
    }
}
