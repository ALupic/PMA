package com.example.news24;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
    private ArrayAdapter<String> adapter2;

    private ArrayList<NewsArticle> newsArticles = new ArrayList<NewsArticle>();
    private ArrayList<NewsArticle> temp = new ArrayList<NewsArticle>();

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

//        String[] dbArticles = new String[newsArticles.size()];
//        for(int i = 0; i < newsArticles.size(); i++){
//            dbArticles[i] = newsArticles.get(i).getTitle();
//        }

        ArrayList<String> titles = new ArrayList<String>();

        for(NewsArticle n : newsArticles){
            titles.add(n.getTitle());
        }

        search_articles = (ListView) findViewById(R.id.search_articles);
        ArrayList<String> arrayArticles = new ArrayList<>();
        arrayArticles.addAll(Arrays.asList(getResources().getStringArray(R.array.articles)));

        adapter = new ArrayAdapter<String>(
                SearchActivity.this,
                android.R.layout.simple_list_item_1,
                titles
        );


        adapter2 = new ArrayAdapter<String>(
                SearchActivity.this,
                android.R.layout.simple_list_item_1,
                titles
        );
        search_articles.setAdapter(adapter);

        search_articles.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id){
                System.out.println("\nID Search:" + id);
                db = new DatabaseHelper(getApplicationContext());

                //ArrayList<NewsArticle>  newsArticles = new ArrayList<NewsArticle>();
                // System.out.println("\n Selektovan kategorija KLIKNUTO-> " + db.findCategoryById(position).getTitle());

                Intent showArticleActivity = new Intent(view.getContext(), ArticleActivity.class);
                int newsArticleId = newsArticles.get(position).getId();
                NewsArticle newsArticle = db.findNewsArticleById(newsArticleId);

                showArticleActivity.putExtra("newsArticle", newsArticle);


                startActivity(showArticleActivity);
            }
        });



        search_articles.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id){


                db = new DatabaseHelper(getApplicationContext());
               // String cat = db.getSelectedCategory().getTitle();
                //ArrayList<NewsArticle>  newsArticles = new ArrayList<NewsArticle>();
                // System.out.println("\n Selektovan kategorija KLIKNUTO-> " + db.findCategoryById(position).getTitle());

//                if(cat.equals("Home")){// ako je home prikazuje sve
//                    newsArticles =  db.getNewsArticles();
//                }else{
//                    newsArticles =  db.getNewsArticlesByCategory(cat);
//                }
                System.out.println("\n  ID SearchActivity : " + id);
                Intent showArticleActivity = new Intent(view.getContext(), ArticleActivity.class);
              //  int newsArticleId = newsArticles.get(position).getId();

                NewsArticle newsArticle = db.findNewsArticleById((int) id);
                showArticleActivity.putExtra("newsArticle", newsArticle);


                startActivity(showArticleActivity);
            }
        });

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
                newText=newText.toLowerCase();
                adapter.getFilter().filter(newText);

                temp = new ArrayList<NewsArticle>();
                for(NewsArticle n: newsArticles){
                    String[] words = n.getTitle().split(" ");
                    for(String s: words){
                        s=s.toLowerCase();
                        if(s.startsWith(newText)){
                            temp.add(n);
                            break;
                         }

                    }
                }
                newsArticles = temp;
                return false;
            }


        });

        return super.onCreateOptionsMenu(menu);
    }
}