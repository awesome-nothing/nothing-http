package io.nothing.http.volley.response;

import android.content.Context;

import com.android.volley.error.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.nothing.http.Result;
import io.nothing.http.volley.Response;


public abstract class NothingResponse<T extends Result> implements Response {

  protected void transfer(JSONArray jsonArray) throws Exception {
    onSuccess((List<T>) getActualClass().transfer(jsonArray));
  }

  private Type getType() {
    Type mySuperClass = this.getClass().getGenericSuperclass();
    return ((ParameterizedType) mySuperClass).getActualTypeArguments()[0];
  }

  protected  <T extends Result> T getActualClass() throws Exception {
    Result cls = null;
    try {
      String clsName = getType().toString().replace("class ", "").trim();
      cls = (io.nothing.http.Result) Class.forName(clsName).newInstance();
    } catch (ClassNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return (T) cls;

  }


  public void transfer(JSONObject jsonObject) throws Exception {
    onSuccess((T) getActualClass().transfer(jsonObject));
  }

  public void transfer(String str) throws Exception {
    onSuccess((T) getActualClass().transfer(str));
  }


  public void onSuccess(List<T> response) {

  }

  public void onSuccess(T response){

  }

  public void onFailure(Context context,VolleyError error,String string){

  }

  public void onFailure(T response){

  }

}
