package com.example.john.rapezeroapp.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.john.rapezeroapp.R;
import com.example.john.rapezeroapp.activities.edits.Edit_Personel;
import com.example.john.rapezeroapp.db_operations.Personel;
import com.example.john.rapezeroapp.util.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by john on 2/21/18.
 */

public class Profile extends AppCompatActivity {
    private TextView text_name,text_description,text_contact,text_location, text_district, text_username,text_edit;
    private Spinner input_district;
    private Button submit_btn;
    List<String> lists = new ArrayList<>();
    List<Integer> lists_id = new ArrayList<>();
    private Context context = this;
    String username, password;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        text_name = (TextView) findViewById(R.id.text_name);
        text_description = (TextView) findViewById(R.id.text_description);
        text_contact = (TextView) findViewById(R.id.text_contact);
        text_location = (TextView) findViewById(R.id.text_location);
        text_district = (TextView) findViewById(R.id.text_district);
        text_username = (TextView) findViewById(R.id.text_username);
        text_edit = (TextView) findViewById(R.id.text_edit);

        setAllValues();


        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        text_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Edit_Personel.class);
                intent.putExtra("username", username);
                intent.putExtra("password", password);
                startActivity(intent);
            }
        });

    }
    public void setAllValues(){
        try{
            Cursor cursor = new Personel(context).selectAll();
            if (cursor.moveToFirst()){
                do {
                    text_name.setText(cursor.getString(cursor.getColumnIndex(Constants.config.PERSONEL_NAME)));
                    text_description.setText(cursor.getString(cursor.getColumnIndex(Constants.config.PERSONEL_DESCRIPTION)));
                    text_contact.setText(cursor.getString(cursor.getColumnIndex(Constants.config.PERSONEL_CONTACT)));
                    text_location.setText(cursor.getString(cursor.getColumnIndex(Constants.config.CURRENT_LOCATION)));
                    text_username.setText(cursor.getString(cursor.getColumnIndex(Constants.config.PERSONEL_NAME)));
                    text_district.setText(cursor.getString(cursor.getColumnIndex(Constants.config.DISTRICT_NAME)));
                    text_username.setText(cursor.getString(cursor.getColumnIndex(Constants.config.USERNAME)));
                    username =  cursor.getString(cursor.getColumnIndex(Constants.config.USERNAME));
                    password =  cursor.getString(cursor.getColumnIndex(Constants.config.PASSWORD));
                }while (cursor.moveToNext());
            }
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
}
