package com.example.news24;


import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TheFragment extends Fragment {

    DatabaseHelper db;
    private ArrayList<NewsArticle> newsArticles = new ArrayList<NewsArticle>();

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
        newsArticles =  db.getNewsArticles();

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
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){
                Intent showArticleActivity = new Intent(view.getContext(), ArticleActivity.class);
                showArticleActivity.putExtra("com.example.news24.ITEM_INDEX", i);
                startActivity(showArticleActivity);
            }
        });

        return view;
    }

}
