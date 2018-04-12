package com.example.john.rapezeroapp.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.john.rapezeroapp.R;
import com.example.john.rapezeroapp.adapters.Concent_Adapter;
import com.example.john.rapezeroapp.core.SharedPreference;
import com.example.john.rapezeroapp.db_operations.Emmergency;
import com.example.john.rapezeroapp.db_operations.Friend;
import com.example.john.rapezeroapp.db_operations.Uploads;
import com.example.john.rapezeroapp.db_operations.Lcs;
import com.example.john.rapezeroapp.db_operations.Personel;
import com.example.john.rapezeroapp.db_operations.Police;
import com.example.john.rapezeroapp.util.Constants;
import com.example.john.rapezeroapp.util.DateTime;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.example.john.rapezeroapp.util.Constants.config.APP_FOLDER;
import static com.example.john.rapezeroapp.util.Constants.config.AUDIO_SUB_FOLDER;

/**
 * Created by john on 11/3/17.
 */

public class ConcentActivity extends AppCompatActivity  implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener{

    public int counter = 20;
    private boolean count_ = true;
    private TextInputLayout pass_layout;
    private Context context = this;
    private ListView listView;

    long delay = 1000; // 1 seconds after user stops typing
    long last_text_edit = 0;
    Handler handler = new Handler();
    private Runnable input_finish_checker = null;
    private CountDownTimer timer = null;
    private long milliLeft = 0,min = 0,sec = 0;
    AlertDialog dialog;
    TextView count_text;
    long total_time = 20000;
    List<String> contact_list = new ArrayList<>();

    //todo:: Locations listener variables
    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    private LocationManager locationManager;
    private LocationRequest mLocationRequest;
    double latitude = 0;
    double longitude = 0;
    private static final String TAG = "Googleplay Map";
    private SharedPreference sharedPreferenceObj; // Declare Global

    FirebaseAuth mAuth;

    //TODO :::: Audio player...!!!
    private MediaRecorder mediaRecorder;
    String voiceStoragePath;
    Uri vPath;
    int count  = 0;
    static final String AB = "abcdefghijklmnopqrstuvwxyz";
    static Random rnd = new Random();

