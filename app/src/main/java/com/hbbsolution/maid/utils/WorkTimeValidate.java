package com.hbbsolution.maid.utils;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by tantr on 6/7/2017.
 */

public class WorkTimeValidate {
    public static String[] workTimeValidate(String endDate)  {
        long currentTime = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat dates = new SimpleDateFormat("dd/MM/yyyy");

        Date mEndDate = null;
        try {
            mEndDate = simpleDateFormat.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long millisecond = mEndDate.getTime();
        long timer = (currentTime - millisecond);
        long timeHistory = (timer / 60000);
        String[] currentTimeHistory = {"0", "0", "0"};
        if (timeHistory < 60) {
            currentTimeHistory[0] = String.format("%d",
                    TimeUnit.MILLISECONDS.toMinutes(timer),
                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timer)));
            currentTimeHistory[1] ="0";
            currentTimeHistory[2] ="0";

        } else if (60 < timeHistory && timeHistory < 1440) {
            currentTimeHistory[1] = String.format("%d",
                    TimeUnit.MILLISECONDS.toHours(timer),
                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timer)));
            currentTimeHistory[0] ="0";
            currentTimeHistory[2] ="0";

        } else if (timeHistory > 1440) {
            currentTimeHistory[2] = String.format("%d",
                    TimeUnit.MILLISECONDS.toDays(timer),
                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timer)));
            currentTimeHistory[0] ="0";
            currentTimeHistory[1] ="0";

        }
        return currentTimeHistory;
    }
}
