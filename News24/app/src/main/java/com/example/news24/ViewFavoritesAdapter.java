package com.example.news24;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewFavoritesAdapter extends FragmentPagerAdapter {

    public ViewFavoritesAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        TheFavoritesFragment demoFragment = new TheFavoritesFragment();
        position = position + 1;
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
        position = position + 1;
        if(position==1){
            return "Home";
        }else if(position==2){
            return "Politics";
        }else if(position==3){
            return "Sport";
        }else if(position==4) {
            return "Travel";
        }else if(position==5){
            return "Entertainment";
        }else if(position==6){
            return "Technology";
        }else{
            return "Business";
        }
    }
}
