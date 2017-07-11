package com.example.jing.kapep.Model.KapModelContent.KapOtherContent;

import com.example.jing.kapep.Model.KapModelContent.KapMineContent.KapModelMineServerContonModel;
import com.example.jing.kapep.Model.KapModelContent.KapModelContonBase;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by jing on 17/5/22.
 */

public class KapModelOtherConton extends KapModelContonBase {
    @SerializedName("images")
    protected List imagesArray;
    @SerializedName("audio")
    protected String audioURLString;

    public List getImagesArray() {
        return imagesArray;
    }

    public String getAudioURLString() {
        return audioURLString;
    }

    //KapModelOtherConton转化为KapModelMineServerContonModel
    public KapModelMineServerContonModel otherModelChangeMineModel(){
        // 所有的转化操作放在一起，装饰者类里面，比较好把
        return new KapModelMineServerContonModel();
    }
}
