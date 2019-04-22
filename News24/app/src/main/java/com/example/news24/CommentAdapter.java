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
    private String[] commentContent;
    private String[] users;
    private String[] time;
    private int[] likes;
    private int[] dislikes;
    private int[] commentId;


    public CommentAdapter(Context cx, String[] commentContent, String[] users, String[] time, int[] likes, int[] dislikes, int[] commentId){
        this.commentContent = commentContent;
        this.users = users;
        this.time = time;
        this.likes = likes;
        this.dislikes = dislikes;
        this.commentId = commentId;
        mInflater = (LayoutInflater) cx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return commentContent.length;
    }

    @Override
    public Object getItem(int position) {
        return commentContent[position];
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
        final TextView likesTW = v.findViewById(R.id.commLikesTW);
        TextView dislikesTW = v.findViewById(R.id.commDisLikesTW);
        TextView commIDTW = v.findViewById(R.id.commIDTW);

        Button likeBtn = v.findViewById(R.id.commLikeBtn);
        likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("NUM OF LIKES IS :::::::::" + likesTW.getText().toString());
            }
        });

        Button dislikeBtn = v.findViewById(R.id.commDislBtn);

        String comment = commentContent[position];
        String user = users[position];
        String date = time[position];
        String numLikes = Integer.toString(likes[position]);
        String numDislikes = Integer.toString(dislikes[position]);
        String cId = Integer.toString(commentId[position]);

        userTW.setText(user);
        dateTW.setText(date);
        commTextTW.setText(comment);
        likesTW.setText(numLikes);
        dislikesTW.setText(numDislikes);
        commIDTW.setText(cId);


        return v;
    }
}
