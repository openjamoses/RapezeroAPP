package com.example.john.rapezeroapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.john.rapezeroapp.R;

import java.util.List;

/**
 * Created by john on 11/3/17.
 */

public class Concent_Adapter extends BaseAdapter {
    Context context;
    List<String> name,title,contact, district;
    LayoutInflater inflter;
    private Button pick_time,save_appointment;
    //List<String> list = new ArrayList<>();
    List<String> body;
    public Concent_Adapter(Context applicationContext, List<String> name, List<String> title,List<String> contact,List<String> district) {
        this.context = applicationContext;

        this.name = name;
        this.title = title;
        this.contact = contact;
        this.district = district;

        inflter = (LayoutInflater.from(applicationContext));
    }
    @Override
    public int getCount() {
        return name.size();
    }
    @Override
    public Object getItem(int i) {
        return null;
    }
    @Override
    public long getItemId(int i) {
        return 0;
    }
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.confirm_lists, null);
        try {
            TextView text_name = (TextView) view.findViewById(R.id.text_name);
            TextView text_title = (TextView) view.findViewById(R.id.text_title);
            TextView text_contact = (TextView) view.findViewById(R.id.text_contact);
            TextView text_district = (TextView) view.findViewById(R.id.text_district);

            text_name.setText(name.get(i));
            text_title.setText(title.get(i));
            text_contact.setText(contact.get(i));
            text_district.setText(district.get(i));

        }catch (Exception e){
            e.printStackTrace();
        }


        return view;
    }
}