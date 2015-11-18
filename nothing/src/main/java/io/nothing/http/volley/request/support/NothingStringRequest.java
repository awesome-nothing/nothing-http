package io.nothing.http.volley.request.support;

import com.android.volley.*;
import com.android.volley.Response;
import com.android.volley.error.ParseError;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import io.nothing.http.Result;

/**
 * Created by sanvi on 9/21/15.
 */
public class NothingStringRequest extends StringRequest implements NothingBaseRequest{

    public NetworkResponse networkResponse;
    public NothingStringRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            this.networkResponse = response;
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    public NetworkResponse getNetworkResponse() {
        return networkResponse;
    }
}
