package io.nothing.utils;

import org.json.JSONObject;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


/**
 * 用于数据存储
 * @author sanvi
 * 
 */
public class SharedPreferencesUtils {

	private static SharedPreferencesUtils instance;
  private static SharedPreferencesUtils customerInstance;

	public static SharedPreferencesUtils getInstance(Context context) {
		if (instance == null) {
			instance = new SharedPreferencesUtils(context);
		}
		return instance;
	}

  public static SharedPreferencesUtils getInstance(Context context,String userId) {
    if (customerInstance == null) {
      customerInstance = new SharedPreferencesUtils(context,userId);
    }
    return customerInstance;
  }

  public static void destroy() {
    customerInstance = null;
    instance = null;
  }




	private SharedPreferences settings;
	

	public SharedPreferencesUtils(Context context) {
		settings = PreferenceManager.getDefaultSharedPreferences(context);
	}

  public SharedPreferencesUtils(Context context, String userId) {
    settings = context.getSharedPreferences(userId, 0);
  }


	public void set(String key, String value) {
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(key, value);
		editor.commit();
	}

  public void clear(){
    SharedPreferences.Editor editor = settings.edit();
    editor.clear();
    editor.commit();
  }


  public void set(String key, int value) {
    SharedPreferences.Editor editor = settings.edit();
    editor.putInt(key, value);
    editor.commit();
  }


  public void set(String key, long value) {
    SharedPreferences.Editor editor = settings.edit();
    editor.putLong(key, value);
    editor.commit();
  }

  public void set(String key, boolean value) {
    SharedPreferences.Editor editor = settings.edit();
    editor.putBoolean(key, value);
    editor.commit();
  }



  public void remove(String key){
		SharedPreferences.Editor editor = settings.edit();
		editor.remove(key);
		editor.commit();
	}
	
	public String get(String key) {
		return settings.getString(key, null);
	}

  public int getInt(String key) {
    return settings.getInt(key, 0);
  }

  public Long getLong(String key) {
    return settings.getLong(key,0);
  }

  public Boolean getBoolean(String key) {
    return settings.getBoolean(key,false);
  }
}
