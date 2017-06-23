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

    public String getCountry() {
        return country;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }

    public int getEmailStatus() {
        return emailStatus;
    }

    public int getHerdID() {
        return herdID;
    }

    public int getUnreadMessages() {
        return unreadMessages;
    }

    public int getUnreadAdiences() {
        return unreadAdiences;
    }

    // 辅助方法
    public String sexToString(){
        return this.sex == 0?"女":"男";
    }
    public static int StringToSex(String string){
        return string.equals("女")?0:1;
    }
    public String occToString(){
        return "户外运动";
    }
}
