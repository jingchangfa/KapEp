package com.example.jing.kapep.UserAccount;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jing on 17/5/19.
 */
// 附加的上传信息
public class KapUserAccountHerd {
    public int ID;
    public String address;
    public String upload;
    public String image;
    //私有构造方法
    private KapUserAccountHerd(){}
    public KapUserAccountHerd(Map herd){
        this.ID = (int)herd.get("id");
        this.address = (String) herd.get("address");
        this.upload = (String)herd.get("upload");
        this.image = (String)herd.get("image");
    }
}
