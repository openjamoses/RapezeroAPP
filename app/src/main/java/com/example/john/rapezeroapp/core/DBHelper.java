package com.example.john.rapezeroapp.core;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Handler;
import android.util.Log;

import com.example.john.rapezeroapp.util.Constants;

public class DBHelper extends SQLiteOpenHelper {

    private final Handler handler;
    public DBHelper(Context context) {
        super(context, Constants.config.DB_NAME, null, Constants.config.DB_VERSION);
        handler = new Handler(context.getMainLooper());
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //TODO : Creating tables
        db.execSQL(Create_Table.create.CREATE_DISTRICT);
        db.execSQL(Create_Table.create.CREATE_PERSONEL);
        db.execSQL(Create_Table.create.CREATE_POLICE);
        db.execSQL(Create_Table.create.CREATE_LC);
        db.execSQL(Create_Table.create.CREATE_FRIEND);
        db.execSQL(Create_Table.create.CREATE_EMERGENCY);
        Log.e("DATABASE OPERATION","6 Tables  created / open successfully");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO: Updating table here
        db.execSQL("DROP TABLE IF EXISTS "+ Constants.config.TABLE_DISTRICT);
        db.execSQL("DROP TABLE IF EXISTS "+ Constants.config.TABLE_PERSONEL);
        db.execSQL("DROP TABLE IF EXISTS "+ Constants.config.TABLE_POLICE);
        db.execSQL("DROP TABLE IF EXISTS "+ Constants.config.TABLE_LC);
        db.execSQL("DROP TABLE IF EXISTS "+ Constants.config.TABLE_FRIEND);
        db.execSQL("DROP TABLE IF EXISTS "+ Constants.config.TABLE_EMERGENCY);
        onCreate(db);
        Log.e("DATABASE OPERATION", "6 Table created / open successfully");

    }
    private void runOnUiThread(Runnable r) {
        handler.post(r);
    }
    public  SQLiteDatabase getWritableDB(){
        SQLiteDatabase database = this.getWritableDatabase();
        return database;
    }
    public SQLiteDatabase getReadableDB(){
        SQLiteDatabase database = this.getReadableDatabase();
        return database;
    }
    /************** Insertion ends here **********************/
}