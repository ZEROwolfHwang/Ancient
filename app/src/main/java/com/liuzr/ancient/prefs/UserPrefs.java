/*
 * Created by wingjay on 11/16/16 3:32 PM
 * Copyright (c) 2016.  All rights reserved.
 *
 * Last modified 11/10/16 11:05 AM
 *
 * Reach me: https://github.com/wingjay
 * Email: yinjiesh@126.com
 */

package com.liuzr.ancient.prefs;

import android.content.Context;
import androidx.annotation.Nullable;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.liuzr.ancient.R;
import com.liuzr.ancient.bean.ImagePoem;

/**
 * Created by wingjay on 10/4/15.
 */
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

  private final static String KEY_HOME_IMAGE_POEM = "key_home_image_poem";

  public void setHomeImagePoem(boolean homeImagePoem) {
    setBoolean(KEY_HOME_IMAGE_POEM, homeImagePoem);
  }

  public boolean getHomeImagePoemSetting() {
    return getBoolean(KEY_HOME_IMAGE_POEM, true);
  }

  private final static String KEY_NEXT_FETCH_HOME_IMAGE_POEM_TIME = "key_next_fetch_home_image_poem_time";

  public boolean canFetchNextHomeImagePoem() {
    return (System.currentTimeMillis() / 1000) >= getLong(KEY_NEXT_FETCH_HOME_IMAGE_POEM_TIME, 0);
  }

  private final static String KEY_LAST_HOME_IMAGE_POEM = "key_last_home_image_poem";

  @Nullable
  public ImagePoem getLastHomeImagePoem() {
    String imagePoemString = getString(KEY_LAST_HOME_IMAGE_POEM, null);
    if (TextUtils.isEmpty(imagePoemString)) {
      return null;
    }
    Gson gson = new Gson();
    return gson.fromJson(imagePoemString, ImagePoem.class);
  }

  private final static String KEY_GLOBAL_BACKGROUND_COLOR_RES = "global_background_color_res";

  public void setBackgroundColor(int colorRes) {
    setInt(KEY_GLOBAL_BACKGROUND_COLOR_RES, colorRes);
  }

  public int getBackgroundColor() {
    return getInt(KEY_GLOBAL_BACKGROUND_COLOR_RES, R.color.normal_bg);
  }

}
