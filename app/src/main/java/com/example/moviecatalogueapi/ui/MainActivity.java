package com.example.moviecatalogueapi.ui;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.moviecatalogueapi.R;
import com.example.moviecatalogueapi.ui.fav.FavoriteActivity;
import com.example.moviecatalogueapi.ui.movie.MovieFragment;
import com.example.moviecatalogueapi.ui.setting.SettingsActivity;
import com.example.moviecatalogueapi.ui.tv.TVFragment;
import com.example.moviecatalogueapi.utils.Constant;
import com.example.moviecatalogueapi.utils.SessionManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity   {

    private static final String FRAGMENT_KEY = "fragment_state";

    private OnChangeLanguageListener listener;
    private OnQueryChangeListener queryChangeListener;
    private Fragment fragment = new MovieFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setUpAdapter();
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            loadFragment(fragment);
        } else {
            fragment = getSupportFragmentManager().getFragment(savedInstanceState, FRAGMENT_KEY);
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
                    toolbar.setTitle(getString(R.string.title_movies));
                    fragment = new MovieFragment();
                    loadFragment(fragment);
                    break;
                case R.id.navigation_tv:
                    toolbar.setTitle(getString(R.string.title_tv));
                    fragment = new TVFragment();
                    loadFragment(fragment);
                    break;
            }
            return false;
        }
    };

    private void setUpAdapter() {
        toolbar.setTitle(getString(R.string.title_movies));
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
        getSupportFragmentManager().putFragment(outState, FRAGMENT_KEY, fragment);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) MainActivity.this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(MainActivity.this.getComponentName()));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    if (newText.length() > 3) queryChangeListener.onQueryChange(newText);
                    return true;
                }
            });
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_change_en) {
            SessionManager.putString(this, Constant.LANGUAGE, Constant.EN);
            listener.onClickLanguageSetting(Constant.EN);
        } else if (item.getItemId() == R.id.action_change_id){
            SessionManager.putString(this, Constant.LANGUAGE, Constant.ID);
            listener.onClickLanguageSetting(Constant.ID);
        } else if (item.getItemId() == R.id.action_setting){
            startActivity(new Intent(this, SettingsActivity.class));
        } else {
            startActivity(new Intent(this, FavoriteActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    public void setOnChangeLanguageListener(OnChangeLanguageListener listener) {
        this.listener = listener;
    }

    public void setQueryChangeListener(OnQueryChangeListener queryChangeListener) {
        this.queryChangeListener = queryChangeListener;
    }
}
