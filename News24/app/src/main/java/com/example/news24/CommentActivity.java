package com.example.news24;

import android.annotation.SuppressLint;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

public class CommentActivity extends AppCompatActivity implements CommentDialog.NoticeDialogListener{

    private ListView commentsListView;
    private String[] commentContent;
    private String[] users;
    private String[] time;
    private int[] likes;
    private int[] dislikes;
    private int[] commentID;
    private int articleId;
    private boolean[] likebBtnPressed;
    private boolean[] dislikeBtnPressed;
    private ArrayList<Comment> comments;

    private Button openDialog;
    private Button commLikeBtn;

    CommentAdapter commentAdapter;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commentt);

        if(getIntent().hasExtra("category")) {
            TextView commCategoryTW = findViewById(R.id.commCategoryTW);
            TextView commTitleTW = findViewById(R.id.commTitleTW);

            commCategoryTW.setText(getIntent().getExtras().getString("category"));
            commTitleTW.setText(getIntent().getExtras().getString("title"));
            articleId = getIntent().getExtras().getInt("articleID");
        }

        DatabaseHelper db = new DatabaseHelper(this);
        comments = db.findCommentsByArticleId(articleId);

        commentsListView = findViewById(R.id.commListView);

        commentContent = new String[comments.size()];
        users = new String[comments.size()];
        time = new String[comments.size()];
        likes = new int[comments.size()];
        dislikes = new int[comments.size()];
        commentID = new int[comments.size()];
        likebBtnPressed = new boolean[comments.size()];
        dislikeBtnPressed = new boolean[comments.size()];

        for(int i=0; i<comments.size(); i++){
            commentContent[i] = comments.get(i).getContent();
            users[i] = comments.get(i).getUser_id();
            time[i] = comments.get(i).getTime();
            likes[i] = comments.get(i).getLikes();
            dislikes[i] = comments.get(i).getDislikes();
            commentID[i] = comments.get(i).getId();
            likebBtnPressed[i] = false;
            dislikeBtnPressed[i] = false;
        }

        TextView numCommTW = findViewById(R.id.numCommTW);
        numCommTW.setText(Integer.toString(comments.size()));

        commentAdapter = new CommentAdapter(this, commentContent, users, time, likes, dislikes, commentID, likebBtnPressed, dislikeBtnPressed);
        commentsListView.setAdapter(commentAdapter);
       // setListViewHeightBasedOnChildren(commentsListView);

        openDialog = findViewById(R.id.commBtn);
        openDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentDialog commentDialog = new CommentDialog();
                Bundle bundle = new Bundle();
                bundle.putInt("articleID", articleId);
                commentDialog.setArguments(bundle);
                commentDialog.show(getSupportFragmentManager(), "missiles");
            }
        });
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        DatabaseHelper db = new DatabaseHelper(this);
        comments = db.findCommentsByArticleId(articleId);

        commentsListView = findViewById(R.id.commListView);

        commentContent = new String[comments.size()];
        users = new String[comments.size()];
        time = new String[comments.size()];
        likes = new int[comments.size()];
        dislikes = new int[comments.size()];
        commentID = new int[comments.size()];
        likebBtnPressed = new boolean[comments.size()];
        dislikeBtnPressed = new boolean[comments.size()];


        for(int i=0; i<comments.size(); i++){
            commentContent[i] = comments.get(i).getContent();
            users[i] = comments.get(i).getUser_id();
            time[i] = comments.get(i).getTime();
            likes[i] = comments.get(i).getLikes();
            dislikes[i] = comments.get(i).getDislikes();
            commentID[i] = comments.get(i).getId();
            likebBtnPressed[i] = false;
            dislikeBtnPressed[i] = false;
        }

        TextView numCommTW = findViewById(R.id.numCommTW);
        numCommTW.setText(Integer.toString(comments.size()));

        commentAdapter = new CommentAdapter(this, commentContent, users, time, likes, dislikes, commentID, likebBtnPressed, dislikeBtnPressed);
        commentsListView.setAdapter(commentAdapter);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}
