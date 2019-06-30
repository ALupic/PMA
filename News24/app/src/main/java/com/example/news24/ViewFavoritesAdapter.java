package com.example.news24;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewFavoritesAdapter extends FragmentPagerAdapter {

    private  String loggedUsername;

    public ViewFavoritesAdapter(FragmentManager fm, String loggedUsername) {
        super(fm);
        this.loggedUsername = loggedUsername;
    }

    @Override
    public Fragment getItem(int position) {

        TheFavoritesFragment demoFragment = new TheFavoritesFragment();
        position = position + 1;
        Bundle bundle = new Bundle();
        bundle.putString("message", "Fragment :"+position);
        bundle.putString("loggedUsername", loggedUsername);
        demoFragment.setArguments(bundle);

        return demoFragment;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position){
        position = position + 1;
        if(position==1){
            return "My favorites";
        }
        return "My favorites";

    }
}
