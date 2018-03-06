package com.example.john.rapezeroapp.core;

import android.content.Context;

/**
 * Created by john on 3/1/18.
 */

public class SharedPreference {

    android.content.SharedPreferences pref;
    android.content.SharedPreferences.Editor editor;
    Context _context;
    private static final String PREF_NAME = "testing";

    // All Shared Preferences Keys Declare as #public
    public static final String KEY_SET_APP_RUN_FIRST_TIME       =        "KEY_SET_APP_RUN_FIRST_TIME";
    public static final String KEY_SET_APP__TIME       =        "SET_TIME";


    public SharedPreference(Context context) // Constructor
    {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, 0);
        editor = pref.edit();

    }

    /*
    *  Set Method Generally Store Data;
    *  Get Method Generally Retrieve Data ;
    * */


    public void setApp_runFirst(Boolean App_runFirst, String time)
    {
        editor.remove(KEY_SET_APP_RUN_FIRST_TIME);
        editor.putBoolean(KEY_SET_APP_RUN_FIRST_TIME, App_runFirst);
        editor.putString(KEY_SET_APP__TIME, time);
        editor.commit();
    }

    public boolean getApp_runFirst()
    {
        boolean  App_runFirst = pref.getBoolean(KEY_SET_APP_RUN_FIRST_TIME, false);
        return  App_runFirst;
    }

    public String getApp_Time()
    {
        String  App_runFirst= pref.getString(KEY_SET_APP__TIME, "FIRST");
        return  App_runFirst;
    }


}