
package com.liuzr.ancient.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.liuzr.ancient.R;
import com.liuzr.ancient.global.MainApplication;

import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.Map;

public class StringByTime {
    private static String MORNING = "MORNING";
    private static String BEFORENOON = "BEFORENOON";
    private static String NOON = "NOON";
    private static String AFTERNOON = "AFTERNOON";
    private static String NIGHT = "NIGHT";
    private static String EVENING = "EVENING";
    private static String MIDNIGHT = "MIDNIGHT";
    private static String DEFAULT = "DEFAULT";
    private static Map<String, String> editTitleHintMap = new HashMap<>();

    static {
//        Map<String, String> editTitleHintMap = new HashMap<>();
        Context context = MainApplication.getInstance().getApplicationContext();
        Resources resources = context.getResources();
        String[] editTitleHintArray = resources.getStringArray(R.array.edit_title_hint_array);
        editTitleHintMap.put(MORNING, editTitleHintArray[0]);
        editTitleHintMap.put(BEFORENOON, editTitleHintArray[1]);
        editTitleHintMap.put(NOON, editTitleHintArray[2]);
        editTitleHintMap.put(AFTERNOON, editTitleHintArray[3]);
        editTitleHintMap.put(NIGHT, editTitleHintArray[4]);
        editTitleHintMap.put(EVENING, editTitleHintArray[5]);
        editTitleHintMap.put(MIDNIGHT, editTitleHintArray[6]);
        editTitleHintMap.put(DEFAULT, editTitleHintArray[7]);
    }



    private enum TimeRange {
        MORNING(6, 10),
        BEFORENOON(10, 12),
        NOON(12, 14),
        AFTERNOON(14, 18),
        NIGHT(18, 21),
        EVENING(21, 24),
        MIDNIGHT(0, 6),
        DEFAULT(-1, -1);
        private int startHour, endHour;

        private static TimeRange[] timeRanges;


        static {
            timeRanges = new TimeRange[8];
            timeRanges[0] = MORNING;
            timeRanges[1] = BEFORENOON;
            timeRanges[2] = NOON;
            timeRanges[3] = AFTERNOON;
            timeRanges[4] = NIGHT;
            timeRanges[5] = EVENING;
            timeRanges[6] = MIDNIGHT;
            timeRanges[7] = DEFAULT;
        }

        TimeRange(int startHour, int endHour) {
            this.startHour = startHour;
            this.endHour = endHour;
        }

        public static TimeRange getTypeByIndex(int index) {
            return timeRanges[index];
        }

        public boolean contains(int currentHour) {
            return startHour <= currentHour && currentHour < endHour;
        }

        public static TimeRange getType(int currentHour) {
            if (MORNING.contains(currentHour)) {
                return MORNING;
            } else if (BEFORENOON.contains(currentHour)) {
                return BEFORENOON;
            } else if (NOON.contains(currentHour)) {
                return NOON;
            } else if (AFTERNOON.contains(currentHour)) {
                return AFTERNOON;
            } else if (NIGHT.contains(currentHour)) {
                return NIGHT;
            } else if (EVENING.contains(currentHour)) {
                return EVENING;
            } else if (MIDNIGHT.contains(currentHour)) {
                return MIDNIGHT;
            }
            return DEFAULT;
        }
    }

    private static Map<TimeRange, String> editContentHintDataSet;
    private static Map<TimeRange, String> editTitleHintDataSet;
    private static Map<TimeRange, String> threeLinePoemDataSet;

//    private static Map<String, String> editTitleHintMap;


    static {
        Context context = MainApplication.getInstance().getApplicationContext();
        Resources resources = context.getResources();

        editContentHintDataSet = new HashMap<>();
        String[] editContentHintArray = resources.getStringArray(R.array.edit_content_hint_array);

        editTitleHintDataSet = new HashMap<>();
        String[] editTitleHintArray = resources.getStringArray(R.array.edit_title_hint_array);

        threeLinePoemDataSet = new HashMap<>();
        String[] threeLinePoemArray = resources.getStringArray(R.array.three_line_poem_array);

        for (int i = 0; i < 8; i++) {
            editContentHintDataSet.put(TimeRange.getTypeByIndex(i), editContentHintArray[i]);
            editTitleHintDataSet.put(TimeRange.getTypeByIndex(i), editTitleHintArray[i]);
            threeLinePoemDataSet.put(TimeRange.getTypeByIndex(i), threeLinePoemArray[i]);
        }
    }

    private static String getStringFromDataset(Map<TimeRange, String> dataSet) {
        DateTime now = new DateTime();
        int currentHour = now.getHourOfDay();
        return dataSet.get(TimeRange.getType(currentHour));
    }

    public static String getEditContentHintByNow() {
        DateTime now = new DateTime();
        int currentHour = now.getHourOfDay();
        return getStringFromDataset(editContentHintDataSet);
    }

    public static String getEditTitleHintByNow() {
        DateTime now = new DateTime();
        int currentHour = now.getHourOfDay();
        String keyType = getType(currentHour);
        return editTitleHintMap.get(keyType);
    }

    /**
     * 根据当前的整点时间拿到时间所属的type值(key)
     * @param currentHour
     * @return
     */
    public static String getType(int currentHour) {
        Log.i("zero", currentHour + "");
        if (currentHour > 6 && currentHour <= 10) {
            return MORNING;
        } else if (currentHour >= 10 && currentHour < 12) {
            return BEFORENOON;
        } else if (currentHour >= 12 && currentHour < 14) {
            return NOON;
        } else if (currentHour >= 14 && currentHour < 18) {
            return AFTERNOON;
        } else if (currentHour >= 18 && currentHour < 21) {
            return NIGHT;
        } else if (currentHour >= 21 && currentHour < 24) {
            return EVENING;
        } else if (currentHour >= 0 && currentHour < 6) {
            return MIDNIGHT;
        }
        return DEFAULT;
    }
    public static String[] getThreeLinePoemArrayByNow() {
        String poemString = getStringFromDataset(threeLinePoemDataSet);
        Context context = MainApplication.getInstance().getApplicationContext();
        return StringUtil.split(poemString, context.getResources().getString(R.string.three_line_string_split));
    }
}
