package io.nothing.http.volley.request.support;

import android.text.TextUtils;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.request.DownloadRequest;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by sanvi on 9/21/15.
 */
public class NothingDownloadSupportRequest extends DownloadRequest implements NothingBaseRequest {
    public NetworkResponse networkResponse;

    /**
     * Creates a new request with the given method.
     *
     * @param url           URL to fetch the string at
     * @param downloadPath
     * @param listener      Listener to receive the String response
     * @param errorListener Error listener, or null to ignore errors
     */
    public NothingDownloadSupportRequest(String url, String downloadPath, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(url, downloadPath, listener, errorListener);
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String parsed = null;
        try {
            this.networkResponse = response;
            byte[] data = response.data;
            //convert array of bytes into file
            FileOutputStream fileOuputStream = new FileOutputStream(mDownloadPath);
            fileOuputStream.write(data);
            fileOuputStream.close();
            parsed = mDownloadPath;
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }  catch (IOException e) {
            e.printStackTrace();
        } finally{
            if(TextUtils.isEmpty(parsed)){
                parsed = "";
            }
        }

        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    public NetworkResponse getNetworkResponse() {
        return networkResponse;
    }
}
