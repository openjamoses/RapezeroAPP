package com.example.john.rapezeroapp.core;


import com.example.john.rapezeroapp.util.Constants;

/**
 * Created by john on 8/12/17.
 */

public class Create_Table {
    public abstract class create{
        //todo: queries to create the table
        public static final String CREATE_PERSONEL =
                "CREATE TABLE "+ Constants.config.TABLE_PERSONEL +" ("+ Constants.config.PERSONEL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " "+Constants.config.PERSONEL_NAME+" TEXT,"+Constants.config.PERSONEL_CONTACT+" TEXT, "+Constants.config.PERSONEL_DESCRIPTION+" TEXT, " +
                        " "+Constants.config.CURRENT_LOCATION+" TEXT, "+Constants.config.DISTRICT_ID+" INTEGER," +
                        " "+Constants.config.PASSWORD+" TEXT,"+Constants.config.USERNAME+" TEXT );";

        public static final String CREATE_POLICE =
                "CREATE TABLE "+ Constants.config.TABLE_POLICE +" ("+ Constants.config.POLICE_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " "+Constants.config.POLICE_NAME+" TEXT,"+Constants.config.POLICE_TYPE+" TEXT," +
                        " "+Constants.config.PHONE_CONTACT+" TEXT, "+Constants.config.DISTRICT_ID+" INTEGER );";

        public static final String CREATE_LC =
                "CREATE TABLE "+ Constants.config.TABLE_LC +" ("+ Constants.config.LC_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " "+Constants.config.LC_NAME+" TEXT,"+Constants.config.LC_TYPE+" TEXT," +
                        " "+Constants.config.PHONE_CONTACT+" TEXT, "+Constants.config.DISTRICT_ID+" INTEGER );";

        public static final String CREATE_FRIEND =
                "CREATE TABLE "+ Constants.config.TABLE_FRIEND +" ("+ Constants.config.FRIEND_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " "+Constants.config.FRIEND_NAME+" TEXT,"+Constants.config.FRIEND_TYPE+" TEXT," +
                        " "+Constants.config.PHONE_CONTACT+" TEXT, "+Constants.config.DISTRICT_ID+" INTEGER );";

        public static final String CREATE_EMERGENCY =
                "CREATE TABLE "+ Constants.config.TABLE_EMERGENCY +" ("+ Constants.config.EMERGENCY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " "+Constants.config.EMERGENCY_NAME+" TEXT,"+Constants.config.EMERGENCY_TYPE+" TEXT," +
                        " "+Constants.config.PHONE_CONTACT+" TEXT, "+Constants.config.DISTRICT_ID+" INTEGER );";

        public static final String CREATE_DISTRICT =
                "CREATE TABLE "+ Constants.config.TABLE_DISTRICT +" ("+ Constants.config.DISTRICT_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " "+Constants.config.DISTRICT_NAME+" TEXT );";


    }
}
