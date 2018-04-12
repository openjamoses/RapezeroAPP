package com.example.john.rapezeroapp.db_operations;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.john.rapezeroapp.core.DBHelper;
import com.example.john.rapezeroapp.util.Constants;

import static com.example.john.rapezeroapp.util.Constants.config.DISTRICT_ID;
import static com.example.john.rapezeroapp.util.Constants.config.PHONE_CONTACT;
import static com.example.john.rapezeroapp.util.Constants.config.POLICE_ID;
import static com.example.john.rapezeroapp.util.Constants.config.POLICE_NAME;
import static com.example.john.rapezeroapp.util.Constants.config.POLICE_TYPE;

/**
 * Created by john on 10/18/17.
 */

public class Police {
    Context context;
    public Police(Context context){
        this.context = context;
    }

    public String save(String name, String type,String phone,int district) {
        SQLiteDatabase database = new DBHelper(context).getWritableDatabase();
        String message = null;

        try{
            int status = 0;
            ContentValues contentValues = new ContentValues();

            contentValues.put(POLICE_NAME,name);
            contentValues.put(POLICE_TYPE,type);
            contentValues.put(PHONE_CONTACT,phone);
            contentValues.put(DISTRICT_ID,district);
            database.insert(Constants.config.TABLE_POLICE, null, contentValues);
            //database.setTransactionSuccessful();
            message = "police cases saved!";

        }catch (Exception e){
            e.printStackTrace();
            message = "Sorry, error: "+e;
        }finally {
            //database.close();
            // database.endTransaction();
        }
        return message;
    }

    public String edit(String name, String type,String phone,int district, int id) {
        SQLiteDatabase database = new DBHelper(context).getWritableDatabase();
        String message = null;

        try{
            int status = 0;
            ContentValues contentValues = new ContentValues();

            contentValues.put(POLICE_NAME,name);
            contentValues.put(POLICE_TYPE,type);
            contentValues.put(PHONE_CONTACT,phone);
            contentValues.put(DISTRICT_ID,district);
            database.update(Constants.config.TABLE_POLICE, contentValues, POLICE_ID+"="+id, null);
            //database.setTransactionSuccessful();
            message = "police cases updated!";

        }catch (Exception e){
            e.printStackTrace();
            message = "Sorry, error: "+e;
        }finally {
            //database.close();
            // database.endTransaction();
        }
        return message;
    }

    public Cursor selectAll(){
        SQLiteDatabase db = new DBHelper(context).getReadableDB();
        Cursor cursor = null;
        try{
            db.beginTransaction();
            String query = "SELECT *  FROM" +
                    " "+ Constants.config.TABLE_POLICE+" p, "+Constants.config.TABLE_DISTRICT+" d" +
                    " WHERE d."+Constants.config.DISTRICT_ID+" = p."+Constants.config.DISTRICT_ID+"" +
                    " ORDER BY "+Constants.config.POLICE_NAME+" DESC LIMIT 2 ";
            cursor = db.rawQuery(query,null);
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
        return  cursor;
    }

    public Cursor select(int id){
        SQLiteDatabase db = new DBHelper(context).getReadableDB();
        Cursor cursor = null;
        try{
            db.beginTransaction();
            String query = "SELECT *  FROM" +
                    " "+ Constants.config.TABLE_POLICE+" p, "+Constants.config.TABLE_DISTRICT+" d" +
                    " WHERE d."+Constants.config.DISTRICT_ID+" = p."+Constants.config.DISTRICT_ID+" AND "+POLICE_ID+" = '"+id+"'" +
                    " ORDER BY "+Constants.config.POLICE_NAME+" ASC ";
            cursor = db.rawQuery(query,null);
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
        return  cursor;
    }
}
