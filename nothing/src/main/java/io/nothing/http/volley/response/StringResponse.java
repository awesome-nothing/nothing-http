package io.nothing.http.volley.response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import io.nothing.http.Result;

/**
 * Created by sanvi on 9/28/15.
 */
public class StringResponse implements Result {
    public String str;
    public StringResponse() {

    }
    public StringResponse(String str) {
        this.str = str;
    }
    @Override
    public <T extends Result> List<T> transfer(JSONArray jsonArray) {
        return null;
    }

    @Override
    public <T extends Result> T transfer(JSONObject jsonObject) {
        str = jsonObject.toString();
        return (T) this;
    }

    @Override
    public <T extends Result> T transfer(String str) {
        this.str = str;
        return (T) this;
    }
}
