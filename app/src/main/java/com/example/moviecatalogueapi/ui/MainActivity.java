package com.example.moviecatalogueapi.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.moviecatalogueapi.R;
import com.example.moviecatalogueapi.ui.movie.MovieFragment;
import com.example.moviecatalogueapi.ui.tv.TVFragment;
import com.example.moviecatalogueapi.utils.Constant;
import com.example.moviecatalogueapi.utils.SessionManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity   {

    private static final String FRAGMENT_KEY = "fragment_state";

    private OnChangeLanguageListener listener;
    private Fragment fragment = new MovieFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setUpAdapter();
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            setListener((OnChangeLanguageListener) fragment);
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
                    setListener((OnChangeLanguageListener) fragment);
                    loadFragment(fragment);
                    break;
                case R.id.navigation_tv:
                    toolbar.setTitle(getString(R.string.title_tv));
                    fragment = new TVFragment();
                    setListener((OnChangeLanguageListener) fragment);
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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_change_en) {
            SessionManager.putString(this, Constant.LANGUAGE, Constant.EN);
            listener.onClickLanguageSetting(Constant.EN);
        } else {
            SessionManager.putString(this, Constant.LANGUAGE, Constant.ID);
            listener.onClickLanguageSetting(Constant.ID);
        }
        return super.onOptionsItemSelected(item);
    }

    private void setListener(OnChangeLanguageListener listener) {
        this.listener = listener;
    }
}
