package com.example.news24;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TheFragment extends Fragment {

    private TextView textView;

    public TheFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_the, container, false);
//        textView = view.findViewById(R.id.textdisplay);
//        textView.setText(getArguments().getString("message"));
            Resources res = getResources();
            ListView myListView = view.findViewById(R.id.myListView);
            String[] articles = res.getStringArray(R.array.articles);
            String[] categories = res.getStringArray(R.array.categories);
            String[] images = res.getStringArray(R.array.images);


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


        return view;
    }

}
