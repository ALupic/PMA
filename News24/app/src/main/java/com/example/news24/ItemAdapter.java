package com.example.news24;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemAdapter extends BaseAdapter {

    LayoutInflater mInflater;
    String[] articles;
    String[] categories;
    String[] images;
    DatabaseHelper db;

    int[] imageIds = {
            R.drawable.article1,
            R.drawable.article2,
            R.drawable.article3
    };

    public ItemAdapter(Context cx, String[] ar, String[] cat, String[] img){
        articles = ar;
        categories = cat;
        images = img;
        mInflater = (LayoutInflater) cx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        db = new DatabaseHelper(cx);
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
        ImageView articleImageView = v.findViewById(R.id.articleImageView);

        String ar = articles[position];
        String cat = categories[position];
        String img = images[position];

        titleTextView.setText(ar);
        categoryTextView.setText(cat);
        System.out.println("----------------- : ");
        System.out.println(position);
        //articleImageView.setImageResource()
        if(position==0){
            articleImageView.setImageResource(imageIds[0]);
        }else if(position==1){
            articleImageView.setImageResource(imageIds[1]);
        }else{
            articleImageView.setImageResource(imageIds[2]);
        }

        return v;
    }
}
