package com.example.john.rapezeroapp.db_operations;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.john.rapezeroapp.core.DBHelper;
import com.example.john.rapezeroapp.util.Constants;
import com.example.john.rapezeroapp.util.Phone;

import static com.example.john.rapezeroapp.util.Constants.config.DISTRICT_ID;
import static com.example.john.rapezeroapp.util.Constants.config.FRIEND_ID;
import static com.example.john.rapezeroapp.util.Constants.config.FRIEND_NAME;
import static com.example.john.rapezeroapp.util.Constants.config.FRIEND_TYPE;
import static com.example.john.rapezeroapp.util.Constants.config.PHONE_CONTACT;

/**
 * Created by john on 10/18/17.
 */

public class Friend {

    Context context;
    public Friend(Context context){
        this.context = context;
    }

    public String save(String name, String type,String phone, int district) {
        SQLiteDatabase database = new DBHelper(context).getWritableDatabase();
        String message = null;

        try{
            int status = 0;
            String imei = Phone.getIMEI(context);
            //database.beginTransaction();
            ContentValues contentValues = new ContentValues();

            contentValues.put(FRIEND_NAME,name);
            contentValues.put(FRIEND_TYPE,type);
            contentValues.put(PHONE_CONTACT,phone);
            contentValues.put(DISTRICT_ID,district);
            database.insert(Constants.config.TABLE_FRIEND, null, contentValues);
            //database.setTransactionSuccessful();
            message = "friend cases saved!";

        }catch (Exception e){
            e.printStackTrace();
            message = "Sorry, error: "+e;
        }finally {
            //database.close();
            // database.endTransaction();
        }
        return message;
    }

    public String edit(String name, String type,String phone, int district, int id) {
        SQLiteDatabase database = new DBHelper(context).getWritableDatabase();
        String message = null;

        try{
            int status = 0;
            String imei = Phone.getIMEI(context);
            //database.beginTransaction();
            ContentValues contentValues = new ContentValues();

            contentValues.put(FRIEND_NAME,name);
            contentValues.put(FRIEND_TYPE,type);
            contentValues.put(PHONE_CONTACT,phone);
            contentValues.put(DISTRICT_ID,district);
            database.update(Constants.config.TABLE_FRIEND, contentValues, FRIEND_ID+"="+id, null);
            //database.setTransactionSuccessful();
            message = "friend cases updated!";

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
                    " "+ Constants.config.TABLE_FRIEND+" p, "+Constants.config.TABLE_DISTRICT+" d" +
                    " WHERE d."+Constants.config.DISTRICT_ID+" = p."+Constants.config.DISTRICT_ID+"" +
                    " ORDER BY "+Constants.config.FRIEND_NAME+" DESC LIMIT 6 ";
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
                    " "+ Constants.config.TABLE_FRIEND+" p, "+Constants.config.TABLE_DISTRICT+" d" +
                    " WHERE d."+Constants.config.DISTRICT_ID+" = p."+Constants.config.DISTRICT_ID+" AND "+FRIEND_ID+" = '"+id+"'" +
                    " ORDER BY "+Constants.config.FRIEND_NAME+" ASC ";
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
