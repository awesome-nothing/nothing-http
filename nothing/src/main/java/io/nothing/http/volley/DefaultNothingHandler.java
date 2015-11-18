package io.nothing.http.volley;

import android.content.Context;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.nothing.http.volley.params.NothingParam;

/**
 * Created by sanvi on 9/21/15.
 */
public class DefaultNothingHandler implements NothingHandler {
    @Override
    public void request(Context context,Request request, Map params) {

    }

    @Override
    public void response(Context context,String response, NetworkResponse networkResponse) {

    }

    @Override
    public NothingParam[] patch() {
        return new NothingParam[0];
    }

    @Override
    public JSONObject filter(JSONObject jsonObject) {
        return jsonObject;
    }

    @Override
    public String filter(String str) {
        return str;
    }

    @Override
    public Map<String, String> headers() {
        return new HashMap<String,String>();
    }
}
