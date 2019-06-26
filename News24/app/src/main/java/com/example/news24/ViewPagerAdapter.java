package com.example.news24;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    DatabaseHelper db;
    private Context context;
    private ArrayList<Category> categories = new ArrayList<Category>();


    public ViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {

        DatabaseHelper db = new DatabaseHelper(context);
        TheFragment demoFragment = new TheFragment();

//        db.selectCategory(db.findCategoryById(position).getId());// u bazi postavljam da je selektovana kategorija
//        System.out.println("\n Selektovan kategorija -> " + db.findCategoryById(position).getTitle());
//        //position = position + 1;
        Bundle bundle = new Bundle();
        bundle.putString("message", "Fragment :"+position);
        demoFragment.setArguments(bundle);

        return demoFragment;
    }

    @Override
    public int getCount() {
        return 7;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position){

        DatabaseHelper db = new DatabaseHelper(context);
       // db.selectCategory(db.findCategoryById(position).getId());// u bazi postavljam da je selektovana kategorija
        return db.findCategoryById(position+1).getTitle();// uzima naslove kategorija iz baze i prosledjuje u tabove
       // categories =  db.getCategories();
      //  position = position + 1;

//        if(position==1){
//            return db.findCategoryById(position).getTitle();
//        }else if(position==2){
//            return "Politics";
//        }else if(position==3){
//            return "Sport";
//        }else if(position==4) {
//            return "Travel";
//        }else if(position==5){
//            return "Entertainment";
//        }else if(position==6){
//            return "Technology";
//        }else{
//            return "Business";
//        }
    }

    @Override
    public int getItemPosition(Object object) {
        // POSITION_NONE makes it possible to reload the PagerAdapter
        return POSITION_NONE;
    }
}
