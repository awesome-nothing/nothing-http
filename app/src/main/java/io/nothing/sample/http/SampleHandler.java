package io.nothing.sample.http;

import android.content.Context;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.error.AuthFailureError;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.nothing.http.volley.NothingHandler;
import io.nothing.http.volley.params.NothingParam;

/**
 * Created by sanvi on 9/21/15.
 */
public class SampleHandler implements NothingHandler {
    private Request request;
    private Map params;

    @Override
    public void request(Context context,Request request,Map params) {
        this.request = request;
        this.params = params;
    }

    @Override
    public void response(Context context,String response, NetworkResponse networkResponse) {
        Log.d("test",request.getUrl() + " " + request.getMethod() + " " + networkResponse.statusCode
                + " " + networkResponse.networkTimeMs + " " + networkResponse.data.length + " "
        + params);

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
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("aa","xxx");
        return headers;
    }
}
