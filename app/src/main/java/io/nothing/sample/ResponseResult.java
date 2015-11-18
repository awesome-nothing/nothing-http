package io.nothing.sample;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import io.nothing.http.Result;

/**
 * Created by sanvi on 9/17/15.
 */
public class ResponseResult implements Result{
    public int code;
    public String msg;

    @Override
    public <T extends Result> List<T> transfer(JSONArray jsonArray) {
        return JSONConvert.transfer(jsonArray, this.getClass());
    }

    @Override
    public <T extends Result> T transfer(JSONObject jsonObject) {
        return JSONConvert.transfer(jsonObject, this.getClass());
    }

    @Override
    public <T extends Result> T transfer(String str) {
        return null;
    }
}
