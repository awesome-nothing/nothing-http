package io.nothing.http.volley;

import android.content.Context;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;

import org.json.JSONObject;

import java.util.Map;

import io.nothing.http.volley.params.NothingParam;

/**
 * 拦截器
 * Created by sanvi on 9/21/15.
 */
public interface NothingHandler {
    /**
     * 请求前拦截
     * @param request
     * @param params
     */
    public void request(Context context,Request request,Map params);

    /**
     * HTTP返回后拦截
     * @param response
     * @param networkResponse
     */
    public void response(Context context,String response,NetworkResponse networkResponse);

    /**
     * 请求参数补丁
     * @return
     */
    public NothingParam[] patch();
    /**
     * 数据格式处理
     * @return
     */
    public JSONObject filter(JSONObject jsonObject);
    /**
     * 数据格式处理
     */
    public String filter(String str);

    /**
     * headers设置拦截
     * @return
     */
    public Map<String,String> headers();
}
