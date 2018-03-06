package com.example.john.rapezeroapp.db_operations;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.john.rapezeroapp.core.DBHelper;
import com.example.john.rapezeroapp.util.Constants;
import com.example.john.rapezeroapp.util.Phone;

import static com.example.john.rapezeroapp.util.Constants.config.CURRENT_LOCATION;
import static com.example.john.rapezeroapp.util.Constants.config.DISTRICT_ID;
import static com.example.john.rapezeroapp.util.Constants.config.PASSWORD;
import static com.example.john.rapezeroapp.util.Constants.config.PERSONEL_CONTACT;
import static com.example.john.rapezeroapp.util.Constants.config.PERSONEL_DESCRIPTION;
import static com.example.john.rapezeroapp.util.Constants.config.PERSONEL_NAME;
import static com.example.john.rapezeroapp.util.Constants.config.USERNAME;

/**
 * Created by john on 10/17/17.
 */

public class Personel {
    Context context;
    public Personel(Context context){
        this.context = context;
    }

    public String save(String name, String contact,String description,String location, int district, String password,String username) {
        SQLiteDatabase database = new DBHelper(context).getWritableDatabase();
        String message = null;

        try{
            int status = 0;
            String imei = Phone.getIMEI(context);
            //database.beginTransaction();
            ContentValues contentValues = new ContentValues();

            contentValues.put(PERSONEL_NAME,name);
            contentValues.put(PERSONEL_CONTACT,contact);
            contentValues.put(PERSONEL_DESCRIPTION,description);
            contentValues.put(CURRENT_LOCATION,location);
            contentValues.put(DISTRICT_ID,district);
            contentValues.put(USERNAME,username);
            contentValues.put(PASSWORD,password);
            database.insert(Constants.config.TABLE_PERSONEL, null, contentValues);
            //database.setTransactionSuccessful();
            message = "personel cases saved!";

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
                    " "+ Constants.config.TABLE_PERSONEL+" p, "+Constants.config.TABLE_DISTRICT+" d" +
                    " WHERE d."+Constants.config.DISTRICT_ID+" = p."+Constants.config.DISTRICT_ID+"" +
                    " ORDER BY "+Constants.config.PERSONEL_ID+" DESC LIMIT 1 ";
            cursor = db.rawQuery(query,null);
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
        return  cursor;
    }


    public String selectPassword(){
        SQLiteDatabase db = new DBHelper(context).getReadableDB();
        Cursor cursor = null;
        String password = "";
        try{
            db.beginTransaction();
            String query = "SELECT "+Constants.config.PASSWORD+" FROM" +
                    " "+ Constants.config.TABLE_PERSONEL+" " +
                    " ORDER BY "+Constants.config.PERSONEL_ID+" DESC LIMIT 1 ";
            cursor = db.rawQuery(query,null);
            if (cursor.moveToFirst()){
                do {
                    password = cursor.getString(cursor.getColumnIndex(Constants.config.PASSWORD));
                }while (cursor.moveToNext());
            }
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
        return  password;
    }

    public String login(String username, String password) {
        SQLiteDatabase db = new DBHelper(context).getReadableDB();
        Cursor cursor = null;
        String message = "";
        try{
            db.beginTransaction();
            String query = "SELECT "+Constants.config.PASSWORD+" FROM" +
                    " "+ Constants.config.TABLE_PERSONEL+" WHERE "+USERNAME+" = '"+username+"' AND "+PASSWORD+" = '"+password+"' " +
                    " ORDER BY "+Constants.config.PERSONEL_ID+" DESC LIMIT 1 ";
            cursor = db.rawQuery(query,null);
            if (cursor.moveToFirst()){
                message = "Login Successfuly!";
            }else {
                message = "Login failed!";
            }
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
        return  message;
    }

    public String edit(String fname, String contact, String descriptions, String location, int district_id, String password, String username, String user, String pass) {
        SQLiteDatabase database = new DBHelper(context).getWritableDatabase();
        String message = null;

        try{
            int status = 0;
            String imei = Phone.getIMEI(context);
            //database.beginTransaction();
            ContentValues contentValues = new ContentValues();

            contentValues.put(PERSONEL_NAME,fname);
            contentValues.put(PERSONEL_CONTACT,contact);
            contentValues.put(PERSONEL_DESCRIPTION,descriptions);
            contentValues.put(CURRENT_LOCATION,location);
            contentValues.put(DISTRICT_ID,district_id);
            contentValues.put(USERNAME,username);
            contentValues.put(PASSWORD,password);
            database.update(Constants.config.TABLE_PERSONEL,contentValues,USERNAME+"= '"+user+"' AND '"+PASSWORD+"' = '"+pass+"'", null);
            message = "personel details updated!";

        }catch (Exception e){
            e.printStackTrace();
            message = "Sorry, error: "+e;
        }finally {
            //database.close();
            // database.endTransaction();
        }
        return message;
    }
}
