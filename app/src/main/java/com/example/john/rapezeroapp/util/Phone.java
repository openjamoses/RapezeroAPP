package com.example.john.rapezeroapp.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by john on 10/4/17.
 */

public class Phone {

    public static String getIMEI(Context context){
        String imei = "";
        try {
            TelephonyManager mngr = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
             imei = mngr.getDeviceId();
        }catch (Exception e){
            e.printStackTrace();
        }
        return imei;
    }

    public static String getInstallDate(Context context) {
        // get app installation date
        String firstInstallDate = "";
        String lastInsatllDate = "";
        String versionName = "";
        String applicationName;
        String filesizes = "";
        //
        String allInfo = "";
        try {
            // create package manager instance first
            PackageManager pm = context.getPackageManager();
            // ======= get first installation date
            long installed = pm.getPackageInfo(context.getPackageName(), 0).firstInstallTime;
            Date installedDate = new Date(installed);

            // create a date time formatter
            SimpleDateFormat formatter = new SimpleDateFormat(
                    "yyyy-MM-dd");
            firstInstallDate = formatter.format(installedDate);

            allInfo += "1st installation date:" + firstInstallDate + " \n";

            // ======= get last installation date =========

            ApplicationInfo appInfo = pm.getApplicationInfo(
                    context.getPackageName(), 0);
            String appFile = appInfo.sourceDir;
            long lastinstalled = new File(appFile).lastModified();
            Date lastUpdateObj = new Date(lastinstalled);
            lastInsatllDate = formatter.format(lastUpdateObj);
            allInfo += "Last installion date:" + lastInsatllDate + " \n";

            // ======== get version number ==========

            versionName = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionName;
            allInfo += "Current version Name: " + versionName + " \n";

            // ======== app name ==========

            applicationName = (String) (appInfo != null ? pm
                    .getApplicationLabel(appInfo) : "(unknown)");
            allInfo += "Application Name: " + applicationName + " \n";

            // ======== app file size (in bytes)  ============

            long fileSize = new FileInputStream(appInfo.sourceDir).getChannel()
                    .size();
            filesizes = String.valueOf(fileSize);
            allInfo += "Application Size: " + filesizes + " bytes\n";

            // set allInfo string as TextViews text
            //textView_app_details.setText(allInfo);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return firstInstallDate;
    }
}
