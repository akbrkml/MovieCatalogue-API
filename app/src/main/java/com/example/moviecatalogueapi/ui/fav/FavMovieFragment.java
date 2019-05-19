package com.example.moviecatalogueapi.ui.fav;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.moviecatalogueapi.R;
import com.example.moviecatalogueapi.db.AppDatabase;
import com.example.moviecatalogueapi.model.Movie;
import com.example.moviecatalogueapi.ui.movie.DetailMovieActivity;
import com.example.moviecatalogueapi.ui.movie.adapter.MovieAdapter;
import com.example.moviecatalogueapi.ui.movie.view.MovieClickListener;
import com.example.moviecatalogueapi.utils.Constant;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavMovieFragment extends Fragment implements MovieClickListener {

    private static final String MOVIE_STATE_KEY = "MOVIE_STATE";

    @BindView(R.id.rvMovie)
    RecyclerView rvMovie;
    @BindView(R.id.tvNoData)
    TextView tvNoData;

    private MovieAdapter adapter;
    private final CompositeDisposable disposable = new CompositeDisposable();
    private AppDatabase database;

    private final ArrayList<Movie> movies = new ArrayList<>();

    public FavMovieFragment() {
        // Required empty public constructor
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

        database = AppDatabase.getAppDatabase(getActivity());
        showRecyclerView();

        if (savedInstanceState == null) {
            showData();
        } else {
            ArrayList<Movie> stateList = savedInstanceState.getParcelableArrayList(MOVIE_STATE_KEY);
            if (stateList != null) {
                movies.addAll(stateList);
                adapter.setMovies(movies);
            }
        }
    }

    private void showRecyclerView() {
        adapter = new MovieAdapter(getActivity(), this);
        rvMovie.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMovie.setHasFixedSize(true);
        rvMovie.setAdapter(adapter);
    }

    private void showData() {
        disposable.add(
                database.movieDao().getAllMovies()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(movies -> {
                            if (movies.size() > 0) {
                                this.movies.addAll(movies);
                                adapter.setMovies(movies);
                                rvMovie.setVisibility(View.VISIBLE);
                                tvNoData.setVisibility(View.INVISIBLE);
                            } else {
                                rvMovie.setVisibility(View.INVISIBLE);
                                tvNoData.setVisibility(View.VISIBLE);
                            }

                        })
        );
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
        Intent intent = new Intent(getActivity(), DetailMovieActivity.class);
        intent.putExtra(Constant.EXTRA_CATALOG, movie);
        startActivity(intent);
    }
}
