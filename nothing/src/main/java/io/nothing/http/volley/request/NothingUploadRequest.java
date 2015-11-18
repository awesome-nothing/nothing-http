package io.nothing.http.volley.request;

import android.content.Context;
import android.util.Log;

import com.android.volley.error.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.multipart.MultipartEntity;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;

import io.nothing.http.volley.NothingHandler;
import io.nothing.http.volley.request.support.NothingMultipartSupportRequest;
import io.nothing.http.volley.response.NothingResponse;

/**
 * Created by sanvi on 9/21/15.
 */
public class NothingUploadRequest {
    protected static final String PROTOCOL_CHARSET = "utf-8";
    private final NothingMultipartSupportRequest request;
    private Context context;
    private NothingHandler handler;

    public NothingUploadRequest(Context context,String url, final NothingResponse response, final NothingHandler handler,MultipartEntity entity) {
        this.handler = handler;
        this.context = context;
        request = new NothingMultipartSupportRequest(url, successResponseHandler(response), errorResponseHandler(response),entity);
        handler.request(context,request, new HashMap());
    }

    public NothingMultipartSupportRequest create() {
        return request;
    }


    private com.android.volley.Response.Listener successResponseHandler(final NothingResponse nothingResponse) {
        return new com.android.volley.Response.Listener<String>(){

            @Override
            public void onResponse(String response) {
                Log.d("response", "body：" + response.toString());
                try {
                    handler.response(context,response, request.networkResponse);
                    nothingResponse.transfer(handler.filter(new JSONObject(URLDecoder.decode(URLEncoder.encode(response, HttpHeaderParser.parseCharset(request.networkResponse.headers)), "UTF-8"))));
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        };
    }

    private com.android.volley.Response.ErrorListener errorResponseHandler(final NothingResponse nothingResponse) {
        return new com.android.volley.Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("response", "error");
                try {
                    String jsonString = new String(error.networkResponse.data,
                            HttpHeaderParser.parseCharset(error.networkResponse.headers, PROTOCOL_CHARSET));
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
