package com.fetherz.saim.twitterredux.utils;


import com.fetherz.saim.twitterredux.database.utils.DBFlowExclusionStrategy;
import com.google.gson.ExclusionStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by sm032858 on 3/10/17.
 */

public class JsonHelper {
    @SuppressWarnings("unchecked")
    public static <T> T GetResponseObject(String responseString, Class<T> responseClass) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setExclusionStrategies(new ExclusionStrategy[]{new DBFlowExclusionStrategy()});

        Gson gson = gsonBuilder.create();
        T responseObject = gson.fromJson(responseString, responseClass);
        return responseObject;
    }
}