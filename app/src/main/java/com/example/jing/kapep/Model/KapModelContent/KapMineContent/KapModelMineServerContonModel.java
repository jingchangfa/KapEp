package com.example.jing.kapep.Model.KapModelContent.KapMineContent;

import com.example.jing.kapep.Model.KapModelContent.KapOtherContent.KapModelOtherConton;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by jing on 17/5/22.
 */

public class KapModelMineServerContonModel extends KapModelMineContonModelBase {
    @SerializedName("images")
    protected List imagesArray;
    @SerializedName("audio")
    protected String audioURLString;
    // 通过网络数据更新自己的model
    public void updateByKapModelOtherConton(KapModelOtherConton modelOtherConton){
        //这个用装饰者模式比较好
    }
}
