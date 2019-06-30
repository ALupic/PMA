package com.example.news24;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment  {

    DatabaseHelper db;
    String cat = "Home";

    private  int position;
    public SearchFragment() {
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

        cat = db.getSelectedCategory().getTitle();
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




        ItemAdapter itemAdapter = new ItemAdapter(getActivity(), articles, categories, images);
        myListView.setAdapter(itemAdapter);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id){


                db = new DatabaseHelper(getActivity());
                String cat = db.getSelectedCategory().getTitle();
                ArrayList<NewsArticle>  newsArticles = new ArrayList<NewsArticle>();
               // System.out.println("\n Selektovan kategorija KLIKNUTO-> " + db.findCategoryById(position).getTitle());

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

                startActivity(showArticleActivity);
            }
        });



        return view;
    }



    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            // Refresh your fragment here
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
            Log.i("IsRefresh", "Yes");
        }
    }
}
