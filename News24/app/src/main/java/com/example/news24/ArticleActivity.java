package com.example.news24;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.View;
import android.widget.ImageButton;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ArticleActivity extends AppCompatActivity {

    private Toolbar toolbar;

    DatabaseHelper db;

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
        db = new DatabaseHelper(getParent());

        toolbar = findViewById(R.id.toolBar);

//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle(null);

//        getSupportActionBar().setLogo(R.drawable.news_pic);

        Intent in = getIntent();
       // int index = in.getIntExtra("com.example.news24.ITEM_INDEX", -1);
     //   int selectedNewsArticleId = in.getIntExtra("selectedNewsArticleId", -1);
        final NewsArticle newsArticle = (NewsArticle) in.getSerializableExtra("newsArticle");

        TextView acTitleTextView = findViewById(R.id.acTitleTextView);
        TextView acCategoryTextView = findViewById(R.id.acCategoryTextView);
        TextView acContentTextView = findViewById(R.id.acContentTextView);
        ImageView acImgImageView = findViewById(R.id.acImgImageView);
        TextView acLikeTextView = findViewById(R.id.acLikeTextView);
        TextView acDisikeTextView = findViewById(R.id.acDislikeTextView);

        //acTitleTextView.setText(titleIds[index]);
        acTitleTextView.setText(newsArticle.getTitle());
        acCategoryTextView.setText(newsArticle.getCategory());
        acContentTextView.setText(newsArticle.getContent());
        acLikeTextView.setText(String.valueOf(newsArticle.getLikes()));
        acDisikeTextView.setText(String.valueOf(newsArticle.getDislikes()));

        String imgName = newsArticle.getImage();
        Context c = ArticleActivity.this;
        Resources res = getResources();
        int resourceId = res.getIdentifier(imgName, "drawable", c.getPackageName());
        acImgImageView.setImageResource(resourceId);

        //ZORICEV KOD -- samo saljem podatke ka aktivnosti

        ImageButton goToCommentsBtn = findViewById(R.id.goToCommentsBtn);
        //Novo
        goToCommentsBtn.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.comment_img));
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
        ImageButton goToMapBtn = findViewById(R.id.goToMapBtn);
        goToMapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), MapsActivity.class);
                startIntent.putExtra("lat", newsArticle.getLat());
                startIntent.putExtra("longg", newsArticle.getLongg());

                startActivity(startIntent);
            }
        });


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
