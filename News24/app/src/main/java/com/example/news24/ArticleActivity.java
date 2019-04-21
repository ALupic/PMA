package com.example.news24;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
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
            "Politics",
            "Sport",
            "Travel"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        toolbar = findViewById(R.id.toolBar);

//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle(null);

//        getSupportActionBar().setLogo(R.drawable.news_pic);

        Intent in = getIntent();
        int index = in.getIntExtra("com.example.news24.ITEM_INDEX", -1);

        TextView acTitleTextView = findViewById(R.id.acTitleTextView);
        acTitleTextView.setText(titleIds[index]);

        //ZORICEV KOD -- samo saljem podatke ka aktivnosti

        ImageButton goToCommentsBtn = findViewById(R.id.goToCommentsBtn);
        goToCommentsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), CommentActivity.class);
                startIntent.putExtra("category", "Politics");
                startIntent.putExtra("title", "Tusk suggests Brexit delay of up to a year");

                startActivity(startIntent);
            }
        });


        //KRAJ ZORICEVOG KODA


        TextView acCategoryTextView = findViewById(R.id.acCategoryTextView);
        acCategoryTextView.setText(categoryIds[index]);

        acFavoritesToggleButton = (ToggleButton) findViewById(R.id.acFavoritesToggleButton);
        acFavoritesToggleButton.setChecked(false);
        acFavoritesToggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.star_off));
        acFavoritesToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    acFavoritesToggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.star_on));
                else
                    acFavoritesToggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.star_off));
            }
        });

        acLikeToggleButton = (ToggleButton) findViewById(R.id.acLikeToggleButton);
        acLikeToggleButton.setChecked(false);
        acLikeToggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.like_off));

        acDislikeToggleButton = (ToggleButton) findViewById(R.id.acDislikeToggleButton);
        acDislikeToggleButton.setChecked(false);
        acDislikeToggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.dislike_off));

        acLikeToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    acLikeToggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.like_on));
                    acDislikeToggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.dislike_off));
                    acDislikeToggleButton.setChecked(false);

                }else
                    acLikeToggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.like_off));
                    }
        });

        acDislikeToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    acDislikeToggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.dislike_on));
                    acLikeToggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.like_off));
                    acLikeToggleButton.setChecked(false);
                }else
                    acDislikeToggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.dislike_off));
            }
        });
    }
}
