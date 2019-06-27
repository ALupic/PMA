package com.example.news24;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TheFavoritesFragment extends Fragment  {

    DatabaseHelper db;
    private ArrayList<NewsArticle> newsArticles = new ArrayList<NewsArticle>();
    private ArrayList<NewsArticle> newsArticlesPrepare = new ArrayList<NewsArticle>();
    private ArrayList<Favorites> favorites = new ArrayList<Favorites>();


    public TheFavoritesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_favorites, container, false);

        Resources res = getResources();
        ListView favoritesListView = view.findViewById(R.id.favoritesListView);

        String loggedUsername = getArguments().getString("loggedUsername");

        db = new DatabaseHelper(getActivity());
        newsArticlesPrepare =  db.getNewsArticles();
        favorites = db.getAllFavorites();

        for(int i = 0; i < favorites.size(); i ++){
            if(loggedUsername.equals(favorites.get(i).getUser_id())){
                //ako su isti prikazi ga
                newsArticles.add(db.findNewsArticleById(favorites.get(i).getArticle_id()));
            }
        }

        String[] articles = new String[newsArticles.size()];
        String[] categories = new String[newsArticles.size()];
        String[] images = new String[newsArticles.size()];

        for(int i = 0; i < newsArticles.size(); i++){
            articles[i] = newsArticles.get(i).getTitle();
            categories[i] = newsArticles.get(i).getCategory();
            images[i] = newsArticles.get(i).getImage();
        }



        ItemAdapter itemAdapter = new ItemAdapter(getActivity(), articles, categories, images);
        favoritesListView.setAdapter(itemAdapter);

        favoritesListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id){
                Intent showArticleActivity = new Intent(view.getContext(), ArticleActivity.class);
                int selectedNewsArticleId = adapterView.getId();
              //  showArticleActivity.putExtra("com.example.news24.ITEM_INDEX", position);
                int newsArticleId = newsArticles.get(position).getId();
                NewsArticle newsArticle = db.findNewsArticleById(newsArticleId);
                showArticleActivity.putExtra("newsArticle", newsArticle);
                /*showArticleActivity.putExtra("newsArticleTitle", newsArticle.getTitle());
                showArticleActivity.putExtra("newsArticleCategory", newsArticle.getCategory());
                showArticleActivity.putExtra("newsArticleContent", newsArticle.getContent());
                showArticleActivity.putExtra("newsArticleLikes", newsArticle.getLikes());
                showArticleActivity.putExtra("newsArticleDislikes", newsArticle.getDislikes());
                showArticleActivity.putExtra("newsArticleImage", newsArticle.getImage());
                showArticleActivity.putExtra("newsArticleLat", newsArticle.getLat());
                showArticleActivity.putExtra("newsArticleLongg", newsArticle.getLongg());
*/

                startActivity(showArticleActivity);
            }
        });

        return view;
    }

}
