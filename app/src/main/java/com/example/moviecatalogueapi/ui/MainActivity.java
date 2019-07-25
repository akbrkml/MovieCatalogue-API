package com.example.moviecatalogueapi.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.moviecatalogueapi.R;
import com.example.moviecatalogueapi.ui.favorite.FavoriteFragment;
import com.example.moviecatalogueapi.ui.movie.MovieFragment;
import com.example.moviecatalogueapi.ui.tv.TVFragment;
import com.example.moviecatalogueapi.utils.Constant;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private Fragment fragment = new MovieFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initViews();

        if (savedInstanceState == null) {
            loadFragment(fragment);
            if (getSupportActionBar() != null)
                getSupportActionBar().setTitle(getString(R.string.title_movies));
        } else {
            fragment = getSupportFragmentManager().getFragment(savedInstanceState, Constant.FRAGMENT_KEY);
            loadFragment(fragment);
        }

    }

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.frame_container)
    FrameLayout mainPager;
    @BindView(R.id.nav_view)
    BottomNavigationView navView;

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            navView.getMenu().findItem(item.getItemId()).setChecked(true);
            switch (item.getItemId()) {
                case R.id.navigation_movie:
                    if (getSupportActionBar() != null)
                        getSupportActionBar().setTitle(getString(R.string.title_movies));
                    fragment = new MovieFragment();
                    loadFragment(fragment);
                    break;
                case R.id.navigation_tv:
                    if (getSupportActionBar() != null)
                        getSupportActionBar().setTitle(getString(R.string.title_tv));
                    fragment = new TVFragment();
                    loadFragment(fragment);
                    break;
                case R.id.navigation_fav:
                    if (getSupportActionBar() != null)
                        getSupportActionBar().setTitle(getString(R.string.title_favorite));
                    fragment = new FavoriteFragment();
                    loadFragment(fragment);
                    break;
            }
            return false;
        }
    };

    private void initViews() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setTitle(null);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_container, fragment)
                    .commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getSupportFragmentManager().putFragment(outState, Constant.FRAGMENT_KEY, fragment);
        super.onSaveInstanceState(outState);
    }

}
