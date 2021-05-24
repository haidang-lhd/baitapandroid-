package com.example.mediaplayer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class Adapter_listmain extends ArrayAdapter<String> {
    Context context;
    String[] items;

    public Adapter_listmain(Context context, int layoutTobeInflated, String[] items) {
        super(context, R.layout.adapter_listmain, items);
        this.context = context;
        this.items = items;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View row = inflater.inflate(R.layout.adapter_listmain, null);
        TextView text = (TextView) row.findViewById(R.id.textView);
        text.setText(items[position]);
        return row;
    }

}
