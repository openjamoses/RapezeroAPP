package com.example.john.rapezeroapp.firebase;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.example.john.rapezeroapp.util.Constants;
import com.firebase.client.Firebase;
import com.firebase.client.ServerValue;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static com.example.john.rapezeroapp.util.Constants.config.CURRENT_LOCATION;
import static com.example.john.rapezeroapp.util.Constants.config.DISTRICT_NAME;
import static com.example.john.rapezeroapp.util.Constants.config.FIREBASE_URL;
import static com.example.john.rapezeroapp.util.Constants.config.PERSONEL_CONTACT;
import static com.example.john.rapezeroapp.util.Constants.config.PERSONEL_DESCRIPTION;
import static com.example.john.rapezeroapp.util.Constants.config.PERSONEL_NAME;
import static com.example.john.rapezeroapp.util.Constants.config.STORAGE_REFERENCE;
import static com.example.john.rapezeroapp.util.Constants.config.USERNAME;

/**
 * Created by john on 4/8/18.
 */

public class UploadFile {
    private Context context;
    private Firebase reference;
    private StorageReference storageReference;
    public UploadFile(Context context){
        this.context = context;
        Firebase.setAndroidContext(context);
        try{
            FirebaseApp.initializeApp(context);
        }catch (Exception e){
            e.printStackTrace();
        }
        storageReference =
                FirebaseStorage.getInstance().getReferenceFromUrl(STORAGE_REFERENCE);
        this.reference = new Firebase(FIREBASE_URL+"upload_details");

    }
    public void create(final String name, final String contact, final String description, String location, String district, String username,final String filePath){

        Map<String, String> map = new HashMap<String, String>();
        map.put(PERSONEL_NAME, name);
        map.put(PERSONEL_CONTACT,contact);
        map.put(PERSONEL_DESCRIPTION,description);
        map.put(CURRENT_LOCATION,location);
        map.put(DISTRICT_NAME,district);
        map.put(USERNAME,username);
        uploadFile(filePath,map,name);

    }
    private void uploadFile(String filePath, final Map<String, String> map, final String name ) {

        try{
          //  BaseApplication.deleteCache(context);
        }catch (Exception e){
            e.printStackTrace();
        }

        Uri uri = Uri.fromFile(new File(filePath));
        //checking if file is available
        if (filePath != null) {
            //getting the storage reference
            StorageReference sRef = storageReference.child(Constants.config.FIREBASE_UPLOADS+"/"+name + System.currentTimeMillis() + ".3gpp");
            //adding the file to reference
            try {
                sRef.putFile(uri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                int type = 1;
                                if (taskSnapshot.getDownloadUrl().toString() != null) {
                                    if (taskSnapshot.getDownloadUrl().toString().equals("")) {
                                        //adding an upload to firebase database
                                        map.put("file_url", taskSnapshot.getDownloadUrl().toString());
                                        map.put("date", String.valueOf(ServerValue.TIMESTAMP));
                                        map.put("type", String.valueOf(type));
                                        //TODO:::
                                        reference.push().setValue(map);
                                    }
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                //progressDialog.dismiss();
                                Log.e("FIREBASE", exception.getMessage());
                                //Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();


                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                //displaying the upload progress
                                //double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                                //progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                            }
                        });
            }catch (Exception e){
                e.printStackTrace();
            }
        } else {
            //display an error if no file is selected

            Toast.makeText(context, "Error uploading the file..!", Toast.LENGTH_SHORT).show();

        }
    }

    public String getFileExtension(Uri uri) {
        ContentResolver cR = context.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

}
