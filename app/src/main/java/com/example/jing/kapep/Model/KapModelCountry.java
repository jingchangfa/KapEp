package com.example.jing.kapep.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jing on 17/5/22.
 */

public class KapModelCountry extends KapModelBase {
    @SerializedName("area_code")
    private String numberString;

    @SerializedName("region_name")
    private String countryNameString;

}
