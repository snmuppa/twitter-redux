package com.fetherz.saim.twitterredux.utils;

import android.text.format.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by sm032858 on 3/25/17.
 */

public class GenericUtil {

    private static final String TAG = "GenericUtil";

    // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");

    /**
     * Takes in the raw JSON date and converts in to a date in twitter date format
     * @param rawJsonDate
     * @return Date formatted as a String in 'twitter' date format
     * @Exception ParseException
     */
    public static String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            LogUtil.logE(TAG, e.getMessage(), e);
        }

        return relativeDate;
    }

    public static boolean isMatch(String input, String search){
        if(input != null && search != null){
            String regex = String.format("(?i:.*%s.*)", search);
            return input.matches(regex);
        }
        return false;
    }
}
