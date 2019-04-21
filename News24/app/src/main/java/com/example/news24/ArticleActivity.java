package com.example.news24;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ArticleActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView commentsListView;
    private String[] comments;

    String[] titleIds = {
            "Tusk suggests Brexit delay of up to a year",
            "Liverpool take control against Porto",
            "We went troll hunting in Iceland"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setLogo(R.drawable.news_pic);

        Intent in = getIntent();
        int index = in.getIntExtra("com.example.news24.ITEM_INDEX", -1);

        TextView acTitleTextView = findViewById(R.id.acTitleTextView);
        acTitleTextView.setText(titleIds[index]);

        Resources res = getResources();
        commentsListView = findViewById(R.id.commetListView);
        comments = res.getStringArray(R.array.comments);

        commentsListView.setAdapter(new ArrayAdapter<String>(this, R.layout.comment_listview_detail, comments));
    }
}
