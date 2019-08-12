
package com.liuzr.ancient.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Observable;

public class BasePrefs extends Observable {

  protected Context context;
  private final static String PREFS_NAME = "PREFS_NAME";
  private static final String KEY_TIME_MODIFIED = "timeModified";

  protected final SharedPreferences preferences;

  public BasePrefs(Context context, final String prefName) {
    this.context = context;
    this.preferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
  }

  protected String getString(String key, String defaultValue) {
    return preferences.getString(key, defaultValue);
  }

  protected Integer getInt(String key, int defaultValue) {
    return preferences.getInt(key, defaultValue);
  }

  protected void setInt(String key, int value) {
    SharedPreferences.Editor editor = preferences.edit();
    editor.putInt(key, value);
    editor.putLong(KEY_TIME_MODIFIED, System.currentTimeMillis());
    editor.apply();
  }

  protected void setBoolean(String key, boolean value) {
    SharedPreferences.Editor editor = preferences.edit();
    editor.putBoolean(key, value);
    editor.putLong(KEY_TIME_MODIFIED, System.currentTimeMillis());
    editor.apply();
  }

  protected boolean getBoolean(String key, boolean defaultValue) {
    return preferences.getBoolean(key, defaultValue);
  }


  protected long getLong(String key, long defaultValue) {
    return preferences.getLong(key, defaultValue);
  }

  public void clear() {
    SharedPreferences.Editor editor = preferences.edit();
    editor.clear();
    editor.apply();
  }
}
