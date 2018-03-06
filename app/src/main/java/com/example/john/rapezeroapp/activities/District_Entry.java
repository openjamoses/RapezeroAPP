package com.example.john.rapezeroapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.john.rapezeroapp.R;
import com.example.john.rapezeroapp.db_operations.District;

/**
 * Created by john on 10/20/17.
 */

public class District_Entry extends AppCompatActivity {
    private Button submit_btn;
    private Context context = this;
    private EditText input_district;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_district_entry);
        submit_btn = (Button) findViewById(R.id.submit_btm);
        input_district = (EditText) findViewById(R.id.input_district);
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void submit() {
        try {
            String name = input_district.getText().toString().trim();

            if (!name.equals("")) {
                String message = new District(context).save(name);
                Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
                View view = toast.getView();
                view.setBackgroundResource(R.drawable.round);
                TextView text = (TextView) view.findViewById(android.R.id.message);
                toast.show();
                input_district.setText("");
            } else {

            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            Intent intent =  new Intent(context,DistrictActivity.class);
            startActivity(intent);
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }
}
