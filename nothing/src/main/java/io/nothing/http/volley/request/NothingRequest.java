package io.nothing.http.volley.request;

import android.content.Context;
import android.util.Log;

import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import io.nothing.http.volley.NothingHandler;
import io.nothing.http.volley.params.NothingParam;
import io.nothing.http.volley.request.support.NothingStringRequest;
import io.nothing.http.volley.response.NothingResponse;
import io.nothing.http.volley.params.NothingStringParam;
import io.nothing.http.volley.utils.NothingResponseHandlerUtils;

/**
 * Created by sanvi on 9/21/15.
 */
public class NothingRequest {
    protected static final String PROTOCOL_CHARSET = "utf-8";
    private final NothingHandler handler;
    private NothingStringRequest request;
    private Context context;

    protected Map<String, String> stripNulls(NothingHandler handler,NothingParam... nothingParams) {
        Map<String, String> params = new HashMap<String, String>();
        stripNulls(params,nothingParams);
        //patch params
        if(handler != null) {
            stripNulls(params, handler.patch());
        }
        return params;
    }

    private Map<String,String> stripNulls(Map<String,String> requestParams, NothingParam[] nothingParams) {
        if (nothingParams != null) {
            for (int i = 0; i < nothingParams.length; i++) {
                NothingParam param = nothingParams[i];
                if (param.getValue() != null) {
                    if (param instanceof NothingStringParam) {
                        NothingStringParam nothingParam = (NothingStringParam) param;
                        requestParams.put(nothingParam.getName(), nothingParam.getValue());
                    }

                }
            }
        }
        return requestParams;
    }

    public NothingRequest(Context context,String url, int method,NothingResponse response,final Map<String, String> headerParams, final NothingHandler handler,final NothingParam... params){
        this.handler = handler;
        this.context = context;
        NothingResponseHandlerUtils responseHandlerUtils = new NothingResponseHandlerUtils();
        request = new NothingStringRequest(method,url,responseHandlerUtils.success(context,handler,response),
                responseHandlerUtils.error(context,handler,response)){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return stripNulls(handler,params);
            }

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
        handler.request(context,request,stripNulls(handler,params));
    }

    public NothingStringRequest create() {
        return request;
    }

}
