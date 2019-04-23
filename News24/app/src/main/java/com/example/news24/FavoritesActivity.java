package com.example.news24;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class FavoritesActivity extends AppCompatActivity {

    private ViewFavoritesAdapter vpAdapter;
    private ViewPager viewFavorites;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);


        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("   My Favorites");
        getSupportActionBar().setLogo(R.drawable.news_pic);

        viewFavorites = findViewById(R.id.viewFavorites);
        vpAdapter = new ViewFavoritesAdapter(getSupportFragmentManager());
        viewFavorites.setAdapter(vpAdapter);

    }


}
