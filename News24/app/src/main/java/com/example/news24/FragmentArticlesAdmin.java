package com.example.news24;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentArticlesAdmin extends Fragment {
    View view;

    public FragmentArticlesAdmin(){

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view =inflater.inflate(R.layout.article_fragment_admin,container,false);

        return view;
    }

}
