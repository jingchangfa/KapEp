package com.example.jing.kapep.Model.KapListenerAndFriend;

import com.example.jing.kapep.Model.KapModelBase;
import com.google.gson.annotations.SerializedName;

/**
 * Created by jing on 17/5/22.
 */

public abstract class KapModelContentBase extends KapModelBase {
    @SerializedName("id")
    protected int ID;
    @SerializedName("name")
    protected String name;
    @SerializedName("portrait_url")
    protected String portrait_url;

    @SerializedName("sex")
    protected int sex;
    @SerializedName("location")
    protected String location;
    @SerializedName("career_id")
    protected int career_id;

    // 判断是同一个人
    public  boolean isSameUser(KapModelContentBase user){
        return true;
    }
}
