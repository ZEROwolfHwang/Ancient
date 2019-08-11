package com.liuzr.ancient.util;

import android.util.Log;

import org.joda.time.DateTime;

public class DateUtil {
  private static final long SECOND_IN_MILLIS = 1000;

  public static boolean checkDayAndMonth(int day, int month, int year) {
    if (month <= 0 || month > 12 || day <= 0) {
      return false;
    }
    DateTime dateTime = new DateTime(year, month, 1, 0, 0);
    return day <= dateTime.dayOfMonth().getMaximumValue();
  }

  public static int getLastDay(int month, int year) {
    if (!checkMonth(month)) {
      return -1;
    }
    DateTime dateTime = new DateTime(year, month, 1, 0, 0);
    return dateTime.dayOfMonth().getMaximumValue();
  }

  private static boolean checkMonth(int month) {
    return month > 0 && month <= 12;
  }

  public static long getCurrentTimeStamp() {
    Log.i("lzr", System.currentTimeMillis() / SECOND_IN_MILLIS + "");
    return System.currentTimeMillis() / SECOND_IN_MILLIS;
  }

}
