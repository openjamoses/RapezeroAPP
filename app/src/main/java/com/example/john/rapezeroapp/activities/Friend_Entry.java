package com.example.john.rapezeroapp.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.john.rapezeroapp.R;
import com.example.john.rapezeroapp.db_operations.District;
import com.example.john.rapezeroapp.db_operations.Friend;
import com.example.john.rapezeroapp.db_operations.Lcs;
import com.example.john.rapezeroapp.util.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by john on 10/19/17.
 */

public class Friend_Entry extends AppCompatActivity {

    private EditText input_fname,input_contact;
    private Spinner input_district;
    private Button submit_btn;
    private AwesomeValidation awesomeValidation;
    List<String> lists = new ArrayList<>();
    List<Integer> lists_id = new ArrayList<>();
    private Context context = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_friend_entry);
        input_fname = (EditText) findViewById(R.id.input_fname);
        //input_description = (EditText) findViewById(R.id.input_description);
        input_contact = (EditText) findViewById(R.id.input_contact);
        input_district = (Spinner) findViewById(R.id.input_district);
        submit_btn = (Button) findViewById(R.id.submit_btn);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        setValues();

        final String pattern = "\\d{10}|(?:\\d{3})";
        ///Todo ......!!! String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        awesomeValidation.addValidation(this, R.id.input_description, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.enter_valid_name);
        awesomeValidation.addValidation(this, R.id.input_contact, pattern, R.string.inavid_number);
        awesomeValidation.addValidation(this, R.id.input_fname, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.enter_valid_name);



        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int district_id = 0;
                String fname = input_fname.getText().toString().trim();
                String descriptions = "close friend";
                String contact = input_contact.getText().toString().trim();
                String district = input_district.getSelectedItem().toString().trim();

                for (int i=0; i<lists.size(); i++){
                    if (district.equals(lists.get(i))){
                        district_id = lists_id.get(i);
                    }
                }
                if (awesomeValidation.validate()) {
                    String message = new Friend(context).save(fname,contact,descriptions,district_id);
                    Toast.makeText(context,message,Toast.LENGTH_SHORT).show();

                    if (message.equals("friend cases saved!")){
                        startActivity(new Intent(context,Friend_Activity.class));
                        finish();
                    }
                }
            }
        });
    }

    private void setValues() {
        try{
            Cursor cursor = new District(context).selectAll();
            if (cursor.moveToFirst()){
                do {
                    lists.add(cursor.getString(cursor.getColumnIndex(Constants.config.DISTRICT_NAME)));
                    lists_id.add(cursor.getInt(cursor.getColumnIndex(Constants.config.DISTRICT_ID)));
                }while (cursor.moveToNext());
            }

            ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, lists);
            dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            input_district.setAdapter(dataAdapter2);
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
