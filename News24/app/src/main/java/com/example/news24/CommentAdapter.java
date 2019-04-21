package com.example.news24;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class CommentAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private String[] comments;
    private String[] users;
    private String[] time;


    //int[]

    public CommentAdapter(Context cx, String[] comments, String[] users, String[] time){
        this.comments = comments;
        this.users = users;
        this.time = time;
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
        View v = mInflater.inflate(R.layout.comment_listview_detaill, null);
        TextView userTW = v.findViewById(R.id.commPosterTW);
        TextView dateTW = v.findViewById(R.id.commDateTW);
        TextView commTextTW = v.findViewById(R.id.commTextTW);
        Button likeBtn = v.findViewById(R.id.commLikeBtn);
        Button dislikeBtn = v.findViewById(R.id.commDislBtn);

        String comment = comments[position];
        String user = users[position];
        String date = time[position];

        userTW.setText(user);
        dateTW.setText(date);
        commTextTW.setText(comment);

        return v;
    }
}
