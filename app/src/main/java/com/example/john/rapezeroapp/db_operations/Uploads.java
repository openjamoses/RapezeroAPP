package com.example.john.rapezeroapp.db_operations;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.example.john.rapezeroapp.core.DBHelper;
import com.example.john.rapezeroapp.util.AndroidMultiPartEntity;
import com.example.john.rapezeroapp.util.DateTime;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.john.rapezeroapp.util.Constants.config.FILE_UPLOAD_URL;
import static com.example.john.rapezeroapp.util.Constants.config.HOST_URL;

/**
 * Created by john on 9/10/17.
 */

public class Uploads {
    private final static String TAG = "SMS_Operations";
    private Context context;
    public List<String> lists;
    public Uploads(Context context) {
        this.context = context;
        lists = new ArrayList<>();
    }

    public void uploads(String filePath,String name,String contact,String desc,String location, String district, String username){
        new UploadFileToServer(context,filePath,name, contact,desc,location,district,username).execute();
    }

    public void upload(String filePath,String doctor_id,String phone,String message,int type){
        //new UploadFileToServer(context,id,filePath, doctor_id,phone,message,type).execute();
    }

    /**
     * Uploading the file to server
     * */
    private class UploadFileToServer extends AsyncTask<Void, Integer, String> {
        String filePath;
        Context context;
        String name,contact, desc, location, district, username;
        long totalSize = 0;

        UploadFileToServer(Context context,String filePath, String name, String contact, String desc, String location, String district, String username){
            this.context = context;
            this.filePath = filePath;
            this.name = name;
            this.contact = contact;
            this.desc = desc;
            this.location = location;
            this.district = district;
            this.username = username;
        }
        @Override
        protected void onPreExecute() {
            // setting progress bar to zero
            //progressBar.setProgress(0);
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(Void... params) {
            return uploadFile();
        }
        @SuppressWarnings("deprecation")
        private String uploadFile() {
            String responseString = null;
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(HOST_URL+FILE_UPLOAD_URL);
            try {
                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                        new AndroidMultiPartEntity.ProgressListener() {
                            @Override
                            public void transferred(long num) {
                                publishProgress((int) ((num / (float) totalSize) * 100));
                            }
                        });

                File sourceFile = new File(filePath);
                String date = DateTime.getCurrentDate();
                String time = DateTime.getCurrentTime();
                int status = 0;
                // Adding file data to http body
                entity.addPart("image", new FileBody(sourceFile));
                // Extra parameters if you want to pass to server
                entity.addPart("name", new StringBody(name));
                entity.addPart("contact", new StringBody(contact));
                entity.addPart("desc", new StringBody(desc));
                entity.addPart("location", new StringBody(location));
                entity.addPart("district", new StringBody(district));
                entity.addPart("username", new StringBody(username));
                entity.addPart("date", new StringBody(date));
                entity.addPart("time", new StringBody(time));

                totalSize = entity.getContentLength();
                httppost.setEntity(entity);
                // Making server call
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();

                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    // Server response
                    responseString = EntityUtils.toString(r_entity);

                    try{
                        JSONObject jsonObject = new JSONObject(responseString);

                        String message_ = jsonObject.getString("message");
                        String paths = jsonObject.getString("file_path");
                        int new_status = 0;
                        if (message_.equals("File uploaded successfully!")){
                            new_status = 1;
                            Log.e(TAG,"*******************************************************");
                            Log.e(TAG,responseString);

                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    Log.e(TAG,"*******************************************************");

                } else {
                    responseString = "Error occurred! Http Status Code: "
                            + statusCode;
                }
            } catch (ClientProtocolException e) {
                responseString = e.toString();
            } catch (IOException e) {
                responseString = e.toString();
            }
            return responseString;
        }
        @Override
        protected void onPostExecute(String result) {
            Log.e(TAG, "Response from server: " + result);
            super.onPostExecute(result);
        }
    }
}

