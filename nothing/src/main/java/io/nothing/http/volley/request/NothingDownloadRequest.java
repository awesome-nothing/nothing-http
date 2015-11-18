package io.nothing.http.volley.request;

import android.content.Context;
import android.util.Log;

import com.android.volley.error.VolleyError;
import com.android.volley.request.DownloadRequest;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;

import io.nothing.http.volley.request.support.NothingDownloadSupportRequest;
import io.nothing.http.volley.response.NothingFileResponse;
import io.nothing.http.volley.NothingHandler;
import io.nothing.http.volley.utils.NothingResponseHandlerUtils;

/**
 * Created by sanvi on 9/21/15.
 */
public class NothingDownloadRequest {
    protected static final String PROTOCOL_CHARSET = "utf-8";
    private final NothingDownloadSupportRequest request;
    private Context context;
    private NothingHandler handler;

    public NothingDownloadRequest(Context context,String url, String path, final NothingFileResponse response, final NothingHandler handler) {
        this.handler = handler;
        this.context = context;
        NothingResponseHandlerUtils responseHandlerUtils = new NothingResponseHandlerUtils();
        request = new NothingDownloadSupportRequest(url, path, responseHandlerUtils.success(context,handler,response), responseHandlerUtils.error(context,handler,response));
        request.setOnProgressListener(new com.android.volley.Response.ProgressListener() {
            @Override
            public void onProgress(long transferredBytes, long totalSize) {
                response.onProgress(transferredBytes, totalSize);
            }
        });
        responseHandlerUtils.setRequest(request);
        handler.request(context,request, new HashMap());
    }

    public DownloadRequest create() {
        return request;
    }

}
