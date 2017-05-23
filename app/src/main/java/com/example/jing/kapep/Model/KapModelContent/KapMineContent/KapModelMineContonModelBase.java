package com.example.jing.kapep.Model.KapModelContent.KapMineContent;

import com.example.jing.kapep.Model.KapModelContent.KapOtherContent.KapModelOtherConton;
import com.google.gson.annotations.SerializedName;

/**
 * Created by jing on 17/5/22.
 */

public abstract class KapModelMineContonModelBase extends KapModelOtherConton{
    @SerializedName("imagesData")
    protected byte[] imagesData;

    /**
     * listenData 不会存在于model中
     * 只是用他的set方法 来生成 listenDataFilePath
     */
    protected byte[] listenData;

    @SerializedName("listenDataFilePath")
    protected String listenDataFilePath;
}
