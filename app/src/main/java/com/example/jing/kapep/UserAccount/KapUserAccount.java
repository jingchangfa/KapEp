package com.example.jing.kapep.UserAccount;

import java.util.Map;

/**
 * Created by jing on 17/5/19.
 */
// 用户管理类
public class KapUserAccount {
    public int ID;
    public String token;
    public KapUserAccountHerd herd;
    //私有构造方法
    private KapUserAccount(){}
    // 创建
    public KapUserAccount(int ID, String token, Map herd){
        this.ID = ID;
        this.token = token;
        this.herd = new KapUserAccountHerd(herd);
    }
    // 存储
    public static void saveKapUserAccount(KapUserAccount kapUserAccount){
        KapUserAccountStore.save(kapUserAccount);
    }
    public void saveKapUserAccount(){
        KapUserAccountStore.save(this);
    }
    // 获取
    public static KapUserAccount loadActiveUserAccount(){
        return KapUserAccountStore.load();
    }
}
