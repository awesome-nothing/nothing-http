package io.nothing.http.volley.request.support;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.multipart.MultipartEntity;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by sanvi on 9/25/15.
 */
public class NothingMultipartSupportRequest extends Request<String> implements NothingBaseRequest {
    MultipartEntity mMultiPartEntity;
    private Response.Listener<String> mListener;
    public NetworkResponse networkResponse;

    public NothingMultipartSupportRequest(String url, Response.Listener<String> listener,
                                          Response.ErrorListener errorListener,MultipartEntity mMultiPartEntity) {
        super(Method.POST, url, errorListener);
        this.mListener = listener;
        this.mMultiPartEntity = mMultiPartEntity;
    }


    /**
     * @return
     */
    public MultipartEntity getMultiPartEntity() {
        return mMultiPartEntity;
    }

    @Override
    public String getBodyContentType() {
        return mMultiPartEntity.getContentType().getValue();
    }

    @Override
    public byte[] getBody() {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try
        {
            // 将mMultiPartEntity中的参数写入到bos中
            mMultiPartEntity.writeTo(bos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bos.toByteArray();
    }

    @Override
    protected void deliverResponse(String response) {
        try {
            mListener.onResponse(response);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            networkResponse = response;
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
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
