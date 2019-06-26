package com.example.news24;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FragmentArticlesAdmin extends Fragment {
    View view;
    DatabaseHelper db;

    private ArrayList<NewsArticle> articles;

    public FragmentArticlesAdmin(){

    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.articles_fragment_admin,container,false);
        db = new DatabaseHelper(getActivity());
        articles = db.getNewsArticles();
        setHasOptionsMenu(true);
        List<String> names = new ArrayList<>();
        for (NewsArticle newsArticle : articles) {
            names.add(newsArticle.getTitle());
        }

        ListView myListView = (ListView) view.findViewById(R.id.articlesListViewAdmin);

        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                names
        );
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = adapterView.getItemAtPosition(i).toString();

                Cursor data = db.getArticleID(name); //get the id associated with that name
                int itemID = -1;
                while(data.moveToNext()){
                    itemID = data.getInt(0);
                }
                if(itemID > -1){
                    Intent intent = new Intent(getActivity(), EditFragmentArticleAdmin.class);
                    startActivity(intent);
                    intent.putExtra("id",itemID);
                    intent.putExtra("name",name);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getActivity(), "This is my Toast message!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    /* myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                        Intent intent = new Intent(getActivity(), EditFragmentAdmin.class);
                        startActivity(intent);
                }
            });*/
        myListView.setAdapter(listViewAdapter);
        return view;
    }

}
