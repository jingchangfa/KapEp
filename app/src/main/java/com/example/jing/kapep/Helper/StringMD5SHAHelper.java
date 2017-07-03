package com.example.jing.kapep.Helper;

import android.util.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by jing on 17/5/15.
 * 字符串加密相关
 */

public class StringMD5SHAHelper {
//    private static String key = "a6U&1$Ip[Jr/sed]Rfvn=O>Mz+}lXN*%-gLcGD|0";
    public static String getSHA(String val){
        return getSHA512(val);
    }
    public static String getSHA512(String val){
        byte[] m = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("SHA-512");
            md5.update(val.getBytes());
            m = md5.digest();//加密
        }catch (NoSuchAlgorithmException e){
        }
        return getString(m);
    }
    private static String getString(byte[] b){
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs;
    }
}
