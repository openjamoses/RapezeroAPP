package com.example.john.rapezeroapp.activities.edits;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.john.rapezeroapp.R;
import com.example.john.rapezeroapp.db_operations.District;
import com.example.john.rapezeroapp.db_operations.Personel;
import com.example.john.rapezeroapp.util.Constants;

import net.rimoto.intlphoneinput.IntlPhoneInput;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by john on 11/3/17.
 */

public class Edit_Personel extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private EditText input_fname,input_description,input_location,input_username,input_password,input_confirm;
    private Spinner input_district;
    private Button submit_btn;
    private AwesomeValidation awesomeValidation;
    String password1,password2;
    private TextInputLayout con_layout,pass_layout;
    private RelativeLayout confim_layout;
    private Context context = this;
    List<String> lists = new ArrayList<>();
    List<Integer> lists_id = new ArrayList<>();
    IntlPhoneInput phoneInputView;
    String user, pass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_entry);

        input_fname = (EditText) findViewById(R.id.input_fname);
        input_description = (EditText) findViewById(R.id.input_description);
        phoneInputView = (IntlPhoneInput) findViewById(R.id.my_phone_input);
        input_location = (EditText) findViewById(R.id.input_location);
        input_username = (EditText) findViewById(R.id.input_username);
        input_password = (EditText) findViewById(R.id.input_password);
        input_confirm = (EditText) findViewById(R.id.input_confirm);
        input_district = (Spinner) findViewById(R.id.input_district);
        submit_btn = (Button) findViewById(R.id.submit_btn);
        confim_layout = (RelativeLayout) findViewById(R.id.confim_layout);
        input_district.setOnItemSelectedListener(this);

        con_layout = (TextInputLayout) findViewById(R.id.con_layout);
        pass_layout = (TextInputLayout) findViewById(R.id.pass_layout);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        user = getIntent().getStringExtra("username");
        pass = getIntent().getStringExtra("password");

        setValues();
        setAllValues();

        final String pattern = "\\d{10}|(?:\\d{3})";
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        //awesomeValidation.addValidation(this, R.id.input_description, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.enter_valid_name);
        awesomeValidation.addValidation(this, R.id.input_fname, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.enter_valid_name);
        awesomeValidation.addValidation(this, R.id.input_username, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.atleast_6character);
        awesomeValidation.addValidation(this, R.id.input_location, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.enter_valid_name);

        //submit_btn.setEnabled(false);

        input_password.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                password1 = input_password.getText().toString().trim();
                // you can call or do what you want with your EditText here
                if (password1.length() > 5){
                    input_confirm.setEnabled(true);
                    pass_layout.setErrorEnabled(false);
                }else {
                    input_confirm.setEnabled(false);
                    pass_layout.setError("Atleast 6 characters!");
                    pass_layout.setErrorEnabled(true);
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
        input_confirm.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                password2 = input_confirm.getText().toString().trim();
                // you can call or do what you want with your EditText here
                if (password2.equals(password1)){
                    submit_btn.setEnabled(true);
                    con_layout.setErrorEnabled(false);
                }else {
                    submit_btn.setEnabled(false);
                    con_layout.setError("Passwords doesn't match!");
                    con_layout.setErrorEnabled(true);
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int district_id = 0;
                String fname = input_fname.getText().toString().trim();
                String descriptions = input_description.getText().toString().trim();
                String contact =  phoneInputView.getNumber();
                String location = input_location.getText().toString().trim();
                String district = input_district.getSelectedItem().toString().trim();
                String username = input_username.getText().toString().trim();

                if (phoneInputView.isValid()) {
                    for (int i = 0; i < lists.size(); i++) {
                        if (district.equals(lists.get(i))) {
                            district_id = lists_id.get(i);
                        }
                    }
                    String password = input_password.getText().toString().trim();
                    if (awesomeValidation.validate()) {
                        String message = new Personel(context).edit(fname,contact,descriptions,location,district_id,password,username,user,pass);
                        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
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

    public void setAllValues(){
        try{
            Cursor cursor = new Personel(context).selectAll();
            if (cursor.moveToFirst()){
                do {
                    input_fname.setText(cursor.getString(cursor.getColumnIndex(Constants.config.PERSONEL_NAME)));
                    input_description.setText(cursor.getString(cursor.getColumnIndex(Constants.config.PERSONEL_DESCRIPTION)));
                    phoneInputView.setNumber(cursor.getString(cursor.getColumnIndex(Constants.config.PERSONEL_CONTACT)));
                    input_location.setText(cursor.getString(cursor.getColumnIndex(Constants.config.CURRENT_LOCATION)));
                    input_password.setText(cursor.getString(cursor.getColumnIndex(Constants.config.PERSONEL_NAME)));

                    int index = lists.indexOf(cursor.getString(cursor.getColumnIndex(Constants.config.DISTRICT_NAME)));
                    lists.set(index,lists.get(0));
                    lists.set(0,cursor.getString(cursor.getColumnIndex(Constants.config.DISTRICT_NAME)));
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
