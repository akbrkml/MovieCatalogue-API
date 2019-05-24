package com.example.moviecatalogueapi.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.example.moviecatalogueapi.GlideApp;
import com.example.moviecatalogueapi.R;
import com.example.moviecatalogueapi.db.AppDatabase;
import com.example.moviecatalogueapi.model.Movie;
import com.example.moviecatalogueapi.utils.Constant;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private List<Movie> movies = new ArrayList<>();
    private final Context mContext;
    private final CompositeDisposable disposable = new CompositeDisposable();
    private AppDatabase database;

    StackRemoteViewsFactory(Context mContext) {
        this.mContext = mContext;
        this.database = AppDatabase.getAppDatabase(mContext);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        disposable.add(
                database.movieDao().getAllMovies()
                        .subscribeOn(Schedulers.computation())
                        .subscribe(movies -> {
                            if (movies.size() > 0) {
                                this.movies = movies;
                                for (Movie item: this.movies) {
                                    Log.e("onDataSetChanged", item.getPosterPath());
                                }
                            }
                        })
        );
    }

    @Override
    public void onDestroy() {
        disposable.clear();
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);

        Bitmap bitmap = null;
        try {
            bitmap = GlideApp.with(mContext)
                    .asBitmap()
                    .load(Constant.POSTER_URL + movies.get(position).getPosterPath())
                    .submit(250, 110)
                    .get();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("Widget Load Error", "error");
        }
        rv.setImageViewBitmap(R.id.imageView, bitmap);
        Bundle extras = new Bundle();
        extras.putInt(MovieTvWidget.EXTRA_ITEM, position);

        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
