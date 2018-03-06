package com.example.john.rapezeroapp.util;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by john on 7/9/17.
 */

public class DateTime {
    public static String getCurrentDate(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = sdf.format(c.getTime());
        return strDate;
    }
    public static String getYear(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String strDate = sdf.format(c.getTime());
        return strDate;
    }
    public static String getCurrentTime(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        String strTime = sdf.format(c.getTime());
        return strTime;
    }

    public static String diff(String time1, String time2){
        String diff = "";
        try {
            SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss aa");
            Date date1 = format.parse(time1);
            Date date2 = format.parse(time2);
            long mills = date2.getTime() - date1.getTime();
            Log.v("Data1", "" + date1.getTime());
            Log.v("Data2", "" + date2.getTime());
            int hours = (int) (mills / (1000 * 60 * 60));
            int mins = (int) (mills % (1000 * 60 * 60));

            diff = hours + ":" + mins; // updated value every1 second
        }catch (Exception e){
            e.printStackTrace();
        }
        return diff;
    }
}
