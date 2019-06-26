package com.example.news24;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FragmentTopicsAdmin extends Fragment{
    View view;
   DatabaseHelper db;

    private ArrayList<Category> categories;

    public FragmentTopicsAdmin(){

    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view =inflater.inflate(R.layout.topics_fragment_admin,container,false);
        db = new DatabaseHelper(getActivity());
        categories = db.getCategories();
        setHasOptionsMenu(true);
        List<String> names = new ArrayList<>();
        for (Category category : categories) {
            names.add(category.getTitle());
        }

        ListView myListView = (ListView) view.findViewById(R.id.topicsListViewAdmin);

        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                names
        );
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = adapterView.getItemAtPosition(i).toString();

                Cursor data = db.getCategoryID(name); //get the id associated with that name
                int itemID = -1;
                while(data.moveToNext()){
                    itemID = data.getInt(0);
                }
                if(itemID > -1){
                    Intent intent = new Intent(getActivity(), EditFragmentAdmin.class);
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
