package com.example.moviecatalogueapi.ui.fav;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.moviecatalogueapi.R;
import com.example.moviecatalogueapi.db.DatabaseContract;
import com.example.moviecatalogueapi.db.DatabaseHelper;
import com.example.moviecatalogueapi.model.Movie;
import com.example.moviecatalogueapi.ui.movie.DetailMovieActivity;
import com.example.moviecatalogueapi.ui.movie.adapter.MovieAdapter;
import com.example.moviecatalogueapi.ui.movie.view.MovieClickListener;
import com.example.moviecatalogueapi.utils.Constant;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavMovieFragment extends Fragment implements MovieClickListener, LoaderManager.LoaderCallbacks<Cursor> {

    private static final String MOVIE_STATE_KEY = "MOVIE_STATE";

    private static final int FAVORITE_MOVIES_LOADER = 0;

    @BindView(R.id.rvMovie)
    RecyclerView rvMovie;
    @BindView(R.id.tvNoData)
    TextView tvNoData;

    private MovieAdapter adapter;
    private DatabaseHelper database;

    private FavoriteActivity mActivity;

    private final ArrayList<Movie> movies = new ArrayList<>();

    public FavMovieFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity){
            mActivity = (FavoriteActivity) context;
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fav_movie, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        database = new DatabaseHelper(mActivity);

        showRecyclerView();

        if (savedInstanceState == null) {
            LoaderManager.getInstance(mActivity).initLoader(FAVORITE_MOVIES_LOADER, null, this);
        } else {
            ArrayList<Movie> stateList = savedInstanceState.getParcelableArrayList(MOVIE_STATE_KEY);
            if (stateList != null) {
                movies.addAll(stateList);
                adapter.setMovies(movies);
            }
        }
    }

    private void showRecyclerView() {
        adapter = new MovieAdapter(mActivity, this);
        rvMovie.setLayoutManager(new LinearLayoutManager(mActivity));
        rvMovie.setHasFixedSize(true);
        rvMovie.setAdapter(adapter);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle state) {
        // Save list state
        state.putParcelableArrayList(MOVIE_STATE_KEY, movies);
        super.onSaveInstanceState(state);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onItemClick(Movie movie) {
        Intent intent = new Intent(mActivity, DetailMovieActivity.class);
        intent.putExtra(Constant.EXTRA_CATALOG, movie);
        startActivity(intent);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(mActivity,
                DatabaseContract.MoviesEntry.CONTENT_URI,
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        if (data.getCount() > 0){
            this.movies.clear();
            this.movies.addAll(database.getMovieList(data));
            adapter.setMovies(movies);
            rvMovie.setVisibility(View.VISIBLE);
            tvNoData.setVisibility(View.INVISIBLE);
        } else {
            rvMovie.setVisibility(View.INVISIBLE);
            tvNoData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}
