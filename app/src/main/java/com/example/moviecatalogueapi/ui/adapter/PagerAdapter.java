package com.example.moviecatalogueapi.ui.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.moviecatalogueapi.R;
import com.example.moviecatalogueapi.ui.favorite.FavoriteMovieFragment;
import com.example.moviecatalogueapi.ui.favorite.FavoriteTVFragment;

public class PagerAdapter extends FragmentPagerAdapter {

    private final Context mContext;

    public PagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    // This determines the fragment for each tab
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new FavoriteMovieFragment();
        } else {
            return new FavoriteTVFragment();
        }
    }

    // This determines the number of tabs
    @Override
    public int getCount() {
        return 2;
    }

    // This determines the title for each tab
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        switch (position) {
            case 0:
                return mContext.getString(R.string.title_movies);
            case 1:
                return mContext.getString(R.string.title_tv);
            default:
                return null;
        }
    }

}
