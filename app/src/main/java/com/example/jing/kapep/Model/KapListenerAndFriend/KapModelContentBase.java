package com.example.jing.kapep.Model.KapListenerAndFriend;

import com.example.jing.kapep.Model.KapModelBase;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

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
    protected long career_id;

    // 判断是同一个人
    public  boolean isSameUser(KapModelContentBase user){
        return true;
    }


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPortrait_url() {
        return portrait_url;
    }

    public void setPortrait_url(String portrait_url) {
        this.portrait_url = portrait_url;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public long getCareer_id() {
        return career_id;
    }

    public void setCareer_id(long career_id) {
        this.career_id = career_id;
    }
}
