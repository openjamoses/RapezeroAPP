package com.example.john.rapezeroapp.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

/**
 * Created by john on 9/21/17.
 */

public class CompressBitmap {
    static Bitmap  bitmap2;
    static ByteArrayOutputStream bytearrayoutputstream;
    static byte[] BYTE;
    public static Bitmap compress(Bitmap bitmap){
        try{
            bytearrayoutputstream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,40,bytearrayoutputstream);
            BYTE = bytearrayoutputstream.toByteArray();
            bitmap2 = BitmapFactory.decodeByteArray(BYTE,0,BYTE.length);
        }catch (Exception e){
            e.printStackTrace();
        }

        return bitmap2;

    }
}
