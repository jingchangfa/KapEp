package com.example.jing.kapep.Manager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.xml.transform.Result;

/**
 * Created by jing on 17/5/22.
 */
// gson的管理类
public class KapGsonManager {
    private KapGsonManager(){}
    private static KapGsonManager shareGsonManager = new KapGsonManager();
    private Gson gson = new Gson();

    // model
    public static String KapModelToJson(Object o){
        if (o == null) return "";
        return shareGsonManager.gson.toJson(o);
    }
    public static <T> T KapJsonToModel(String jsonString,Class<T> modelClass){
        if (jsonString == null) return null;
        return shareGsonManager.gson.fromJson(jsonString,modelClass);
    }

    // modelList
    public static String KapModelsToJson(List modelList){
        if (modelList == null) return "";
        return shareGsonManager.gson.toJson(modelList);
    }
//    public static <T> List<T> KapJsonToModels(String jsonString,Class<T> modelClass){
//        Type listType = new TypeToken<List<T>>(){}.getType();
//        List<T> modelList = shareGsonManager.gson.fromJson(jsonString,listType);
//        String name = modelList.get(0).getClass().toString();
//        if(modelList == null) return null;
//        return modelList;
//    }
    // http://www.jianshu.com/p/d62c2be60617 泛型分析链接
    public static <T> List<T> KapJsonToModels(String jsonString,Class<T> modelClass){
//        Type listType = new TypeToken<List<T>>(){}.getType();
        Type listType = new ParameterizedTypeImpl(List.class,new Class[]{modelClass});
        List<T> modelList = shareGsonManager.gson.fromJson(jsonString,listType);
        String name = modelList.get(0).getClass().toString();
        if(modelList == null) return null;
        return modelList;
    }

    public static class ParameterizedTypeImpl implements ParameterizedType {
        private final Class raw;
        private final Type[] args;
        public ParameterizedTypeImpl(Class raw, Type[] args) {
            this.raw = raw;
            this.args = args != null ? args : new Type[0];
        }
        @Override
        public Type[] getActualTypeArguments() {
            return args;
        }
        @Override
        public Type getRawType() {
            return raw;
        }
        @Override
        public Type getOwnerType() {return null;}
    }
}
