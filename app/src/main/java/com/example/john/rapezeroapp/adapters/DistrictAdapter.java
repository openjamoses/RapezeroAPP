package com.example.john.rapezeroapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.john.rapezeroapp.R;
import com.example.john.rapezeroapp.activities.edits.Edit_District;

import java.util.List;

/**
 * Created by john on 10/13/17.
 */

public class DistrictAdapter extends BaseAdapter {
    Context context;
    List<String> name;
    List<Integer> id_;
    LayoutInflater inflter;
    private Button pick_time,save_appointment;
    //List<String> list = new ArrayList<>();
    List<String> body;


    public DistrictAdapter(Context applicationContext, List<String> name,List<Integer> id_) {
        this.context = applicationContext;
        this.name = name;
        this.id_ = id_;

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
        view = inflter.inflate(R.layout.content_lists, null);
        try {
            RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.layout);
            TextView textView = (TextView) view.findViewById(R.id.text_name);

            textView.setText(name.get(i));

            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, Edit_District.class);
                    intent.putExtra("id",String.valueOf(id_.get(i)));
                    context.startActivity(intent);
                    //finish();
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }


        return view;
    }
}
