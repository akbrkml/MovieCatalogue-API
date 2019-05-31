package com.example.moviecatalogueapi.ui.tv.presenter;

import android.content.ContentValues;
import android.content.Context;

import com.example.moviecatalogueapi.R;
import com.example.moviecatalogueapi.db.DatabaseContract;
import com.example.moviecatalogueapi.db.DatabaseHelper;
import com.example.moviecatalogueapi.model.TvShow;
import com.example.moviecatalogueapi.ui.tv.view.DetailTvView;

import java.util.List;

public class DetailTvPresenter implements DetailTvPresenterIntr {

    private final DetailTvView view;
    private final DatabaseHelper database;
    private final Context context;

    public DetailTvPresenter(DetailTvView view, Context context) {
        this.view = view;
        this.context = context;
        this.database = new DatabaseHelper(context);
    }

    @Override
    public void insertTv(TvShow tv) {
        ContentValues values = DatabaseHelper.getTvContentValues(tv);
        context.getContentResolver().insert(DatabaseContract.TvShowEntry.CONTENT_URI, values);
        view.onMessage(context.getString(R.string.success_inserted));
    }

    @Override
    public void deleteTv(Integer tvId) {
        context.getContentResolver().delete(DatabaseContract.TvShowEntry.CONTENT_URI.buildUpon()
                .appendPath(String.valueOf(tvId)).build(), null, null);
        view.onMessage(context.getString(R.string.success_deleted));
    }

    @Override
    public void getTv(Integer tvId) {
        List<TvShow> tvShows;
        tvShows = database.getTvById(String.valueOf(tvId));
        if (tvShows.size() > 0) view.onGetDataTvStatus(true);
    }

}
