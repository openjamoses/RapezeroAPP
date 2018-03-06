package com.example.john.rapezeroapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.john.rapezeroapp.MainActivity;
import com.example.john.rapezeroapp.R;
import com.example.john.rapezeroapp.bluetooth.DeviceListActivity;
import com.example.john.rapezeroapp.db_operations.Personel;

/**
 * Created by john on 11/12/17.
 */

public class LoginActivity extends AppCompatActivity {

    private EditText input_username,input_password;
    private Button btn_login;
    private Context context = this;
    private TextView text_signup;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        input_username = (EditText) findViewById(R.id.input_username);
        input_password = (EditText) findViewById(R.id.input_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        text_signup = (TextView) findViewById(R.id.text_signup);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = input_username.getText().toString().trim();
                String password = input_password.getText().toString().trim();

                String message = new Personel(context).login(username,password);
                if (message.equals("Login Successfuly!")){
                    startActivity(new Intent(context, MainActivity.class));
                }else {
                    Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
                }
            }
        });


        text_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,Personel_Entry.class));
                //finish();
            }
        });

        //TODO: To be removed soon...!
        Button btn_bluetooth = (Button) findViewById(R.id.btn_bluetooth);
        btn_bluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, DeviceListActivity.class));
            }
        });
    }
}
