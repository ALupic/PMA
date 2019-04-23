package com.example.news24;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.TabLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener {
    private static int SPLASH_TIME_OUT = 4000;

    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedPreferences;

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private ViewPagerAdapter vpAdapter;
    private TabLayout tabLayout;

    DatabaseHelper db;
    private ArrayList<NewsArticle> newsArticles = new ArrayList<NewsArticle>();

//    private ListView myListView;
//    private String[] articles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHelper db = new DatabaseHelper(this);

        //INITIALIZE PREFERENCES - THESE ARE USED FOR SESSION PURPOSES
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setLogo(R.drawable.news_pic);


        viewPager = findViewById(R.id.pager);
        vpAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(vpAdapter);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        drawerLayout = findViewById(R.id.drawer_layout);

        //NEW
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


    }

 /*   @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()){
            case R.id.nav_politics:
                getSupportFragmentManager().beginTransaction().replace(R.id.drawer_layout, new PoliticsFragment()).commit();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }
*/
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

  /*      MenuItem menuItem = menu.findItem(R.id.option_search);
        SearchView searchView = menuItem.getActionView(); // returns the objects of the class that is specified within the actionViewClass field (options_menu.xml)

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) { // gets called with every new input string, newText is the input string
                db = new DatabaseHelper(getApplicationContext());
                newsArticles =  db.getNewsArticles();

                ArrayList<NewsArticle> results = new ArrayList<NewsArticle>();

                for(NewsArticle x: newsArticles){
                    if(x.getTitle().contains(newText))
                        results.add(x);
                }

                //((MyAdapter)listView.getAdapter()).update(results); // to refresh the listView

                return false;
            }
        });
*/

        return true;
    }

    @Override
        public boolean onOptionsItemSelected(MenuItem item){
        //int id = item.getItemId();
        switch (item.getItemId()){
            /*case R.id.option_search:
                Toast.makeText(MainActivity.this, "You clicked Search", Toast.LENGTH_SHORT).show();
                View searchView = MenuItemCompat.getActionView(item);
                searchView.setOnQueryTextListener(new View.OnQueryTextLisener(){
                    @Override
                    public boolean onQueryTextChange(String newText){
                        ArrayList<String> tempList = new ArrayList<>();

                        for(String temp: a)
                    }
                });
                break;*/

            case R.id.option_login:
                Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                break;

            case R.id.option_settings:
                Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(settingsIntent);
                break;
            case R.id.option_logout:
                loginIntent = new Intent(MainActivity.this, LoginActivity.class);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();

                startActivity(loginIntent);
                break;

            case R.id.option_register:
                Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
                break;
        }

//        if(id == R.id.option_login){
//            Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
//            startActivity(loginIntent);
//            return false;
//        }

        //return super.onOptionsItemSelected(item);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){

        MenuItem loginMenu = menu.findItem(R.id.option_login);
        MenuItem logOutMenu = menu.findItem(R.id.option_logout);
        MenuItem settingsMenu = menu.findItem(R.id.option_settings);
        MenuItem registerMenu = menu.findItem(R.id.option_register);

        if(sharedPreferences.getString("username","").equals("")){ //IF THERE IS NO SESSION
            loginMenu.setVisible(true);
            registerMenu.setVisible(true);
            logOutMenu.setVisible(false);
            settingsMenu.setVisible(false);
        }
        else{
            loginMenu.setVisible(false);
            registerMenu.setVisible(false);
            logOutMenu.setVisible(true);
            settingsMenu.setVisible(true);
        }

        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item){
        int id = item.getItemId();

        if(id==R.id.nav_home){
            Toast.makeText(this, "You clicked Home", Toast.LENGTH_SHORT).show();
            //Intent i1 = new Intent(MainActivity.this, TheFragment.class);
            //i1.putExtra("value", 1);
            //startActivity(i1);
        }else if(id==R.id.nav_politics){
            Toast.makeText(this, "You clicked Politics", Toast.LENGTH_SHORT).show();
        }else if(id==R.id.nav_favorites) {

            if(sharedPreferences.getString("username","").equals("")){
                Toast.makeText(this, "Login first", Toast.LENGTH_SHORT).show();

            }else {
                Toast.makeText(this, "You clicked Favorites", Toast.LENGTH_SHORT).show();
                Intent favoritesIntent = new Intent(MainActivity.this, FavoritesActivity.class);
                startActivity(favoritesIntent);
            }
        }else{
                Toast.makeText(this, "You clicked something else", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
