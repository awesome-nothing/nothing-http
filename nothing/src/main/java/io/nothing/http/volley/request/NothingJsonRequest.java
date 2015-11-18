package io.nothing.http.volley.request;

import android.content.Context;

import com.android.volley.error.AuthFailureError;
import com.android.volley.request.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.nothing.http.volley.NothingHandler;
import io.nothing.http.volley.request.support.NothingJsonObjectRequest;
import io.nothing.http.volley.response.NothingResponse;
import io.nothing.http.volley.utils.NothingResponseHandlerUtils;

/**
 * Created by sanvi on 9/21/15.
 */
public class NothingJsonRequest {
    protected static final String PROTOCOL_CHARSET = "utf-8";
    private NothingJsonObjectRequest request;
    private NothingHandler handler;
    private Context context;
    public NothingJsonRequest(Context context, String url, int method, String json, NothingResponse response, final Map<String, String> headerParams, final NothingHandler handler){
        try {
            this.handler = handler;
            this.context = context;
            NothingResponseHandlerUtils responseHandlerUtils = new NothingResponseHandlerUtils();
            request = new NothingJsonObjectRequest(method,url,new JSONObject(json),
                    responseHandlerUtils.success(context,handler, response),
                    responseHandlerUtils.error(context,handler, response)){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> headerMap = new HashMap<>();
                    if(headerParams != null) {
                        headerMap.putAll(headerParams);
                    }
                    headerMap.putAll(handler.headers());
                    return headerMap;
                }

            };
            responseHandlerUtils.setRequest(request);
            Map<String, Object> retMap = new Gson().fromJson(json, new TypeToken<HashMap<String, Object>>() {}.getType());
            handler.request(context,request,retMap);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public JsonObjectRequest create() {
        return request;
    }




}
