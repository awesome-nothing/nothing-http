package io.nothing.http.volley.response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import io.nothing.http.Result;

/**
 * Created by sanvi on 10/28/15.
 */
public class JsonResponse implements Result {

    public JSONObject object;

    public JsonResponse(String str) {
        try {
            object = new JSONObject(str);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JsonResponse() {
    }

    public JsonResponse(JSONObject jsonObject) {
        this.object = jsonObject;
    }

    @Override
    public <T extends Result> List<T> transfer(JSONArray jsonArray) {
        return null;
    }

    @Override
    public <T extends Result> T transfer(JSONObject jsonObject) {
        this.object = jsonObject;
        return (T) this;
    }

    @Override
    public <T extends Result> T transfer(String str) {
        try {
            this.object = new JSONObject(str);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return (T)this;
    }
}
