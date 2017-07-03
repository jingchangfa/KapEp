package com.example.jing.kapep.Manager;

/**
 * Created by jing on 2017/7/3.
 * 定位管理
 */

public class KapLocationManager {
    public interface LocationListener{
        void successResult(String country,String locationString);
        void failureResult(String errorMsg);
    }
    public static void UsersLocation(LocationListener listener){
    }

}
