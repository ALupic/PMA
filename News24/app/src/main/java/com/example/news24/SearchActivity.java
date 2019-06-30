package com.example.news24;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;


import java.util.ArrayList;
import java.util.Arrays;

public class SearchActivity extends AppCompatActivity {



    private Toolbar toolbar;
    private SharedPreferences sharedPreferences;

    private ListView search_articles;
    private ArrayAdapter<String> adapter;
    private ArrayList<NewsArticle> newsArticles = new ArrayList<NewsArticle>();

    private ViewFavoritesAdapter vpAdapter;
    private ViewPager viewFavorites;

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("   Search");
        //getSupportActionBar().setLogo(R.drawable.news_pic);

        //sharedPreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        //String username = sharedPreferences.getString("username","");


        db = new DatabaseHelper(this);
        newsArticles =  db.getNewsArticles();

        String[] dbArticles = new String[newsArticles.size()];
        for(int i = 0; i < newsArticles.size(); i++){
            dbArticles[i] = newsArticles.get(i).getTitle();
        }

        search_articles = (ListView) findViewById(R.id.search_articles);
        ArrayList<String> arrayArticles = new ArrayList<>();
        arrayArticles.addAll(Arrays.asList(getResources().getStringArray(R.array.articles)));

        adapter = new ArrayAdapter<String>(
                SearchActivity.this,
                android.R.layout.simple_list_item_1,
                arrayArticles
        );

        search_articles.setAdapter(adapter);


        //viewFavorites = findViewById(R.id.viewFavorites);
        //vpAdapter = new ViewFavoritesAdapter(getSupportFragmentManager(), username);
        //viewFavorites.setAdapter(vpAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem(R.id.search_articles);
        SearchView searchView = (SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}