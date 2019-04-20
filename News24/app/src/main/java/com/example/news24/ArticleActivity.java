package com.example.news24;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

public class ArticleActivity extends AppCompatActivity {

    private Toolbar toolbar;

    ToggleButton acFavoritesToggleButton;
    ToggleButton acLikeToggleButton;
    ToggleButton acDislikeToggleButton;

    String[] titleIds = {
            "Tusk suggests Brexit delay of up to a year",
            "Liverpool take control against Porto",
            "We went troll hunting in Iceland"
    };
    String[] categoryIds = {
            " cat Tusk ",
            "cat Liverpool ",
            "cat We "
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        toolbar = findViewById(R.id.toolBar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle(null);
//
//        getSupportActionBar().setLogo(R.drawable.news_pic);

        Intent in = getIntent();
        int index = in.getIntExtra("com.example.news24.ITEM_INDEX", -1);

        TextView acTitleTextView = findViewById(R.id.acTitleTextView);
        acTitleTextView.setText(titleIds[index]);

        TextView acCategoryTextView = findViewById(R.id.acCategoryTextView);
        acCategoryTextView.setText(categoryIds[index]);


        acFavoritesToggleButton = (ToggleButton) findViewById(R.id.acFavoritesToggleButton);
        acFavoritesToggleButton.setChecked(false);
        acFavoritesToggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.star_grey));
        acFavoritesToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    acFavoritesToggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.star_gold));
                else
                    acFavoritesToggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.star_grey));
            }
        });

        acLikeToggleButton = (ToggleButton) findViewById(R.id.acLikeToggleButton);
        acLikeToggleButton.setChecked(false);
        acLikeToggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.like_grey));

        acDislikeToggleButton = (ToggleButton) findViewById(R.id.acDislikeToggleButton);
        acDislikeToggleButton.setChecked(false);
        acDislikeToggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.dislike_grey));

        acLikeToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    acLikeToggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.like_green));
                    acDislikeToggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.dislike_grey));
                    acDislikeToggleButton.setChecked(false);

                }else
                    acLikeToggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.like_grey));
                    }
        });

        acDislikeToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    acDislikeToggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.dislike_red));
                    acLikeToggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.like_grey));
                    acLikeToggleButton.setChecked(false);
                }else
                    acDislikeToggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.dislike_grey));
            }
        });
    }
}
