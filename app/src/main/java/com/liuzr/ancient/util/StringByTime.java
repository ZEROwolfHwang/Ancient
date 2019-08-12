
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
    private static Map<String, String> editContentHintMap = new HashMap<>();
    private static Map<String, String> threeLinePoemMap = new HashMap<>();

    static {
//        Map<String, String> editTitleHintMap = new HashMap<>();
        Context context = MainApplication.getInstance().getApplicationContext();
        Resources resources = context.getResources();
        String[] editTitleHintArray = resources.getStringArray(R.array.edit_title_hint_array);
        String[] editContentHintArray = resources.getStringArray(R.array.edit_content_hint_array);
        String[] threeLinePoemArray = resources.getStringArray(R.array.three_line_poem_array);

        //加载标题title的map
        editTitleHintMap.put(MORNING, editTitleHintArray[0]);
        editTitleHintMap.put(BEFORENOON, editTitleHintArray[1]);
        editTitleHintMap.put(NOON, editTitleHintArray[2]);
        editTitleHintMap.put(AFTERNOON, editTitleHintArray[3]);
        editTitleHintMap.put(NIGHT, editTitleHintArray[4]);
        editTitleHintMap.put(EVENING, editTitleHintArray[5]);
        editTitleHintMap.put(MIDNIGHT, editTitleHintArray[6]);
        editTitleHintMap.put(DEFAULT, editTitleHintArray[7]);

        //加载内容content的map
        editContentHintMap.put(MORNING, editContentHintArray[0]);
        editContentHintMap.put(BEFORENOON, editContentHintArray[1]);
        editContentHintMap.put(NOON, editContentHintArray[2]);
        editContentHintMap.put(AFTERNOON, editContentHintArray[3]);
        editContentHintMap.put(NIGHT, editContentHintArray[4]);
        editContentHintMap.put(EVENING, editContentHintArray[5]);
        editContentHintMap.put(MIDNIGHT, editContentHintArray[6]);
        editContentHintMap.put(DEFAULT, editContentHintArray[7]);

        //加载三列显示的map
        threeLinePoemMap.put(MORNING, threeLinePoemArray[0]);
        threeLinePoemMap.put(BEFORENOON, threeLinePoemArray[1]);
        threeLinePoemMap.put(NOON, threeLinePoemArray[2]);
        threeLinePoemMap.put(AFTERNOON, threeLinePoemArray[3]);
        threeLinePoemMap.put(NIGHT, threeLinePoemArray[4]);
        threeLinePoemMap.put(EVENING, threeLinePoemArray[5]);
        threeLinePoemMap.put(MIDNIGHT, threeLinePoemArray[6]);
        threeLinePoemMap.put(DEFAULT, threeLinePoemArray[7]);
    }

    public static String getEditContentHintByNow() {
        DateTime now = new DateTime();
        int currentHour = now.getHourOfDay();
        String keyType = getType(currentHour);
        return editContentHintMap.get(keyType);
//        return getStringFromDataset(editContentHintDataSet);
    }

    public static String getEditTitleHintByNow() {
        DateTime now = new DateTime();
        int currentHour = now.getHourOfDay();
        String keyType = getType(currentHour);
        return editTitleHintMap.get(keyType);
    }

    public static String[] getThreeLinePoemArrayByNow() {
        DateTime now = new DateTime();
        int currentHour = now.getHourOfDay();
        String keyType = getType(currentHour);
        String poemString = threeLinePoemMap.get(keyType);
        Context context = MainApplication.getInstance().getApplicationContext();
        return StringUtil.split(poemString, context.getResources().getString(R.string.three_line_string_split));
    }
    /**
     * 根据当前的整点时间拿到时间所属的type值(key)
     * @param currentHour
     * @return
     */
    public static String getType(int currentHour) {
        Log.i("zero", currentHour + "");
        if (currentHour >= 6 && currentHour < 10) {
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
}
