package com.example.news24;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TheFragment extends Fragment  {

    DatabaseHelper db;
   // private ArrayList<NewsArticle> newsArticles = new ArrayList<NewsArticle>();
    private  int position;
    public TheFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_the, container, false);

        Resources res = getResources();
        ListView myListView = view.findViewById(R.id.myListView);

        db = new DatabaseHelper(getActivity());
        String cat = db.getSelectedCategory().getTitle();
        System.out.println("\n Selektovan kategorija 2 -> " + cat);

        ArrayList<NewsArticle>  newsArticles = new ArrayList<NewsArticle>();
        if(cat.equals("Home")){// ako je home prikazuje sve
            newsArticles =  db.getNewsArticles();
        }else{
            newsArticles =  db.getNewsArticlesByCategory(cat);
        }

        String[] articles = new String[newsArticles.size()];
        String[] categories = new String[newsArticles.size()];
        String[] images = new String[newsArticles.size()];

        for(int i = 0; i < newsArticles.size(); i++){
            articles[i] = newsArticles.get(i).getTitle();
            categories[i] = newsArticles.get(i).getCategory();
            images[i] = newsArticles.get(i).getImage();
        }


  /*
        ArrayAdapter<String>listViewAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                articles
        );

        myListView.setAdapter(listViewAdapter);
*/

        ItemAdapter itemAdapter = new ItemAdapter(getActivity(), articles, categories, images);
        myListView.setAdapter(itemAdapter);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id){


                db = new DatabaseHelper(getActivity());
                String cat = db.getSelectedCategory().getTitle();
                ArrayList<NewsArticle>  newsArticles = new ArrayList<NewsArticle>();
                System.out.println("\n Selektovan kategorija KLIKNUTO-> " + db.findCategoryById(position).getTitle());

                if(cat.equals("Home")){// ako je home prikazuje sve
                    newsArticles =  db.getNewsArticles();
                }else{
                    newsArticles =  db.getNewsArticlesByCategory(cat);
                }

                Intent showArticleActivity = new Intent(view.getContext(), ArticleActivity.class);
                int newsArticleId = newsArticles.get(position).getId();
                NewsArticle newsArticle = db.findNewsArticleById(newsArticleId);
                showArticleActivity.putExtra("newsArticle", newsArticle);

                int selectedNewsArticleId = adapterView.getId();
                //  showArticleActivity.putExtra("com.example.news24.ITEM_INDEX", position);
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
