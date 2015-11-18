package io.nothing.http.volley.utils;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.DownloadRequest;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import io.nothing.http.volley.NothingHandler;
import io.nothing.http.volley.request.support.NothingBaseRequest;
import io.nothing.http.volley.response.FileResponse;
import io.nothing.http.volley.response.NothingFileResponse;
import io.nothing.http.volley.response.NothingResponse;
import io.nothing.http.volley.response.StringResponse;
import io.nothing.utils.StringUtils;

/**
 * Created by sanvi on 9/28/15.
 */
public class NothingResponseHandlerUtils<T> {
    protected static final String PROTOCOL_CHARSET = "utf-8";

    private NothingBaseRequest request;

    public void setRequest(NothingBaseRequest request){
        this.request = request;
    }

    public Response.Listener success(final Context context,final NothingHandler handler, final NothingResponse nothingResponse){
        return new com.android.volley.Response.Listener<T>(){

            @Override
            public void onResponse(T response) {
                try {
                    if(response instanceof JSONObject) {
                        JSONObject jsonResponse  = (JSONObject)response;
                        handler.response(context, URLDecoder.decode(URLEncoder.encode(response.toString(), HttpHeaderParser.parseCharset(request.getNetworkResponse().headers)), "UTF-8"), request.getNetworkResponse());
                        nothingResponse.transfer(handler.filter(jsonResponse));
                    } else if(response instanceof String) {
                        String  strResponse = URLDecoder.decode(URLEncoder.encode((String) response, HttpHeaderParser.parseCharset(request.getNetworkResponse().headers)), "UTF-8");
                        handler.response(context,URLDecoder.decode(URLEncoder.encode(response.toString(), HttpHeaderParser.parseCharset(request.getNetworkResponse().headers)), "UTF-8"), request.getNetworkResponse());
                        nothingResponse.transfer(handler.filter(strResponse));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public com.android.volley.Response.ErrorListener error(final Context context,final NothingHandler handler, final NothingResponse nothingResponse) {
        return new com.android.volley.Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    String jsonString = "";
                    if(error.networkResponse.data != null){
                        jsonString =  new String(error.networkResponse.data,
                                HttpHeaderParser.parseCharset(error.networkResponse.headers, PROTOCOL_CHARSET));
                    }
                    nothingResponse.onFailure(context,error,jsonString);
                    handler.response(context,jsonString, error.networkResponse);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };
    }
}
