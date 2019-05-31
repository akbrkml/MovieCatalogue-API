package com.example.moviecatalogueapi.ui.movie.presenter;

import android.content.ContentValues;
import android.content.Context;

import com.example.moviecatalogueapi.R;
import com.example.moviecatalogueapi.db.DatabaseContract;
import com.example.moviecatalogueapi.db.DatabaseHelper;
import com.example.moviecatalogueapi.model.Movie;
import com.example.moviecatalogueapi.ui.movie.view.DetailMovieView;

import java.util.List;

public class DetailMoviePresenter implements DetailMoviePresenterIntr {

    private final DetailMovieView view;
    private final DatabaseHelper database;
    private final Context context;

    public DetailMoviePresenter(DetailMovieView view, Context context) {
        this.view = view;
        this.context = context;
        this.database = new DatabaseHelper(context);
    }

    @Override
    public void insertMovie(Movie movie) {
        ContentValues values = DatabaseHelper.getMovieContentValues(movie);
        context.getContentResolver().insert(DatabaseContract.MoviesEntry.CONTENT_URI, values);
        view.onMessage(context.getString(R.string.success_inserted));
    }

    @Override
    public void deleteMovie(Integer movieId) {
        context.getContentResolver().delete(DatabaseContract.MoviesEntry.CONTENT_URI.buildUpon()
                .appendPath(String.valueOf(movieId)).build(), null, null);
        view.onMessage(context.getString(R.string.success_deleted));
    }

    @Override
    public void getMovie(Integer movieId) {
        List<Movie> movies;
        movies = database.getMovieById(String.valueOf(movieId));
        if (movies.size() > 0) view.onGetDataMovieStatus(true);
    }

}
