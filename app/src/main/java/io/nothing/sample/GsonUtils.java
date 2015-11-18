package io.nothing.sample;

import com.google.gson.Gson;

/**
 * Created by sanvi on 9/17/15.
 */
public class GsonUtils {

    private static GsonUtils instance = null;
    private static Gson gson;

    public static GsonUtils getInstance() {
        if (instance == null) {
            instance = new GsonUtils();
            gson = new Gson();
//      gson = new GsonBuilder().setExclusionStrategies(new SpecificClassExclusionStrategy(null, Model.class)).create();
        }
        return instance;
    }

    public Gson getGson() {
        return gson;
    }




}

