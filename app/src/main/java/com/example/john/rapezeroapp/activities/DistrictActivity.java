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
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.john.rapezeroapp.R;
import com.example.john.rapezeroapp.activities.edits.Edit_District;
import com.example.john.rapezeroapp.adapters.DistrictAdapter;
import com.example.john.rapezeroapp.db_operations.District;
import com.example.john.rapezeroapp.util.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by john on 10/20/17.
 */

public class DistrictActivity extends AppCompatActivity {
    private ListView listView;
    private Context context = this;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_district);

        listView = (ListView) findViewById(R.id.listView);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(context,District_Entry.class);
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
        List<String> lists = new ArrayList<>();
        final List<Integer> id_ = new ArrayList<>();
        try{
            Cursor cursor = new District(context).selectAll();
            if (cursor.moveToFirst()){
                do {
                    lists.add(cursor.getString(cursor.getColumnIndex(Constants.config.DISTRICT_NAME)));
                    id_.add(cursor.getInt(cursor.getColumnIndex(Constants.config.DISTRICT_ID)));
                }while (cursor.moveToNext());
            }

            DistrictAdapter adapter = new DistrictAdapter(context,lists,id_);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(context, Edit_District.class);
                    intent.putExtra("id",String.valueOf(id_.get(i)));
                    startActivity(intent);
                    finish();
                }
            });

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
