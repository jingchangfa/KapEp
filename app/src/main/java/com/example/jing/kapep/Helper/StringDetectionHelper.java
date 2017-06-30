package com.example.jing.kapep.Helper;

/**
 * Created by jing on 2017/6/30.
 */

public class StringDetectionHelper {
    public static boolean isAuthorizedNumberString(String numberString){
        if (numberString == null||"".equals(numberString)){
            return false;
        }
        return true;
    }
    public static boolean isAuthorizedVerificationString(String verificationString){
        if (verificationString == null||"".equals(verificationString)){
            return false;
        }
        return true;
    }
}
