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
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity { //implements NavigationView.OnNavigationItemSelectedListener
    private static int SPLASH_TIME_OUT = 4000;

    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedPreferences;

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private ViewPagerAdapter vpAdapter;
    private TabLayout tabLayout;

    ListView myListView;
    String[] articles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
     //   NavigationView navigationView = findViewById(R.id.nav_view);
     //   navigationView.setNavigationItemSelectedListener(this);

        Resources res = getResources();
        myListView = findViewById(R.id.fragment_list);
        articles = res.getStringArray(R.array.articles);

    //    myListView.setAdapter(new ArrayAdapter<String>(this, R.layout.my_listview_detail, articles));

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
        return true;
    }

    @Override
        public boolean onOptionsItemSelected(MenuItem item){
        //int id = item.getItemId();
        switch (item.getItemId()){
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
}
