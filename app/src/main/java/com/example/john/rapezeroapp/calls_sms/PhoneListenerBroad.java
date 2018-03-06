package com.example.john.rapezeroapp.calls_sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;


import com.example.john.rapezeroapp.activities.AcceptCallActivity;
import com.example.john.rapezeroapp.db_operations.Emmergency;
import com.example.john.rapezeroapp.db_operations.Friend;
import com.example.john.rapezeroapp.db_operations.Lcs;
import com.example.john.rapezeroapp.db_operations.Police;
import com.example.john.rapezeroapp.util.Constants;
import com.example.john.rapezeroapp.util.DateTime;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by john on 9/8/17.
 */

public class PhoneListenerBroad extends BroadcastReceiver
{

    Context c;
    private String outgoing;
    long start = 0;
    long end = 0;

    @Override
    public void onReceive(Context context, Intent intent)
    {
        c = context;
        start = System.nanoTime();
        try
        {
            TelephonyManager tmgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            MyPhoneStateListener PhoneListener = new MyPhoneStateListener();
            tmgr.listen(PhoneListener, PhoneStateListener.LISTEN_CALL_STATE);
        }
        catch (Exception e)
        {
            Log.e("Phone Receive Error", " " + e);
        }

    }
    private class MyPhoneStateListener extends PhoneStateListener
    {
        int flag = 0;
        int f1 = 0;
        int flag2 = 0;
        int flag3;
        public void onCallStateChanged(final int state, final String incomingNumber)
        {
            try {

                String date = DateTime.getCurrentDate();
                String time = DateTime.getCurrentTime();
                String status = "0";
                final List<String> contact_list = getContacts();
                Log.e("INCOMMING CALLS", incomingNumber);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            if (incomingNumber != null){
                                if (contact_list.contains(incomingNumber)){
                                    Intent intent = new Intent(c, AcceptCallActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK
                                            | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                                    c.startActivity(intent);
                                }
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }, 5000);




                if (state == 0) {

                } else if (state == 1) {

                } else if (state == 2) {

                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }


        // Method to disconnect phone automatically and programmatically
        // Keep this method as it is
        @SuppressWarnings({ "rawtypes", "unchecked" })
        public void disconnectPhoneItelephony(Context context) {
            try {


                String serviceManagerName = "android.os.ServiceManager";
                String serviceManagerNativeName = "android.os.ServiceManagerNative";
                String telephonyName = "com.android.internal.telephony.ITelephony";

                Class telephonyClass;
                Class telephonyStubClass;
                Class serviceManagerClass;
                Class serviceManagerStubClass;
                Class serviceManagerNativeClass;
                Class serviceManagerNativeStubClass;

                Method telephonyCall;
                Method telephonyEndCall;
                Method telephonyAnswerCall;
                Method getDefault;

                Method[] temps;
                Constructor[] serviceManagerConstructor;

                // Method getService;
                Object telephonyObject;
                Object serviceManagerObject;
                telephonyClass = Class.forName(telephonyName);
                telephonyStubClass = telephonyClass.getClasses()[0];
                serviceManagerClass = Class.forName(serviceManagerName);
                serviceManagerNativeClass = Class.forName(serviceManagerNativeName);

                Method getService = // getDefaults[29];
                        serviceManagerClass.getMethod("getService", String.class);

                Method tempInterfaceMethod = serviceManagerNativeClass.getMethod(
                        "asInterface", IBinder.class);

                Binder tmpBinder = new Binder();
                tmpBinder.attachInterface(null, "fake");

                serviceManagerObject = tempInterfaceMethod.invoke(null, tmpBinder);
                IBinder retbinder = (IBinder) getService.invoke(serviceManagerObject, "phone");
                Method serviceMethod = telephonyStubClass.getMethod("asInterface", IBinder.class);

                telephonyObject = serviceMethod.invoke(null, retbinder);
                //telephonyCall = telephonyClass.getMethod("call", String.class);
                telephonyEndCall = telephonyClass.getMethod("endCall");
                //telephonyAnswerCall = telephonyClass.getMethod("answerRingingCall");

                telephonyEndCall.invoke(telephonyObject);

            } catch (Exception e) {
                e.printStackTrace();

            }
        }


        private List<String> getContacts() {
            List<String> contact_list = new ArrayList<>();
           try{
                Cursor cursor = new Police(c).selectAll();
                if (cursor.moveToFirst()){
                    do {
                        //name_list.add(cursor.getString(cursor.getColumnIndex(Constants.config.POLICE_NAME)));
                        //title_list.add("Police "+cursor.getString(cursor.getColumnIndex(Constants.config.POLICE_TYPE)));
                        contact_list.add(cursor.getString(cursor.getColumnIndex(Constants.config.PHONE_CONTACT)));
                        //district_list.add(cursor.getString(cursor.getColumnIndex(Constants.config.DISTRICT_NAME)));
                    }while (cursor.moveToNext());
                }
                cursor = new Lcs(c).selectAll();
                if (cursor.moveToFirst()){
                    do {
                        //name_list.add(cursor.getString(cursor.getColumnIndex(Constants.config.LC_NAME)));
                        //title_list.add("LC "+cursor.getString(cursor.getColumnIndex(Constants.config.LC_TYPE)));
                        contact_list.add(cursor.getString(cursor.getColumnIndex(Constants.config.PHONE_CONTACT)));
                        //district_list.add(cursor.getString(cursor.getColumnIndex(Constants.config.DISTRICT_NAME)));
                    }while (cursor.moveToNext());
                }
                cursor = new Emmergency(c).selectAll();
                if (cursor.moveToFirst()){
                    do {
                        //name_list.add(cursor.getString(cursor.getColumnIndex(Constants.config.EMERGENCY_NAME)));
                        //title_list.add("LC "+cursor.getString(cursor.getColumnIndex(Constants.config.EMERGENCY_TYPE)));
                        contact_list.add(cursor.getString(cursor.getColumnIndex(Constants.config.PHONE_CONTACT)));
                        //district_list.add(cursor.getString(cursor.getColumnIndex(Constants.config.DISTRICT_NAME)));
                    }while (cursor.moveToNext());
                }
                cursor = new Friend(c).selectAll();
                if (cursor.moveToFirst()){
                    do {
                        //name_list.add(cursor.getString(cursor.getColumnIndex(Constants.config.FRIEND_NAME)));
                        //title_list.add("LC "+cursor.getString(cursor.getColumnIndex(Constants.config.FRIEND_TYPE)));
                        contact_list.add(cursor.getString(cursor.getColumnIndex(Constants.config.PHONE_CONTACT)));
                        //district_list.add(cursor.getString(cursor.getColumnIndex(Constants.config.DISTRICT_NAME)));
                    }while (cursor.moveToNext());
                }

            }catch (Exception e){
                e.printStackTrace();
            }
            return contact_list;
        }
    }
}