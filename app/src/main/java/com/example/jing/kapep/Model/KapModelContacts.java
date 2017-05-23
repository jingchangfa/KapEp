package com.example.jing.kapep.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by jing on 17/5/22.
 */

public class KapModelContacts extends KapModelBase {

    @SerializedName("id")
    private String ID;

    @SerializedName("first_name")
    private String first_name;

    @SerializedName("last_name")
    private String last_name;

    @SerializedName("emails")
    private List emails;

    @SerializedName("mobiles")
    private List mobiles;

}
