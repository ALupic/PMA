package com.example.news24;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

public class CommentAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private String[] commentContent;
    private String[] users;
    private String[] time;
    private int[] likes;
    private int[] dislikes;
    private int[] commentId;

    ToggleButton commLikeBtn;
    ToggleButton commDislBtn;


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
        DatabaseHelper db = new DatabaseHelper(v.getContext());



        TextView userTW = v.findViewById(R.id.commPosterTW);
        TextView dateTW = v.findViewById(R.id.commDateTW);
        TextView commTextTW = v.findViewById(R.id.commTextTW);

        TextView commIDTW = v.findViewById(R.id.commIDTW);
        TextView likesTW = v.findViewById(R.id.commLikesTW);
        TextView dislikesTW = v.findViewById(R.id.commDisLikesTW);

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

       final Comment c = db.findCommentById(commentId[position]);



//
//        Button likeBtn = v.findViewById(R.id.commLikeBtn);
//        likeBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//               // System.out.println("NUM OF LIKES IS :::::::::" + likesTW.getText().toString());
//            }
//        });
//
//        Button dislikeBtn = v.findViewById(R.id.commDislBtn);
//
//

        //stele pocetak
        commLikeBtn = (ToggleButton) v.findViewById(R.id.commLikeBtn);
        commLikeBtn.setChecked(false);
        commLikeBtn.setBackgroundDrawable(ContextCompat.getDrawable(v.getContext().getApplicationContext(), R.drawable.like_off));

        commDislBtn = (ToggleButton) v.findViewById(R.id.commDislBtn);
        commDislBtn.setChecked(false);
        commDislBtn.setBackgroundDrawable(ContextCompat.getDrawable(v.getContext().getApplicationContext(), R.drawable.dislike_off));
    int pos =  2;

        commLikeBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                View v = mInflater.inflate(R.layout.comment_listview_detaill, null);
                TextView commIDTW = v.findViewById(R.id.commIDTW);
                DatabaseHelper db = new DatabaseHelper(v.getContext());
                commLikeBtn = (ToggleButton) v.findViewById(R.id.commLikeBtn);
                commDislBtn = (ToggleButton) v.findViewById(R.id.commDislBtn);
//                String a = commIDTW.getText().toString();
//                int id = Integer.parseInt(a);

                Comment comment = db.findCommentById(c.getId());

                int nbrLike = comment.getLikes();
                if (isChecked){

                    comment = db.likeCommentById(comment.getId(), nbrLike + 1);

                    commLikeBtn.setBackgroundDrawable(ContextCompat.getDrawable(v.getContext().getApplicationContext(), R.drawable.like_on));
                    commDislBtn.setBackgroundDrawable(ContextCompat.getDrawable(v.getContext().getApplicationContext(), R.drawable.dislike_off));
                    commDislBtn.setChecked(false);

                }else {

                    comment = db.likeCommentById(comment.getId(), nbrLike -1);

                    commLikeBtn.setBackgroundDrawable(ContextCompat.getDrawable(v.getContext().getApplicationContext(), R.drawable.like_off));
                }
                TextView likesTW = v.findViewById(R.id.commLikesTW);
                TextView dislikesTW = v.findViewById(R.id.commDisLikesTW);
                likesTW.setText(String.valueOf(comment.getLikes()));
                dislikesTW.setText(String.valueOf(comment.getDislikes()));

            }

        });

        commDislBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                View v = mInflater.inflate(R.layout.comment_listview_detaill, null);
                TextView commIDTW = v.findViewById(R.id.commIDTW);
                DatabaseHelper db = new DatabaseHelper(v.getContext());
                commLikeBtn = (ToggleButton) v.findViewById(R.id.commLikeBtn);
                commDislBtn = (ToggleButton) v.findViewById(R.id.commDislBtn);
//                String a = commIDTW.getText().toString();
//                int id = Integer.parseInt(a);

                Comment comment = db.findCommentById(c.getId());

                int nbrDislike = comment.getDislikes();
                if (isChecked){

                    comment = db.dislikeCommentById(comment.getId(), nbrDislike + 1);

                    commDislBtn.setBackgroundDrawable(ContextCompat.getDrawable(v.getContext().getApplicationContext(), R.drawable.dislike_on));
                    commLikeBtn.setBackgroundDrawable(ContextCompat.getDrawable(v.getContext().getApplicationContext(), R.drawable.like_off));
                    commLikeBtn.setChecked(false);

                }else {

                    comment = db.dislikeCommentById(comment.getId(), nbrDislike -1);

                    commDislBtn.setBackgroundDrawable(ContextCompat.getDrawable(v.getContext().getApplicationContext(), R.drawable.dislike_off));
                }
                TextView likesTW = v.findViewById(R.id.commLikesTW);
                TextView dislikesTW = v.findViewById(R.id.commDisLikesTW);
                likesTW.setText(String.valueOf(comment.getLikes()));
                dislikesTW.setText(String.valueOf(comment.getDislikes()));

            }

        });


        //stele kraj


        return v;
    }
}
