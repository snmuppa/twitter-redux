package com.fetherz.saim.twitterredux.database.utils;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.raizlabs.android.dbflow.structure.ModelAdapter;

/**
 * If you intend to use DBFlow with the Gson library, you may get StackOverflowError exceptions when trying to use Java objects
 * that extend from BaseModel. In order to avoid these issues, you need to exclude the ModelAdapter class, which is a field
 * included with BaseModel
 * author Sai Muppa
 */
public class DBFlowExclusionStrategy implements ExclusionStrategy {

    // Otherwise, Gson will go through base classes of DBFlow models
    // and hang forever.
    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        return f.getDeclaredClass().equals(ModelAdapter.class);
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }
}