    MediaPlayer mediaPlayer;
    DataOutputStream output; // output stream to target file
    boolean isRecording; // indicates if sound is currently being captured

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_content);

        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.consenct_dialog, null);
        // this is set the view from XML inside AlertDialog
        alert.setView(view);

        Button cancel_btn = (Button) view.findViewById(R.id.cancel_btn);
        Button continue_btn = (Button) view.findViewById(R.id.ok_btn);
        count_text = (TextView) view.findViewById(R.id.count_text);
        final EditText pass_text = (EditText) view.findViewById(R.id.pass_text);
        pass_layout = (TextInputLayout) view.findViewById(R.id.pass_layout);
        listView = (ListView) findViewById(R.id.listView);

        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false);

        dialog = alert.create();
        dialog.show();
        try{
            sharedPreferenceObj = new SharedPreference(context);

        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            FirebaseApp.initializeApp(context);
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            mAuth = FirebaseAuth.getInstance();
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            FirebaseUser user = mAuth.getCurrentUser();
            if (user != null) {
                // do your stuff
            } else {
                signInAnonymously();
            }
            }catch (Exception e){
            e.printStackTrace();
        }
        try {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        }catch (Exception e){
            e.printStackTrace();
        }

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String password = pass_text.getText().toString().trim();
                if (password.equals(new Personel(context).selectPassword())){
                    dialog.dismiss();
                    count_ = false;
                    Toast.makeText(context,"Thanks ....!!!",Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    pass_layout.setError("Incorrect password!");
                }

                //sharedPreferenceObj.setApp_runFirst(false, DateTime.getCurrentTime());

            }
        });

        continue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count_ = false;
                dialog.dismiss();
                sendSMS();
            }
        });
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        setValues();
        //// TODO: 11/5/17
        startTimer(total_time);

        input_finish_checker = new Runnable() {
            public void run() {
                if (System.currentTimeMillis() > (last_text_edit + delay - 500)) {
                    // TODO: do what you need here
                    // ............
                    // ............

                    timerResume();

                }
            }
        };


        pass_text.addTextChangedListener(new TextWatcher() {
                                            @Override
                                            public void beforeTextChanged (CharSequence s,int start, int count,
                                                                           int after){
                                            }
                                            @Override
                                            public void onTextChanged ( final CharSequence s, int start, int before,
                                                                        int count){
                                                //You need to remove this to run only once
                                                handler.removeCallbacks(input_finish_checker);

                                            }
                                            @Override
                                            public void afterTextChanged ( final Editable s){
                                                //avoid triggering event when text is empty
                                                if (s.length() > 0) {
                                                    last_text_edit = System.currentTimeMillis();
                                                    handler.postDelayed(input_finish_checker, delay);
                                                    timerPause();
                                                } else {

                                                }
                                            }
                                        }
        );
    }
    public void startTimer(long total_time ){

        timer = new CountDownTimer(total_time, 1000){
            public void onTick(long millisUntilFinished){
                count_text.setText(counter+" s");

                milliLeft=millisUntilFinished;
                min = (millisUntilFinished/(1000*60));
                sec = ((millisUntilFinished/1000)-min*60);
                count_text.setText(Long.toString(min)+":"+Long.toString(sec));

                //if (count){
                // counter--;
                //}

            }
            public  void onFinish(){
                count_text.setText("FINISH!!");
                dialog.dismiss();
                if (count_ == true){
                    sendSMS();
                }

            }

        };

        timer.start();
    }


    private void sendSMS(){
        SmsManager smsManager = SmsManager.getDefault();

        String message =getResources().getString(R.string.help_message)+ " location : http://maps.google.com/?q="+latitude+","+longitude;
        final String phone = "+256750507955";
        try {
            SmsManager sms = SmsManager.getDefault();
            final List<String> list = new ArrayList<>();
            try{
                Cursor cursor = new Police(context).selectAll();
                if (cursor.moveToFirst()){
                    do {
                        list.add(cursor.getString(cursor.getColumnIndex(Constants.config.POLICE_TYPE)));
                    }while (cursor.moveToNext());
                }
                cursor = new Friend(context).selectAll();
                if (cursor.moveToFirst()){
                    do {
                        list.add(cursor.getString(cursor.getColumnIndex(Constants.config.FRIEND_TYPE)));
                    }while (cursor.moveToNext());
                }
                cursor = new Emmergency(context).selectAll();
                if (cursor.moveToFirst()){
                    do {
                        list.add(cursor.getString(cursor.getColumnIndex(Constants.config.EMERGENCY_TYPE)));
                    }while (cursor.moveToNext());
                }
                cursor = new Lcs(context).selectAll();
                if (cursor.moveToFirst()){
                    do {
                        list.add(cursor.getString(cursor.getColumnIndex(Constants.config.LC_TYPE)));
                    }while (cursor.moveToNext());
                }


                for (int i=0; i<list.size(); i++){
                    sms.sendTextMessage(list.get(i), null,message , null, null);
                    Log.e("MESSAGES", "SMS SENT SUCCESS! TO: "+list.get(i));

                    if (i == list.size()-1){
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (contact_list.size() > 0){
                                    callPhone(phone);
                                    ///Log.e("Phone:::", list+"\t"+contact_list);
                                }

                            }},10000);
                    }
                }

                Toast.makeText(context,"SMS SENT SUCCESS TO: "+list.size(),Toast.LENGTH_SHORT).show();

            }catch (Exception e){
                e.printStackTrace();
            }




            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (mediaRecorder == null) {
                            initializeMediaRecord();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    try{
                        recordAudio();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }},500);




        }catch (Exception e){
            e.printStackTrace();
            Log.e("MESSAGES", "SMS NOT SENT!");
        }
    }
    private void callPhone(String phone){
        try{
            Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
            callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(callIntent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void timerPause() {
        timer.cancel();
    }
    private void timerResume() {
        Log.e("min", Long.toString(min));
        Log.e("Sec", Long.toString(sec));
        startTimer(milliLeft);
    }
    private void setValues() {
        List<String> name_list = new ArrayList<>();
        List<String> title_list = new ArrayList<>();
        List<String> district_list = new ArrayList<>();
        try{
            Cursor cursor = new Police(context).selectAll();
            if (cursor.moveToFirst()){
                do {
                    name_list.add(cursor.getString(cursor.getColumnIndex(Constants.config.POLICE_NAME)));
                    contact_list.add("Police "+cursor.getString(cursor.getColumnIndex(Constants.config.POLICE_TYPE)));
                    title_list.add(cursor.getString(cursor.getColumnIndex(Constants.config.PHONE_CONTACT)));
                    district_list.add(cursor.getString(cursor.getColumnIndex(Constants.config.DISTRICT_NAME)));
                }while (cursor.moveToNext());
            }
            cursor = new Lcs(context).selectAll();
            if (cursor.moveToFirst()){
                do {
                    name_list.add(cursor.getString(cursor.getColumnIndex(Constants.config.LC_NAME)));
                    contact_list .add("LC "+cursor.getString(cursor.getColumnIndex(Constants.config.LC_TYPE)));
                    title_list.add(cursor.getString(cursor.getColumnIndex(Constants.config.PHONE_CONTACT)));
                    district_list.add(cursor.getString(cursor.getColumnIndex(Constants.config.DISTRICT_NAME)));
                }while (cursor.moveToNext());
            }
            cursor = new Emmergency(context).selectAll();
            if (cursor.moveToFirst()){
                do {
                    name_list.add(cursor.getString(cursor.getColumnIndex(Constants.config.EMERGENCY_NAME)));
                    contact_list.add("LC "+cursor.getString(cursor.getColumnIndex(Constants.config.EMERGENCY_TYPE)));
                    title_list .add(cursor.getString(cursor.getColumnIndex(Constants.config.PHONE_CONTACT)));
                    district_list.add(cursor.getString(cursor.getColumnIndex(Constants.config.DISTRICT_NAME)));
                }while (cursor.moveToNext());
            }
            cursor = new Friend(context).selectAll();
            if (cursor.moveToFirst()){
                do {
                    name_list.add(cursor.getString(cursor.getColumnIndex(Constants.config.FRIEND_NAME)));
                    contact_list.add("LC "+cursor.getString(cursor.getColumnIndex(Constants.config.FRIEND_TYPE)));
                    title_list .add(cursor.getString(cursor.getColumnIndex(Constants.config.PHONE_CONTACT)));
                    district_list.add(cursor.getString(cursor.getColumnIndex(Constants.config.DISTRICT_NAME)));
                }while (cursor.moveToNext());
            }
            Concent_Adapter adapter = new Concent_Adapter(context,name_list,title_list,contact_list,district_list);
            listView.setAdapter(adapter);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        } startLocationUpdates();
        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if(mLocation == null){
            startLocationUpdates();
        }
        if (mLocation != null) {
             latitude = mLocation.getLatitude();
             longitude = mLocation.getLongitude();
        } else {
            // Toast.makeText(this, "Location not Detected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Connection Suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(TAG, "Connection failed. Error: " + connectionResult.getErrorCode());
    }

    @Override
    public void onLocationChanged(Location location) {
        if (mLocation != null) {
             latitude = mLocation.getLatitude();
             longitude = mLocation.getLongitude();
        }
    }

    protected void startLocationUpdates() {
        // Create the location request
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(1)
                .setFastestInterval(2);
        // Request location updates
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                mLocationRequest, this);
        Log.d("reque", "--->>>>");
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }

        try {
            isRecording = false;
            stopAudioRecording();

            playLastStoredAudioMusic();
            mediaPlayerPlaying();
            isRecording = false;

            sharedPreferenceObj.setApp_runFirst(false, DateTime.getCurrentTime());
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        try {
            isRecording = false;
            stopAudioRecording();

            playLastStoredAudioMusic();
            mediaPlayerPlaying();
            isRecording = false;
            sharedPreferenceObj.setApp_runFirst(false, DateTime.getCurrentTime());

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void stopAudioRecording(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(mediaRecorder != null){
                    mediaRecorder.stop();
                    mediaRecorder.release();
                    mediaRecorder = null;
                }
            }
        });
    }

    private void recordAudio(){
        hasSDCard();
        String PATH = Environment.getExternalStorageDirectory()+ "/"+APP_FOLDER+"/";
        File folder = new File(PATH);
        if(!folder.exists()){
            folder.mkdir();//If there is no folder it will be created.
        }

        if (!isRecording){
            //imageView.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_btn_speak_now));
        }
        voiceStoragePath = Environment.getExternalStorageDirectory()+"/"+APP_FOLDER;
        File audioVoice = new File(voiceStoragePath + File.separator + AUDIO_SUB_FOLDER);
        if(!audioVoice.exists()){
            audioVoice.mkdir();
        }
        voiceStoragePath = voiceStoragePath + File.separator + AUDIO_SUB_FOLDER+"/" + generateVoiceFilename(6) + ".3gpp";
        System.out.println("Audio path : " + voiceStoragePath);

        //stop_button.setEnabled(false);
        //play_button.setEnabled(false);
        initializeMediaRecord();

        isRecording = true;
        startAudioRecording();
    }

    private String generateVoiceFilename( int len ){
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ )
            sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
        return sb.toString();
    }

    private void startAudioRecording(){
        try {
            mediaRecorder.prepare();
            isRecording = true;
            mediaRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void playLastStoredAudioMusic(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (voiceStoragePath != null ) {
                    mediaPlayer = new MediaPlayer();


                    try {
                        mediaPlayer.setDataSource(voiceStoragePath);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        mediaPlayer.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mediaPlayer.start();
                }



            }
        });

        select(voiceStoragePath);
    }

    private void select(String voiceStoragePath){
        String name = "";
        String contact = "";
        String desc = "";
        String location = "";
        String district = "";
        String username = "";
        try{
            Cursor cursor = new Personel(context).selectAll();
            if (cursor.moveToFirst()){
                do {
                    name = cursor.getString(cursor.getColumnIndex(Constants.config.PERSONEL_NAME));
                    desc  = cursor.getString(cursor.getColumnIndex(Constants.config.PERSONEL_DESCRIPTION));
                    contact = cursor.getString(cursor.getColumnIndex(Constants.config.PERSONEL_CONTACT));
                    location = cursor.getString(cursor.getColumnIndex(Constants.config.CURRENT_LOCATION));
                    district = cursor.getString(cursor.getColumnIndex(Constants.config.DISTRICT_NAME));
                    username =  cursor.getString(cursor.getColumnIndex(Constants.config.USERNAME));

                }while (cursor.moveToNext());
            }

            new Uploads(context).uploads(voiceStoragePath,name,contact,desc,location,district,username);
            //new UploadFile(context).create(name,contact,desc,location,district,username,voiceStoragePath);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void stopAudioPlay(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(mediaPlayer != null){
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
            }
        });
    }

    private void hasSDCard(){
        Boolean isSDPresent = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
        if(isSDPresent)        {
            System.out.println("There is SDCard");
        }
        else{
            System.out.println("There is no SDCard");
        }
    }
    private void mediaPlayerPlaying(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(!mediaPlayer.isPlaying()){
                    stopAudioPlay();
                }
            }
        });
    }
    private void initializeMediaRecord(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try{
                    mediaRecorder = new MediaRecorder();
                    mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
                    mediaRecorder.setOutputFile(voiceStoragePath);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }



    private void signInAnonymously() {
        try {
            mAuth.signInAnonymously().addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    // do your stuff
                }
            }).addOnFailureListener(this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.e("TAG", "signInAnonymously:FAILURE", exception);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }



}
