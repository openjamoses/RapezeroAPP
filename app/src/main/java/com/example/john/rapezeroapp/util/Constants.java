package com.example.john.rapezeroapp.util;

/**
 * Created by john on 7/8/17.
 */

public class Constants {

    public abstract class config{
        public static final String DB_NAME = "rapezero_app";
        public static final int DB_VERSION = 1;
        /****** URL DECLARATION ******************************/
        public static final String URL_MODEM = "http://10.127.146.235/";
        public static final String URL_PHONE = "http://192.168.43.18/";
        public static final String URL_CAMTECH = "http://137.63.161.41/";
        public static final String URL_SERVER = "http://173.255.219.164/";
        public static final String HOST_URL = URL_MODEM+"Rapezero/mobile_connections/";

        public static final String IMEI = "IMEI";
        public static final String INSTALL_DATE = "install_date";
        public static final String APP_VERSION = "app_version";
        //// TODO: 10/12/17   ACTIVATIONS
        public static final String POLICE_ID = "police_id";
        public static final String TABLE_POLICE = "police";
        public static final String POLICE_NAME = "police_name";
        public static final String POLICE_TYPE = "police_type";

        public static final String DISTRICT_ID = "district";
        public static final String TABLE_DISTRICT = "district";
        public static final String DISTRICT_NAME = "district_name";
        public static final String KEY_TOKEN = "regId";

        public static final String LC_ID = "lc_id";
        public static final String TABLE_LC = "lc";
        public static final String LC_NAME = "lc_name";
        public static final String LC_TYPE = "lc_type";

        public static final String DATE = "date";
        public static final String TIME = "time";

        public static final String FRIEND_ID = "friend_id";
        public static final String TABLE_FRIEND = "friend";
        public static final String FRIEND_NAME = "friend_name";
        public static final String FRIEND_TYPE = "friend_type";

        public static final String EMERGENCY_ID = "emergency_id";
        public static final String TABLE_EMERGENCY = "emergency";
        public static final String EMERGENCY_NAME = "emergency_name";
        public static final String EMERGENCY_TYPE = "emergency_type";

        public static final String PERSONEL_ID = "personel_id";
        public static final String TABLE_PERSONEL = "personel";
        public static final String PERSONEL_NAME = "personel_name";
        public static final String PERSONEL_CONTACT = "personel_contact";
        public static final String PERSONEL_DESCRIPTION = "personel_descriptions";
        public static final String CURRENT_LOCATION = "current_location";
        public static final String PASSWORD = "password";

        public static final String USERNAME = "username";
        public static final String PHONE_CONTACT = "phone_contact";
        public static final String FILE_UPLOAD_URL = "sync_data/fileUploads.php";

        public static final String FIREBASE_URL = "https://foodscanner-466e0.firebaseio.com/";
        public static final String FIREBASE_UPLOADS = "uploads/";
        public static final String STORAGE_REFERENCE = "gs://foodscanner-466e0.appspot.com/";
        //public static final String APP_FOLDER = "RapeZero_APP";


        public static final String APP_FOLDER = "RapeZero_APP";
        public static final String IMAGE_SUB_FOLDER = "images";
        public static final String VIDEO_SUB_FOLDER = "videos";
        public static final String AUDIO_SUB_FOLDER = "audio";
    }
}
