package com.example.news24;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;

import io.opencensus.tags.Tag;

public class MainActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener {
    private static int SPLASH_TIME_OUT = 4000;

    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedPreferences;

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ViewPager viewPager;

    private TabLayout tabLayout;

    DatabaseHelper db;
    private ArrayList<NewsArticle> newsArticles = new ArrayList<NewsArticle>();

    FirebaseFirestore firestoreRootRaf;
    CollectionReference itemRef;
    ArrayList itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHelper db = new DatabaseHelper(this);
        //FIREBASE //stele
       // GoogleApiClient googleApiClient = new GoogleApiClient.Builder(this).addApi(Auth.GOOGLE_SIGN_IN_API).build();
        firestoreRootRaf = FirebaseFirestore.getInstance();
        itemRef = firestoreRootRaf.collection("newsarticle");
        itemList = new ArrayList<>();
        readData(new FirestoreCallback() {
            @Override
            public void onCallback(List<String> list) {
                Log.d("MainActivity", list.toString());

            }
        });



        //INITIALIZE PREFERENCES - THESE ARE USED FOR SESSION PURPOSES
                sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setLogo(R.drawable.news_pic);

        db.selectCategory(db.findCategoryById(1).getId());
        viewPager = findViewById(R.id.pager);

        ViewPagerAdapter vpAdapter = new ViewPagerAdapter(getSupportFragmentManager(),this);

        for(int i = 0; i < db.getCategories().size(); i++){
            vpAdapter.addFragment(new TheFragment());
        }
        viewPager.setAdapter(vpAdapter);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener( new TabLayout.OnTabSelectedListener() {
            DatabaseHelper db = new DatabaseHelper(getApplicationContext());

            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                int position = tab.getPosition();
                db.selectCategory(db.findCategoryById(position+1).getId());// u bazi postavljam da je selektovana kategorija
                System.out.println("\n Selektovan poz("+position+1+") kategorija -> " + db.findCategoryById(position+1).getTitle());

               // vpAdapter = new ViewPagerAdapter(getSupportFragmentManager(),getApplicationContext());
               // viewPager.setAdapter(vpAdapter);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                db.unselectAll();
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });



        //NEW
        displayNavigationBar();//navigacioni dinamicki kreiran
//        drawerLayout = findViewById(R.id.drawer_layout);
//        NavigationView navigationView = findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);



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


            case R.id.option_about:
                Intent aboutIntent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(aboutIntent);
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

//        if(id==R.id.nav_home){
//            Toast.makeText(this, "You clicked Home", Toast.LENGTH_SHORT).show();
//            //Intent i1 = new Intent(MainActivity.this, TheFragment.class);
//            //i1.putExtra("value", 1);
//            //startActivity(i1);
//        }else if(id==R.id.nav_politics){
//            Toast.makeText(this, "You clicked Politics", Toast.LENGTH_SHORT).show();
//        }else if(id==R.id.nav_favorites) {
//
//            if(sharedPreferences.getString("username","").equals("")){
//                Toast.makeText(this, "Login first", Toast.LENGTH_SHORT).show();
//
//            }else {
//                Toast.makeText(this, "You clicked Favorites", Toast.LENGTH_SHORT).show();
//                Intent favoritesIntent = new Intent(MainActivity.this, FavoritesActivity.class);
//                startActivity(favoritesIntent);
//            }
//        }else if(id == R.id.nav_control_panel) {
//            Intent adminIntent = new Intent(MainActivity.this, AdminActivity.class);
//            startActivity(adminIntent);
//        } else{
//                Toast.makeText(this, "You clicked something else", Toast.LENGTH_SHORT).show();
//        }
        return false;
    }


    private void displayNavigationBar(){
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        drawerLayout = findViewById(R.id.drawer_layout);
        Menu menu = navigationView.getMenu();
        SubMenu menu1 = menu.addSubMenu("Category");
        for (Category cat : db.getCategories()
             ) {
            menu1.add(cat.getTitle()).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    DatabaseHelper db = new DatabaseHelper(getApplicationContext());
                    int selecetdID = 1;
                    Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
                    for (Category c: db.getCategories()) {
                        if(c.getTitle().equals(item.getTitle())){
                            selecetdID = c.getId();
                            break;
                        }
                    }

                    Category category = db.selectCategory(db.findCategoryById(selecetdID).getId());
                    if(drawerLayout.isDrawerOpen(GravityCompat.START)){
                        drawerLayout.closeDrawer(GravityCompat.START);
                    }else{
                        //super.onBackPressed();
                    }
                    viewPager.setCurrentItem(selecetdID-1);// pozicije broji od nule a u bazi cuvamo kao home = 1
                    return false;
                }
            });

        }

        SubMenu menu2 = menu.addSubMenu("Others");

        menu2.add("Control panel").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent adminIntent = new Intent(MainActivity.this, AdminActivity.class);
                startActivity(adminIntent);
                return false;
            }
        });

        menu2.add("Search").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent searchIntent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(searchIntent);
                return false;
            }
        });

        menu2.add("My Favorites").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(sharedPreferences.getString("username","").equals("")){

                    Toast.makeText(getApplicationContext(), "Login first", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(getApplicationContext(), "You clicked Favorites", Toast.LENGTH_SHORT).show();
                    Intent favoritesIntent = new Intent(MainActivity.this, FavoritesActivity.class);
                    startActivity(favoritesIntent);
                }
                return false;
            }
        });



        //drawerLayout.closeDrawer();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void readData(final FirestoreCallback firestoreCallback){

        itemRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot documentSnapshot : task.getResult()){
                        String itemTitle = documentSnapshot.getString("title");
                        itemList.add(itemTitle);
                    }
                 firestoreCallback.onCallback(itemList);
                } else {
                    Log.d("MainActivity", "Error getting document: ", task.getException());
                }
            }
        });


    }

    private interface FirestoreCallback{
        void onCallback(List<String> list);
    }

}
