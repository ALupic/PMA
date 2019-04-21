package com.example.news24;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class CommentActivity extends AppCompatActivity {

    private ListView commentsListView;
    private String[] comments;
    private String[] users;
    private String[] time;
    private Button openDialog;

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
        }


        Resources res = getResources();
        commentsListView = findViewById(R.id.commListView);
        comments = res.getStringArray(R.array.comments);
        users = res.getStringArray(R.array.users);
        time = res.getStringArray(R.array.dates);

        CommentAdapter commentAdapter = new CommentAdapter(this, comments, users, time);
        commentsListView.setAdapter(commentAdapter);
       // setListViewHeightBasedOnChildren(commentsListView);

        openDialog = findViewById(R.id.commBtn);
        openDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentDialog commentDialog = new CommentDialog();
                commentDialog.show(getSupportFragmentManager(), "missiles");
            }
        });
    }

    /**** Method for Setting the Height of the ListView dynamically.
     **** Hack to fix the issue of not showing all the items of the ListView
     **** when placed inside a ScrollView  ****/
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
