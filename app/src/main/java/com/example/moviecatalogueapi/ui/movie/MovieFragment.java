package com.example.moviecatalogueapi.ui.movie;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviecatalogueapi.BuildConfig;
import com.example.moviecatalogueapi.model.Movie;
import com.example.moviecatalogueapi.ui.OnChangeLanguageListener;
import com.example.moviecatalogueapi.ui.movie.view.MovieClickListener;
import com.example.moviecatalogueapi.ui.movie.adapter.MovieAdapter;
import com.example.moviecatalogueapi.ui.movie.presenter.MoviePresenter;
import com.example.moviecatalogueapi.ui.movie.view.MovieView;
import com.example.moviecatalogueapi.R;
import com.example.moviecatalogueapi.utils.Constant;
import com.example.moviecatalogueapi.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment implements MovieView, MovieClickListener, OnChangeLanguageListener {


    private static final String MOVIE_STATE_KEY = "MOVIE_STATE";

    @BindView(R.id.rvMovie)
    RecyclerView rvMovie;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private MovieAdapter adapter;
    private MoviePresenter presenter;
    private final ArrayList<Movie> movies = new ArrayList<>();

    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new MoviePresenter(this);
        showRecyclerView();

        if (savedInstanceState == null) {
            getMovies();
        } else {
            ArrayList<Movie> stateList = savedInstanceState.getParcelableArrayList(MOVIE_STATE_KEY);
            if (stateList != null) {
                movies.addAll(stateList);
                adapter.setMovies(movies);
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    private void showRecyclerView() {
        adapter = new MovieAdapter(getActivity(), this);
        rvMovie.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMovie.setHasFixedSize(true);
        rvMovie.setAdapter(adapter);
    }

    private void getMovies() {
        if (SessionManager.getString(getActivity(), Constant.LANGUAGE).equals(Constant.EN))
            presenter.getMovies(BuildConfig.API_KEY, Constant.EN);
        else presenter.getMovies(BuildConfig.API_KEY, Constant.ID);
    }

    @Override
    public void onItemClick(Movie movie) {
        Intent intent = new Intent(getActivity(), DetailMovieActivity.class);
        intent.putExtra(Constant.EXTRA_CATALOG, movie);
        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle state) {
        // Save list state
        state.putParcelableArrayList(MOVIE_STATE_KEY, movies);
        super.onSaveInstanceState(state);
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void showList(List<Movie> movies) {
        this.movies.addAll(movies);
        adapter.setMovies(movies);
    }

    @Override
    public void showLoading() {
        progressBar.setEnabled(true);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setEnabled(false);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClickLanguageSetting(String language) {
        presenter.getMovies(BuildConfig.API_KEY, language);
    }
}
