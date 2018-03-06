package com.example.john.rapezeroapp.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.john.rapezeroapp.R;
import com.example.john.rapezeroapp.activities.edits.Edit_Emmergency;
import com.example.john.rapezeroapp.activities.edits.Edit_LCS;
import com.example.john.rapezeroapp.adapters.Other_Adapters;
import com.example.john.rapezeroapp.db_operations.Lcs;
import com.example.john.rapezeroapp.db_operations.Police;
import com.example.john.rapezeroapp.util.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by john on 11/6/17.
 */

public class Lcs_Activity extends AppCompatActivity{

    private ListView listView;
    private Context context = this;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitylcs);
        listView = (ListView) findViewById(R.id.listView);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(context,Lcs_Entry.class);
                startActivity(intent);
                finish();
            }
        });
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        setValues();
    }

    private void setValues() {
        List<String> name = new ArrayList<>();
        List<String> desc = new ArrayList<>();
        List<String> contact = new ArrayList<>();
        final List<Integer> id_ = new ArrayList<>();
        final List<String> district_ = new ArrayList<>();
        try{
            Cursor cursor = new Lcs(context).selectAll();
            if (cursor.moveToFirst()){
                do {
                    name.add(cursor.getString(cursor.getColumnIndex(Constants.config.LC_NAME)));
                    desc.add(cursor.getString(cursor.getColumnIndex(Constants.config.LC_TYPE)));
                    contact.add(cursor.getString(cursor.getColumnIndex(Constants.config.PHONE_CONTACT)));
                    id_.add(cursor.getInt(cursor.getColumnIndex(Constants.config.LC_ID)));
                    district_.add(cursor.getString(cursor.getColumnIndex(Constants.config.DISTRICT_NAME)));
                }while (cursor.moveToNext());
            }
            Other_Adapters adapters = new Other_Adapters(context,name,desc,contact,id_,district_,"lc");
            listView.setAdapter(adapters);

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
