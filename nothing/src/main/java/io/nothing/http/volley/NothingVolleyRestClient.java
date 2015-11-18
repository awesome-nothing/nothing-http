package io.nothing.http.volley;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.multipart.*;

import java.util.Map;

import io.nothing.BuildConfig;
import io.nothing.http.volley.params.NothingFileParam;
import io.nothing.http.volley.params.NothingFilesParam;
import io.nothing.http.volley.request.NothingUploadRequest;
import io.nothing.http.volley.response.NothingFileResponse;
import io.nothing.http.volley.params.NothingParam;
import io.nothing.http.volley.response.NothingResponse;
import io.nothing.http.volley.request.NothingDownloadRequest;
import io.nothing.http.volley.request.NothingJsonRequest;
import io.nothing.http.volley.request.NothingRequest;

/**
 * Created by sanvi on 9/17/15.
 */
public class NothingVolleyRestClient {
    private static String TAG = NothingVolleyRestClient.class.getSimpleName();
    private static int TIMEOUT = 300 * 1000;
    private static int CONNECTION_TIMEOUT = 15 * 100;
    protected static final String PROTOCOL_CHARSET = "utf-8";
    private static final String HEADER_COOKIE = "Cookie";
    DefaultRetryPolicy policy;
    private boolean isDebug;

    private NothingHandler handler;
    private RequestQueue queue;

    public void setShoulaCache(boolean shoulaCache) {
        this.shoulaCache = shoulaCache;
    }

    private boolean shoulaCache;

    public NothingVolleyRestClient(Context context) {
        init(context, new DefaultNothingHandler());
    }


    public void setDebug(boolean isDebug){
        this.isDebug = isDebug;
    }


    public NothingVolleyRestClient(Context context, NothingHandler handler) {
        init(context, handler);
    }

    private Request request(Request request){
        request.setRetryPolicy(policy);
        request.setShouldCache(shoulaCache);
        queue.add(request);
        return request;
    }

    private void init(Context context, NothingHandler handler) {
        this.handler = handler;
        policy = new DefaultRetryPolicy(TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        queue = Volley.newRequestQueue(context);
    }

    public void setTimeout(int timeout) {
        policy = new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    }

    /**
     * 发送一个POST请求，请求Content-Type: json
     * @param url
     * @param response
     * @param json
     */
    public Request postJSON(Context context,String url, NothingResponse response, String json) {
        return jsonRequest(context, Request.Method.POST, url, response, null, json);
    }

    /**
     * 发送一个PUT请求，请求Content-Type: json
     * @param url
     * @param response
     * @param json
     */
    public Request putJson(Context context,String url, NothingResponse response, String json) {
        return jsonRequest(context, Request.Method.PUT, url, response, null, json);
    }

    /**
     * 发送一个DELETE请求，请求Content-Type: json
     * @param url
     * @param response
     * @param json
     */
    public Request deleteJson(Context context,String url, NothingResponse response, String json) {
        return jsonRequest(context, Request.Method.DELETE, url, response, null, json);
    }

    /**
     * 发送一个json请求
     * @param method
     * @param url
     * @param response
     * @param headerParams
     * @param json
     */
    public Request jsonRequest(Context context,int method, String url, NothingResponse response, Map<String, String> headerParams, String json) {
        return request(new NothingJsonRequest(context, url, method, json, response, headerParams, handler).create());
    }

    /**
     * 发送POST请求
     * @param url
     * @param response
     * @param params
     */
    public Request post(Context context,String url, NothingResponse response, final NothingParam... params) {
        return post(context, url, response, null, params);
    }


    /**
     * 发送GET请求
     * @param url
     * @param response
     * @param params
     */
    public Request get(Context context,String url, NothingResponse response,final NothingParam... params) {
        return get(context, url, response, null, params);
    }

    /**
     * 发送delete请求
     * @param url
     * @param response
     * @param params
     */
    public Request delete(Context context,String url, NothingResponse response, final NothingParam... params) {
        return delete(context, url, response, null, params);
    }

    /**
     * 发送put请求
     * @param url
     * @param response
     * @param params
     */
    public Request put(Context context,String url, NothingResponse response, final NothingParam... params) {
        return put(context, url, response, null, params);
    }

    /**
     * 发送POST
     * @param url
     * @param response
     * @param headerParams
     * @param params
     */
    public Request put(Context context,String url, NothingResponse response,final Map<String, String> headerParams,final NothingParam... params) {
        return request(new NothingRequest(context,url, Request.Method.PUT, response, headerParams, handler, params).create());
    }


    /**
     * 发送DELETE
     * @param url
     * @param response
     * @param headerParams
     * @param params
     */
    public Request delete(Context context,String url, NothingResponse response,final Map<String, String> headerParams,final NothingParam... params) {
        return request(new NothingRequest(context,url,Request.Method.DELETE,response,headerParams,handler,params).create());
    }

    /**
     * 发送GET
     * @param url
     * @param response
     * @param headerParams
     * @param params
     */
    public Request get(Context context,String url, NothingResponse response,final Map<String, String> headerParams,final NothingParam... params) {
       return request(new NothingRequest(context,url, Request.Method.GET, response, headerParams, handler, params).create());
    }
    /**
     * 发送POST
     * @param url
     * @param response
     * @param headerParams
     * @param params
     */
    public Request post(Context context,String url, NothingResponse response,final Map<String, String> headerParams,final NothingParam... params) {
        return request(new NothingRequest(context,url,Request.Method.POST,response,headerParams,handler,params).create());
    }

    /**
     * 下载一个文件
     * @param url
     * @param path
     * @param nothingFileResponse
     */
    public Request download(Context context,String url, final String path, final NothingFileResponse nothingFileResponse) {
        return request(new NothingDownloadRequest(context,url,path,nothingFileResponse,handler).create());
    }

    /**
     * 上传一个文件
     * @param url
     * @param response
     * @param params
     */
    public Request upload(Context context,String url,final NothingResponse response, NothingParam... params) {
        com.android.volley.toolbox.multipart.MultipartEntity mMultiPartEntity = new MultipartProgressEntity();
        for(NothingParam param: params) {
            if(param instanceof NothingFileParam) {
                NothingFileParam fileParam = (NothingFileParam) param;
                mMultiPartEntity.addPart(new FilePart(fileParam.getName(),fileParam.getValue(),fileParam.getName(),null) );
            }
            if(param instanceof NothingFilesParam){
                NothingFilesParam filesParam = (NothingFilesParam) param;
                for(int i=0;i<filesParam.getValue().size();i++) {
                    mMultiPartEntity.addPart(new FilePart(filesParam.getName(),filesParam.getValue().get(i),filesParam.getName(),null) );
                }
            }
        }
        return request(new NothingUploadRequest(context,url,response,handler,mMultiPartEntity).create());
    }
}
