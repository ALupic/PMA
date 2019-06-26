package com.example.news24;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

public class FavoritesActivity extends AppCompatActivity {

    private ViewFavoritesAdapter vpAdapter;
    private ViewPager viewFavorites;
    private Toolbar toolbar;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);


        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("   My Favorites");
        getSupportActionBar().setLogo(R.drawable.news_pic);

        sharedPreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username","");

        viewFavorites = findViewById(R.id.viewFavorites);
        vpAdapter = new ViewFavoritesAdapter(getSupportFragmentManager(), username);
        viewFavorites.setAdapter(vpAdapter);

    }


}
