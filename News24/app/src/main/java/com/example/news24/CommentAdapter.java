package com.example.news24;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class CommentAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private String[] comments;

    //int[]

    public CommentAdapter(Context cx, String[] ar){
        comments = ar;
        mInflater = (LayoutInflater) cx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return comments.length;
    }

    @Override
    public Object getItem(int position) {
        return comments[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //View v = mInflater.inflate(R.layout.)

        return null;
    }
}
