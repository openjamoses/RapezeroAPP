package com.example.john.rapezeroapp.core;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.john.rapezeroapp.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by john on 10/15/17.
 */

public class Demo {

    public static boolean show(final Context context){
        final boolean[] activate = {false};
        try {
            new SweetAlertDialog(context, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                    .setTitleText("PRISMS Activations!")
                    .setCustomImage(context.getResources().getDrawable(R.drawable.icon))
                    .setContentText("Seems...\nYou have NOT Activated PRISMS!\n Would you like to Activate PRISMS?")
                    .setCancelText("No,cancel")
                    .setConfirmText("Yes,confirm")
                    .showCancelButton(true)
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.cancel();


                        }
                    })
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            activate[0] = true;
                        }
                    })
                    .show();
        }catch (Exception e){
            e.printStackTrace();
        }
        return activate[0];
    }

    private void openDialog(Context context){

    }
}
