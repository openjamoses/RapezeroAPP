package com.example.john.rapezeroapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.john.rapezeroapp.activities.AcceptCallActivity;
import com.example.john.rapezeroapp.activities.BluetoothActivity;
import com.example.john.rapezeroapp.activities.ConcentActivity;
import com.example.john.rapezeroapp.activities.DistrictActivity;
import com.example.john.rapezeroapp.activities.Emmergency_Activity;
import com.example.john.rapezeroapp.activities.Friend_Activity;
import com.example.john.rapezeroapp.activities.Lcs_Activity;
import com.example.john.rapezeroapp.activities.PoliceActivity;
import com.example.john.rapezeroapp.activities.Profile;
import com.example.john.rapezeroapp.activities.RecieveBluetooth;
import com.example.john.rapezeroapp.bluetooth.DeviceListActivity;
import com.example.john.rapezeroapp.core.SharedPreference;
import com.example.john.rapezeroapp.firebase.DeviceToken;
import com.example.john.rapezeroapp.firebase.NotificationUtils;
import com.example.john.rapezeroapp.util.DateTime;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import static com.example.john.rapezeroapp.firebase.Config.PUSH_NOTIFICATION;
import static com.example.john.rapezeroapp.firebase.Config.REGISTRATION_COMPLETE;
import static com.example.john.rapezeroapp.firebase.Config.TOPIC_GLOBAL;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Context context = this;
    private Button personel_btn,friend_btn,other_btn,police_btn,lc_btn;
    int mFlipping = 0 ; // Initially flipping is off
    private SharedPreference sharedPreferenceObj; // Declare Global
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private static final String TAG = "Main";
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        try{
            mRegistrationBroadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                    // startService(new Intent(getApplicationContext(), MyFirebaseInstanceIDService.class));
                    //startService(new Intent(getApplicationContext(), MyFirebaseMessagingService.class));

                    // checking for type intent filter
                    if (intent.getAction().equals(REGISTRATION_COMPLETE)) {
                        // gcm successfully registered
                        // now subscribe to `global` topic to receive app wide notifications
                        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC_GLOBAL);
                        displayFirebaseRegId();
                    } else if (intent.getAction().equals(PUSH_NOTIFICATION)) {
                        // new push notification is received
                        String message = intent.getStringExtra("message");

                        Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();
                        //txtMessage.setText(message);
                    }
                }
            };
        }catch (Exception e){
            e.printStackTrace();
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Snackbar snackbar = Snackbar.make(view, getResources().getString(R.string.help), Snackbar.LENGTH_LONG).setDuration(50000);

                       /** .setAction("Action", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        }).show();
                        **/

                TextView snackbarActionTextView = (TextView) snackbar.getView().findViewById( android.support.design.R.id.snackbar_action );
                snackbarActionTextView.setTextSize( 20 );
                snackbarActionTextView.setTypeface(snackbarActionTextView.getTypeface(), Typeface.BOLD);

                TextView snackbarTextView = (TextView) snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
                snackbarTextView.setTextSize( 16 );
                snackbarTextView.setMaxLines( 10 );
                snackbarActionTextView.setBackground(getResources().getDrawable(R.drawable.round));
                snackbarTextView.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                snackbar.show();
                snackbar.setAction("X", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        snackbar.dismiss();
                    }
                });


            }
        });




        try{
            sharedPreferenceObj = new SharedPreference(MainActivity.this);
            //sharedPreferenceObj.setApp_runFirst(false, DateTime.getCurrentTime());
        }catch (Exception e){
            e.printStackTrace();
        }
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
            }
        });
        //ViewFlipper flipper = (ViewFlipper) findViewById(R.id.flipper1);
        if(mFlipping==0){
            /** Start Flipping */
            //flipper.startFlipping();
            mFlipping=1;
            // mButton.setText(R.string.str_btn_stop);
        }
        else{
            /** Stop Flipping */
            //flipper.stopFlipping();
            mFlipping=0;
            // mButton.setText(R.string.str_btn_start);
        }
        police_btn = (Button) findViewById(R.id.police_btn);
        friend_btn = (Button) findViewById(R.id.friend_btn);
        lc_btn = (Button) findViewById(R.id.lc_btn);
        other_btn = (Button) findViewById(R.id.other_btn);
        //police_btn = (Button) findViewById(R.id.police_btn);

        police_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PoliceActivity.class);
                startActivity(intent);
            }
        });

        friend_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Friend_Activity.class);
                startActivity(intent);
            }
        });
        lc_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Lcs_Activity.class);
                startActivity(intent);
            }
        });
        other_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Emmergency_Activity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    ///// TODO: 11/15/17  To recieve calls ...!!!! 
    public void recieveCalls(){
        Intent intent = new Intent(context, AcceptCallActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK
                | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(intent);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(context,Profile.class));
            return true;
        }

        if (id == R.id.action_test) {
            startActivity(new Intent(context, ConcentActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void displayFirebaseRegId() {
        //SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = new DeviceToken(context).token();
        if(regId == null) {
            regId = FirebaseInstanceId.getInstance().getToken();
        }
        Log.e(TAG, "Firebase reg id: " + regId);

        if (!TextUtils.isEmpty(regId))
            //txtRegId.setText("Firebase Reg Id: " + regId);
            Log.e(TAG,"Firebase Reg Id: " + regId);
        else
            //txtRegId.setText("Firebase Reg Id is not received yet!");
            Log.e(TAG,"Firebase Reg Id is not received yet!");
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_police) {
            // Handle the camera action
        } else if (id == R.id.nav_others) {
            startActivity(new Intent(context, RecieveBluetooth.class));
        } else if (id == R.id.nav_district) {
            startActivity(new Intent(context, DistrictActivity.class));
        }else if (id == R.id.nav_bluetooth) {
            startActivity(new Intent(context, DeviceListActivity.class));
        } else if (id == R.id.nav_exit) {
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.END);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        try{
            // register GCM registration complete receiver
            LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                    new IntentFilter(REGISTRATION_COMPLETE));
            // register new push message receiver
            // by doing this, the activity will be notified each time a new message arrives
            LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                    new IntentFilter(PUSH_NOTIFICATION));

            // clear the notification area when the app is opened
            NotificationUtils.clearNotifications(getApplicationContext());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        try{
            LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
