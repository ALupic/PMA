package com.example.news24;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ItemAdapter extends BaseAdapter {

    LayoutInflater mInflater;
    String[] articles;
    String[] categories;

    public ItemAdapter(Context cx, String[] ar, String[] cat){
        articles = ar;
        categories = cat;
        mInflater = (LayoutInflater) cx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return articles.length;
    }

    @Override
    public Object getItem(int position) {
        return articles[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = mInflater.inflate(R.layout.my_listview_detail, null);
        TextView titleTextView = v.findViewById(R.id.titleTextView);
        TextView categoryTextView = v.findViewById(R.id.categoryTextView);

        String ar = articles[position];
        String cat = categories[position];

        titleTextView.setText(ar);
        categoryTextView.setText(cat);

        return v;
    }
}
