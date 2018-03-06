package com.example.john.rapezeroapp.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;


public class Internet {
    private final static String TAG = "Internet";
    private  Context context;
    public Internet(Context context){
        this.context = context;
    }
    public  boolean check() {
        // get Connectivity Manager object to check connection
        ConnectivityManager connec =(ConnectivityManager)context.getSystemService(context.CONNECTIVITY_SERVICE);

        // Check for network connections
        if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||

                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {

            Log.e(TAG,"You have Intenet Connection!" );
            ////TODO: Fetch data whenever there is internet connection....
            return true;
        }else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {
            //TODO: No internet connections
            Log.e(TAG,"No Intenet Connection!" );
            ///
            return false;
        }
        return false;
    }
}
