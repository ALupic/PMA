package com.example.news24;

import android.content.ClipData;
import android.content.Intent;
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

public class MainActivity extends AppCompatActivity
        //implements NavigationView.OnNavigationItemSelectedListener
        {
            private static int SPLASH_TIME_OUT = 4000;
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
        int id = item.getItemId();

        if(id == R.id.option_login){
            Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(loginIntent);
            return false;
        }

        return super.onOptionsItemSelected(item);
    }
}
