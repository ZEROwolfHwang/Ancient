package com.liuzr.ancient.prefs;

import android.content.Context;

import com.liuzr.ancient.R;


public class UserPrefs extends BasePrefs {

  private static UserPrefs mUserPrefs;

  public static UserPrefs getInstance(Context context) {
    if (mUserPrefs == null) {
      mUserPrefs = new UserPrefs(context);
    }
    return mUserPrefs;
  }

  public final static String PREFS_NAME = "userPrefs";

  public UserPrefs(Context context) {
    super(context, PREFS_NAME);
  }

  private final static String KEY_VERTICAL_WRITE = "vertical_write";

  public void setVerticalWrite(boolean verticalWrite) {
    setBoolean(KEY_VERTICAL_WRITE, verticalWrite);
  }

  public boolean getVerticalWrite() {
    return getBoolean(KEY_VERTICAL_WRITE, true);
  }

  private final static String KEY_GLOBAL_BACKGROUND_COLOR_RES = "global_background_color_res";

  public void setBackgroundColor(int colorRes) {
    setInt(KEY_GLOBAL_BACKGROUND_COLOR_RES, colorRes);
  }

  public int getBackgroundColor() {
    return getInt(KEY_GLOBAL_BACKGROUND_COLOR_RES, R.color.normal_bg);
  }

}
