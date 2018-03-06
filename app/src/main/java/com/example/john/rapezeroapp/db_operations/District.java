package com.example.john.rapezeroapp.db_operations;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.john.rapezeroapp.core.DBHelper;
import com.example.john.rapezeroapp.util.Constants;

import static com.example.john.rapezeroapp.util.Constants.config.DISTRICT_ID;
import static com.example.john.rapezeroapp.util.Constants.config.DISTRICT_NAME;

/**
 * Created by john on 10/18/17.
 */

public class District {
    Context context;
    public District(Context context){
        this.context = context;
    }

    public String save(String name) {
        SQLiteDatabase database = new DBHelper(context).getWritableDatabase();
        String message = null;

        try{
            int status = 0;
            //database.beginTransaction();
            ContentValues contentValues = new ContentValues();

            contentValues.put(DISTRICT_NAME,name);
            database.insert(Constants.config.TABLE_DISTRICT, null, contentValues);
            //database.setTransactionSuccessful();
            message = "District cases saved!";

        }catch (Exception e){
            e.printStackTrace();
            message = "Sorry, error: "+e;
        }finally {
            //database.close();
            // database.endTransaction();
        }
        return message;
    }

    public String edit(String name, int id) {
        SQLiteDatabase database = new DBHelper(context).getWritableDatabase();
        String message = null;

        try{
            int status = 0;
            //database.beginTransaction();
            ContentValues contentValues = new ContentValues();

            contentValues.put(DISTRICT_NAME,name);
            database.update(Constants.config.TABLE_DISTRICT, contentValues, DISTRICT_ID+"="+id, null);
            //database.setTransactionSuccessful();
            message = "District cases updated!";

        }catch (Exception e){
            e.printStackTrace();
            message = "Sorry, error: "+e;
        }finally {
            //database.close();
            // database.endTransaction();
        }
        return message;
    }

    ///// TODO: 10/13/17  select here!
    public Cursor selectAll(){
        SQLiteDatabase db = new DBHelper(context).getReadableDB();
        Cursor cursor = null;
        try{
            db.beginTransaction();
            String query = "SELECT *  FROM" +
                    " "+ Constants.config.TABLE_DISTRICT+"  ORDER BY "+Constants.config.DISTRICT_NAME+" ASC ";
            cursor = db.rawQuery(query,null);
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
        return  cursor;
    }

    public int selectLastID(){
        SQLiteDatabase db = new DBHelper(context).getReadableDB();
        Cursor cursor = null;
        int id = 0;
        try{
            db.beginTransaction();
            String query = "SELECT "+Constants.config.DISTRICT_ID+"  FROM" +
                    " "+ Constants.config.TABLE_DISTRICT+"  ORDER BY "+Constants.config.DISTRICT_ID+" DESC LIMIT 1 ";
            cursor = db.rawQuery(query,null);
            db.setTransactionSuccessful();

            if (cursor.moveToFirst()){
                do {
                    id = cursor.getInt(cursor.getColumnIndex(Constants.config.DISTRICT_ID));
                }while (cursor.moveToNext());
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
        return  id;
    }

    public Cursor select(int id){
        SQLiteDatabase db = new DBHelper(context).getReadableDB();
        Cursor cursor = null;
        try{
            db.beginTransaction();
            String query = "SELECT *  FROM" +
                    " "+ Constants.config.TABLE_DISTRICT+" WHERE "+DISTRICT_ID+" = '"+id+"' " +
                    " ORDER BY "+Constants.config.DISTRICT_NAME+" ASC ";
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
