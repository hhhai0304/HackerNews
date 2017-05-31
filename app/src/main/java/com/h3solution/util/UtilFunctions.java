package com.h3solution.util;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * Utils
 * Created by HHHai on 30-05-2017.
 */
public class UtilFunctions {

    public static String getTimeDifferent(long postTime) {
        Calendar startTime = Calendar.getInstance();
        startTime.setTimeInMillis(postTime);

        Calendar endTime = Calendar.getInstance();

        long diff = endTime.getTimeInMillis() - startTime.getTimeInMillis();
        long diffSeconds = TimeUnit.MILLISECONDS.toSeconds(diff);
        long diffMinutes = TimeUnit.MILLISECONDS.toMinutes(diff);
        long diffHours = TimeUnit.MILLISECONDS.toHours(diff);
        long diffDays = TimeUnit.MILLISECONDS.toDays(diff);

        if (diffDays > 0) {
            return diffDays + " days ago - ";
        } else if (diffHours > 0) {
            return diffHours + " hours ago - ";
        } else if (diffMinutes > 0) {
            return diffMinutes + " minutes ago - ";
        } else {
            return diffSeconds + " seconds ago - ";
        }
    }

    public static String[] getTopStories(String json) {
        return json.replace("[", "").replace("]", "").split(",");
    }
}