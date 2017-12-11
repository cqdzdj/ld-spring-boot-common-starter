package com.lyldj.springboot.common.utils;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

/**
 * json string to object and object to json string
 */
public class JsonUtils {

    public static String objectToJson(Object object) {
        Gson gson = new Gson();
        String json = gson.toJson(object);
        return json;
    }

    public static <T> T jsonToObject(String json, Class<T> clazz){
        Gson gson = new Gson();
        return gson.fromJson(json, clazz);
    }

    public static <T> T jsonToObject(String json, TypeToken<T> typeToken){
        Gson gson = new Gson();
        return gson.fromJson(json, typeToken.getType());
    }

}
