package com.example.jing.kapep.Model.KapListenerAndFriend;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jing on 17/5/22.
 */

public class KapModelUserDetail extends  KapModelContentBase {
    @SerializedName("country")
    protected String country;
    @SerializedName("mobile")
    protected String mobile;
    @SerializedName("email")
    protected String email;
    @SerializedName("email_status")
    protected  int emailStatus;
    @SerializedName("herd_id")
    protected  int herdID;
    @SerializedName("unread_messages")
    protected  int unreadMessages;
    @SerializedName("new_audiences")
    protected  int unreadAdiences;
}
