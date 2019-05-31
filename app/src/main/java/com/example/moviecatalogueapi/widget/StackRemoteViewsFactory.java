package com.example.moviecatalogueapi.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.moviecatalogueapi.GlideApp;
import com.example.moviecatalogueapi.R;
import com.example.moviecatalogueapi.db.DatabaseContract;
import com.example.moviecatalogueapi.model.Movie;
import com.example.moviecatalogueapi.utils.Constant;

class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private final Context mContext;
    private Cursor cursor;

    StackRemoteViewsFactory(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        if (cursor != null){
            cursor.close();
        }

        final long identityToken = Binder.clearCallingIdentity();

        cursor = mContext.getContentResolver().query(
                DatabaseContract.MoviesEntry.CONTENT_URI,
                null, null, null, null);

        // querying ke database
        cursor = mContext.getContentResolver().query(DatabaseContract.MoviesEntry.CONTENT_URI, null, null, null, null);

        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {
        if (cursor == null) return;
        cursor.close();
    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Movie movie = getMovieItem(position);
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);

        Bitmap bitmap = null;
        try {
            bitmap = GlideApp.with(mContext)
                    .asBitmap()
                    .load(Constant.POSTER_URL + movie.getPosterPath())
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
        return cursor.moveToPosition(position) ? cursor.getLong(0) : position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    private Movie getMovieItem(int position) {
        if (!cursor.moveToPosition(position)) throw new IllegalStateException("Error exception!");
        Movie movie = new Movie();
        movie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(
                DatabaseContract.MoviesEntry.ID)));
        movie.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MoviesEntry.MOVIE_TITLE)));
        movie.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MoviesEntry.MOVIE_POSTER_PATH)));
        return movie;
    }
}
