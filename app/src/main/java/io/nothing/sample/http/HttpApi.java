package io.nothing.sample.http;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import io.nothing.http.volley.response.NothingFileResponse;
import io.nothing.http.volley.response.NothingResponse;
import io.nothing.http.volley.params.NothingStringParam;
import io.nothing.http.volley.NothingVolleyRestClient;
import io.nothing.sample.FileUtils;
import io.nothing.sample.GsonUtils;
import io.nothing.sample.LoginResponse;
import io.nothing.sample.Request;
import io.nothing.sample.UnreadResponse;
import io.nothing.sample.VersionRequest;
import io.nothing.sample.VersionResponse;


/**
 * Created by sanvi on 9/17/15.
 */
public class HttpApi {

    protected NothingVolleyRestClient mClient;
    protected String mApiBaseUrl;
    private static HttpApi instance = null;

    public HttpApi(Context context) {
        mClient = new NothingVolleyRestClient(context, new SampleHandler());
    }

    public NothingVolleyRestClient getClient(){
        return mClient;
    }

    public static HttpApi getInstance(Context context){
        if(instance == null) {
            instance = new HttpApi(context);
        }
        return instance;
    }


}
