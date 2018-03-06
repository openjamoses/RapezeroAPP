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
import com.example.john.rapezeroapp.activities.edits.Edit_Emmergency;
import com.example.john.rapezeroapp.activities.edits.Edit_Friends;
import com.example.john.rapezeroapp.activities.edits.Edit_LCS;
import com.example.john.rapezeroapp.activities.edits.Edit_Police;

import java.util.List;

/**
 * Created by john on 11/6/17.
 */

public class Other_Adapters extends BaseAdapter {
    Context context;
    List<String> name,desc,contact,district;
    List<Integer> id_;
    String type;
    LayoutInflater inflter;
    public Other_Adapters(Context applicationContext, List<String> name, List<String> desc, List<String> contact,List<Integer> id_, List<String> district, String type) {
        this.context = applicationContext;
        this.name = name;
        this.desc = desc;
        this.contact = contact;
        this.id_ = id_;
        this.district = district;
        this.type = type;

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
        view = inflter.inflate(R.layout.other_lists, null);
        try {

            RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.layout);
            TextView textView = (TextView) view.findViewById(R.id.text_name);
            TextView textView2 = (TextView) view.findViewById(R.id.text_desc);
            TextView textView3 = (TextView) view.findViewById(R.id.text_contact);
            textView.setText(name.get(i));
            textView2.setText(desc.get(i));
            textView3.setText(contact.get(i));

            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (type.equals("police")){
                        Intent intent = new Intent(context, Edit_Police.class);
                        intent.putExtra("id",String.valueOf(id_.get(i)));
                        intent.putExtra("district",district.get(i));
                        context.startActivity(intent);
                    }else if (type.equals("friend")){
                        Intent intent = new Intent(context, Edit_Friends.class);
                        intent.putExtra("id",String.valueOf(id_.get(i)));
                        intent.putExtra("district",district.get(i));
                        context.startActivity(intent);
                    }else if (type.equals("lc")){
                        Intent intent = new Intent(context, Edit_LCS.class);
                        intent.putExtra("id",String.valueOf(id_.get(i)));
                        intent.putExtra("district",district.get(i));
                        context.startActivity(intent);
                    }else if (type.equals("emergency")){
                        Intent intent = new Intent(context, Edit_Emmergency.class);
                        intent.putExtra("id",String.valueOf(id_.get(i)));
                        intent.putExtra("district",district.get(i));
                        context.startActivity(intent);
                    }

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return view;
    }
}
