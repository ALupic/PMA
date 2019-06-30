package com.example.news24;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;


public class ArticleActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ArrayList<Favorites> favorites = new ArrayList<Favorites>();
    private ArrayList<NewsArticle> newsArticlesPrepare = new ArrayList<NewsArticle>();
    private Toolbar toolbar;
    NewsArticle newsArticle = new NewsArticle();
    DatabaseHelper db;

    private DrawerLayout drawerLayout;
    private ViewPager viewPager;

    ToggleButton acFavoritesToggleButton;
    ToggleButton acLikeToggleButton;
    ToggleButton acDislikeToggleButton;
    SharedPreferences sharedPreferences;
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
        db = new DatabaseHelper(this);

        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle(null);
//        getSupportActionBar().setLogo(R.drawable.news_pic);


        sharedPreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        final String loggedUsername = sharedPreferences.getString("username","");

        Intent in = getIntent();
       // int index = in.getIntExtra("com.example.news24.ITEM_INDEX", -1);
     //   int selectedNewsArticleId = in.getIntExtra("selectedNewsArticleId", -1);
        final NewsArticle na = (NewsArticle) in.getSerializableExtra("newsArticle");
        newsArticle = na;
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

        acTitleTextView.setMovementMethod(new ScrollingMovementMethod());
        acContentTextView.setMovementMethod(new ScrollingMovementMethod());
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
                startIntent.putExtra("category", newsArticle.getCategory());
                startIntent.putExtra("title", newsArticle.getTitle());
                startIntent.putExtra("articleID", newsArticle.getId());

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

        newsArticlesPrepare =  new ArrayList<NewsArticle>();
        favorites = db.getAllFavorites();


        acFavoritesToggleButton = (ToggleButton) findViewById(R.id.acFavoritesToggleButton);
        //ako postoji ulogovani korisnik proveri za njegovo ime id clanka u bazi sa trenutnim, ako se poklapa cekiraj zvezdicu u suprotnom ostavi
        //ako na klik cekira zvezdicu ubaci u bazu u suprotnom obrisi iz baze
        if(loggedUsername !=""){
            acFavoritesToggleButton.setChecked(false);//ako ga nadjemo promenicemo na true
            acFavoritesToggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.star_off));
            for(int i = 0; i < favorites.size(); i ++){
                if(loggedUsername.equals(favorites.get(i).getUser_id())){
                    //ako su isti stavi ga u listu artikala koje cemo proveravati da li je jedan od njih prikazan
                    newsArticlesPrepare.add(db.findNewsArticleById(favorites.get(i).getArticle_id()));
                }
            }
            //da li je nas artikal u listi
            for(int i = 0; i < newsArticlesPrepare.size(); i++) {
                if(newsArticlesPrepare.get(i).getId() == newsArticle.getId() ){
                    acFavoritesToggleButton.setChecked(true);
                    acFavoritesToggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.star_on));
                    break;
                }
            }

        }else{
            acFavoritesToggleButton.setChecked(false);
            acFavoritesToggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.star_off));
        }


        acFavoritesToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    acFavoritesToggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.star_on));
                    db.addFavorites(newsArticle.getId(), loggedUsername);

                }else {
                    acFavoritesToggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.star_off));
                    db.deleteFavorites(newsArticle.getId(), loggedUsername);
                }
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
                int newsArticleId = newsArticle.getId();
                int nbrLike = newsArticle.getLikes();
                if (isChecked){

                    newsArticle = db.likeNewsArticleById(newsArticleId, nbrLike + 1);

                    acLikeToggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.like_on));
                    acDislikeToggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.dislike_off));
                    acDislikeToggleButton.setChecked(false);

                }else {

                    newsArticle = db.likeNewsArticleById(newsArticleId, nbrLike -1);

                    acLikeToggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.like_off));
                }
                TextView acLikeTextView = findViewById(R.id.acLikeTextView);
                TextView acDisikeTextView = findViewById(R.id.acDislikeTextView);
                acLikeTextView.setText(String.valueOf(newsArticle.getLikes()));
                acDisikeTextView.setText(String.valueOf(newsArticle.getDislikes()));

            }

        });

        acDislikeToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int newsArticleId = newsArticle.getId();
                int nbrDislike = newsArticle.getDislikes();

                if (isChecked){

                    newsArticle = db.dislikeNewsArticleById(newsArticleId, nbrDislike + 1);


                    acDislikeToggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.dislike_on));
                    acLikeToggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.like_off));
                    acLikeToggleButton.setChecked(false);
                }else {
                    newsArticle = db.dislikeNewsArticleById(newsArticleId, nbrDislike -1);

                    acDislikeToggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.dislike_off));
                }
                TextView acLikeTextView = findViewById(R.id.acLikeTextView);
                TextView acDisikeTextView = findViewById(R.id.acDislikeTextView);
                acLikeTextView.setText(String.valueOf(newsArticle.getLikes()));
                acDisikeTextView.setText(String.valueOf(newsArticle.getDislikes()));

            }

        });
        displayNavigationBar();
    }
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }


    private void displayNavigationBar(){
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        drawerLayout = findViewById(R.id.drawer_layout_article);
        Menu menu = navigationView.getMenu();
        SubMenu menu2 = menu.addSubMenu("Others");

        menu2.add("Control panel").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent adminIntent = new Intent(ArticleActivity.this, AdminActivity.class);
                startActivity(adminIntent);
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
                    Intent favoritesIntent = new Intent(ArticleActivity.this, FavoritesActivity.class);
                    startActivity(favoritesIntent);
                }
                return false;
            }
        });


        //  drawerLayout.closeDrawer();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

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
                Intent loginIntent = new Intent(ArticleActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                break;

            case R.id.option_settings:
                Intent settingsIntent = new Intent(ArticleActivity.this, SettingsActivity.class);
                startActivity(settingsIntent);
                break;
            case R.id.option_logout:
                loginIntent = new Intent(ArticleActivity.this, LoginActivity.class);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();

                startActivity(loginIntent);
                break;

            case R.id.option_register:
                Intent registerIntent = new Intent(ArticleActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
                break;
        }

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
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }
}
