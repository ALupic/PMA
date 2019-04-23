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
    private boolean[] likeButtons;
    private boolean[] dislikeButtons;

    ToggleButton commLikeBtn;
    ToggleButton commDislBtn;


    public CommentAdapter(Context cx, String[] commentContent, String[] users, String[] time, int[] likes, int[] dislikes, int[] commentId, boolean[] likeBtnPressed, boolean[] dislikeBtnPressed){
        this.commentContent = commentContent;
        this.users = users;
        this.time = time;
        this.likes = likes;
        this.dislikes = dislikes;
        this.commentId = commentId;
        this.likeButtons = likeBtnPressed;
        this.dislikeButtons = dislikeBtnPressed;

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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final View v = mInflater.inflate(R.layout.comment_listview_detaill, null);
        DatabaseHelper db = new DatabaseHelper(v.getContext());



        TextView userTW = v.findViewById(R.id.commPosterTW);
        TextView dateTW = v.findViewById(R.id.commDateTW);
        TextView commTextTW = v.findViewById(R.id.commTextTW);

        TextView commIDTW = v.findViewById(R.id.commIDTW);
        final TextView likesTW = v.findViewById(R.id.commLikesTW);
        final TextView dislikesTW = v.findViewById(R.id.commDisLikesTW);

        String comment = commentContent[position];
        String user = users[position];
        String date = time[position];
        String numLikes = Integer.toString(likes[position]);
        String numDislikes = Integer.toString(dislikes[position]);
        String cId = Integer.toString(commentId[position]);
        boolean likeButtonPressed = likeButtons[position];
        boolean dislikeButtonPressed = dislikeButtons[position];

        userTW.setText(user);
        dateTW.setText(date);
        commTextTW.setText(comment);
        likesTW.setText(numLikes);
        dislikesTW.setText(numDislikes);
        commIDTW.setText(cId);

       final Comment c = db.findCommentById(commentId[position]);

        //stele pocetak
        commLikeBtn = (ToggleButton) v.findViewById(R.id.commLikeBtn);
        commLikeBtn.setChecked(likeButtonPressed);
        if(!likeButtonPressed)
            commLikeBtn.setBackgroundDrawable(ContextCompat.getDrawable(v.getContext().getApplicationContext(), R.drawable.like_off));
        else
            commLikeBtn.setBackgroundDrawable(ContextCompat.getDrawable(v.getContext().getApplicationContext(), R.drawable.like_on));

        commDislBtn = (ToggleButton) v.findViewById(R.id.commDislBtn);
        commDislBtn.setChecked(dislikeButtonPressed);
        if(!dislikeButtonPressed)
            commDislBtn.setBackgroundDrawable(ContextCompat.getDrawable(v.getContext().getApplicationContext(), R.drawable.dislike_off));
        else
            commDislBtn.setBackgroundDrawable(ContextCompat.getDrawable(v.getContext().getApplicationContext(), R.drawable.dislike_on));

        commLikeBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DatabaseHelper db = new DatabaseHelper(v.getContext());
                commLikeBtn = (ToggleButton) v.findViewById(R.id.commLikeBtn);
                commDislBtn = (ToggleButton) v.findViewById(R.id.commDislBtn);//

                Comment comment = db.findCommentById(c.getId());

                int nbrLike = comment.getLikes();
                if (isChecked){
                    comment = db.likeCommentById(comment.getId(), nbrLike + 1);
                    likes[position] +=1;

                    commLikeBtn.setBackgroundDrawable(ContextCompat.getDrawable(v.getContext().getApplicationContext(), R.drawable.like_on));
                    likeButtons[position] = true;

                    commDislBtn.setBackgroundDrawable(ContextCompat.getDrawable(v.getContext().getApplicationContext(), R.drawable.dislike_off));
                    dislikeButtons[position] = false;
                    commDislBtn.setChecked(false);

                }else {

                    comment = db.likeCommentById(comment.getId(), nbrLike -1);
                    likes[position]-=1;
                    likeButtons[position] =false;
                    commLikeBtn.setBackgroundDrawable(ContextCompat.getDrawable(v.getContext().getApplicationContext(), R.drawable.like_off));
                }

                likesTW.setText(String.valueOf(comment.getLikes()));
                dislikesTW.setText(String.valueOf(comment.getDislikes()));

            }

        });

        commDislBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DatabaseHelper db = new DatabaseHelper(v.getContext());
                commLikeBtn = (ToggleButton) v.findViewById(R.id.commLikeBtn);
                commDislBtn = (ToggleButton) v.findViewById(R.id.commDislBtn);

                Comment comment = db.findCommentById(c.getId());

                int nbrDislike = comment.getDislikes();

                if (isChecked){

                    comment = db.dislikeCommentById(comment.getId(), nbrDislike + 1);
                    dislikes[position] +=1;

                    commDislBtn.setBackgroundDrawable(ContextCompat.getDrawable(v.getContext().getApplicationContext(), R.drawable.dislike_on));
                    dislikeButtons[position] = true;

                    commLikeBtn.setBackgroundDrawable(ContextCompat.getDrawable(v.getContext().getApplicationContext(), R.drawable.like_off));
                    likeButtons[position] = false;
                    commLikeBtn.setChecked(false);

                }else {

                    comment = db.dislikeCommentById(comment.getId(), nbrDislike -1);
                    dislikes[position] -=1;
                    dislikeButtons[position] = false;
                    commDislBtn.setBackgroundDrawable(ContextCompat.getDrawable(v.getContext().getApplicationContext(), R.drawable.dislike_off));
                }
                likesTW.setText(String.valueOf(comment.getLikes()));
                dislikesTW.setText(String.valueOf(comment.getDislikes()));

            }

        });


        //stele kraj


        return v;
    }
}
