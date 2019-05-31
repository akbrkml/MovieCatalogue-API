package com.example.moviecatalogueapi.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.moviecatalogueapi.model.Movie;
import com.example.moviecatalogueapi.model.TvShow;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "MOVIE_TV_DB";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE_MOVIE =
            "create table " + DatabaseContract.MoviesEntry.TABLE_MOVIES + " ("
                    + DatabaseContract.MoviesEntry.ID + " integer primary key autoincrement, "
                    + DatabaseContract.MoviesEntry.MOVIE_ID + " integer , "
                    + DatabaseContract.MoviesEntry.MOVIE_POSTER_PATH + " text , "
                    + DatabaseContract.MoviesEntry.MOVIE_OVERVIEW + " text , "
                    + DatabaseContract.MoviesEntry.MOVIE_RELEASE_DATE + " text , "
                    + DatabaseContract.MoviesEntry.MOVIE_TITLE + " text ) ; ";

    private static final String CREATE_TABLE_TV =
            "create table " + DatabaseContract.TvShowEntry.TABLE_TV_SHOW + " ("
                    + DatabaseContract.TvShowEntry.ID + " integer primary key autoincrement, "
                    + DatabaseContract.TvShowEntry.TV_SHOW_ID + " integer , "
                    + DatabaseContract.TvShowEntry.TV_SHOW_POSTER_PATH + " text , "
                    + DatabaseContract.TvShowEntry.TV_SHOW_OVERVIEW + " text , "
                    + DatabaseContract.TvShowEntry.TV_SHOW_RELEASE_DATE + " text , "
                    + DatabaseContract.TvShowEntry.TV_SHOW_TITLE + " text ) ; ";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MOVIE);
        db.execSQL(CREATE_TABLE_TV);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("DatabaseHelper", "Upgrading database from version " + oldVersion + " to " + newVersion + ". OLD DATA WILL BE DESTROYED");

        // Drop the table
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.MoviesEntry.TABLE_MOVIES);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TvShowEntry.TABLE_TV_SHOW);
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + DatabaseContract.MoviesEntry.TABLE_MOVIES + "'");
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + DatabaseContract.TvShowEntry.TABLE_TV_SHOW+ "'");

        // re-create database
        onCreate(db);
    }

    public static ContentValues getMovieContentValues(Movie movies) {

        ContentValues values = new ContentValues();
        values.put(DatabaseContract.MoviesEntry.MOVIE_ID, movies.getId());
        values.put(DatabaseContract.MoviesEntry.MOVIE_POSTER_PATH, movies.getPosterPath());
        values.put(DatabaseContract.MoviesEntry.MOVIE_OVERVIEW, movies.getOverview());
        values.put(DatabaseContract.MoviesEntry.MOVIE_RELEASE_DATE, movies.getReleaseDate());
        values.put(DatabaseContract.MoviesEntry.MOVIE_TITLE, movies.getTitle());

        return values;
    }

    public List<Movie> getMovieById(String id){
        List<Movie> movieList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + DatabaseContract.MoviesEntry.TABLE_MOVIES + " WHERE movie_id=" + id;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                int movie_id = cursor.getInt(cursor.getColumnIndex(DatabaseContract.MoviesEntry.MOVIE_ID));
                String movie_poster_path = cursor.getString(cursor.getColumnIndex(DatabaseContract.MoviesEntry.MOVIE_POSTER_PATH));
                String movie_overview = cursor.getString(cursor.getColumnIndex(DatabaseContract.MoviesEntry.MOVIE_OVERVIEW));
                String movie_release_date = cursor.getString(cursor.getColumnIndex(DatabaseContract.MoviesEntry.MOVIE_RELEASE_DATE));
                String movie_title = cursor.getString(cursor.getColumnIndex(DatabaseContract.MoviesEntry.MOVIE_TITLE));

                Movie movie = new Movie();
                movie.setId(movie_id);
                movie.setPosterPath(movie_poster_path);
                movie.setOverview(movie_overview);
                movie.setReleaseDate(movie_release_date);
                movie.setTitle(movie_title);

                // Adding movie to list
                movieList.add(movie);
            } while (cursor.moveToNext());
        }

        // return movie list
        cursor.close();
        return movieList;
    }

    public List<Movie> getMovieList(Cursor cursor) {

        List<Movie> movies = new ArrayList<>();

        if (cursor != null) {
            if (cursor.moveToFirst()){
                do{

                    int movie_id = cursor.getInt(cursor.getColumnIndex(DatabaseContract.MoviesEntry.MOVIE_ID));
                    String movie_poster_path = cursor.getString(cursor.getColumnIndex(DatabaseContract.MoviesEntry.MOVIE_POSTER_PATH));
                    String movie_overview = cursor.getString(cursor.getColumnIndex(DatabaseContract.MoviesEntry.MOVIE_OVERVIEW));
                    String movie_release_date = cursor.getString(cursor.getColumnIndex(DatabaseContract.MoviesEntry.MOVIE_RELEASE_DATE));
                    String movie_title = cursor.getString(cursor.getColumnIndex(DatabaseContract.MoviesEntry.MOVIE_TITLE));

                    Movie movie = new Movie();
                    movie.setId(movie_id);
                    movie.setPosterPath(movie_poster_path);
                    movie.setOverview(movie_overview);
                    movie.setReleaseDate(movie_release_date);
                    movie.setTitle(movie_title);

                    movies.add(movie);

                }while(cursor.moveToNext());
            }
        }

        return movies;
    }

    public static ContentValues getTvContentValues(TvShow tvShow) {

        ContentValues values = new ContentValues();
        values.put(DatabaseContract.TvShowEntry.TV_SHOW_ID, tvShow.getId());
        values.put(DatabaseContract.TvShowEntry.TV_SHOW_POSTER_PATH, tvShow.getPosterPath());
        values.put(DatabaseContract.TvShowEntry.TV_SHOW_OVERVIEW, tvShow.getOverview());
        values.put(DatabaseContract.TvShowEntry.TV_SHOW_RELEASE_DATE, tvShow.getFirstAirDate());
        values.put(DatabaseContract.TvShowEntry.TV_SHOW_TITLE, tvShow.getName());

        return values;
    }

    public List<TvShow> getTvById(String id){
        List<TvShow> tvList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + DatabaseContract.TvShowEntry.TABLE_TV_SHOW + " WHERE tv_show_id=" + id;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                int tv_id = cursor.getInt(cursor.getColumnIndex(DatabaseContract.TvShowEntry.TV_SHOW_ID));
                String tv_poster_path = cursor.getString(cursor.getColumnIndex(DatabaseContract.TvShowEntry.TV_SHOW_POSTER_PATH));
                String tv_overview = cursor.getString(cursor.getColumnIndex(DatabaseContract.TvShowEntry.TV_SHOW_OVERVIEW));
                String tv_release_date = cursor.getString(cursor.getColumnIndex(DatabaseContract.TvShowEntry.TV_SHOW_RELEASE_DATE));
                String tv_title = cursor.getString(cursor.getColumnIndex(DatabaseContract.TvShowEntry.TV_SHOW_TITLE));

                TvShow tv = new TvShow();
                tv.setId(tv_id);
                tv.setPosterPath(tv_poster_path);
                tv.setOverview(tv_overview);
                tv.setFirstAirDate(tv_release_date);
                tv.setName(tv_title);

                // Adding movie to list
                tvList.add(tv);
            } while (cursor.moveToNext());
        }

        // return movie list
        cursor.close();
        return tvList;
    }

    public List<TvShow> getTvList(Cursor cursor) {

        List<TvShow> tvList = new ArrayList<>();

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    int tv_id = cursor.getInt(cursor.getColumnIndex(DatabaseContract.TvShowEntry.TV_SHOW_ID));
                    String tv_poster_path = cursor.getString(cursor.getColumnIndex(DatabaseContract.TvShowEntry.TV_SHOW_POSTER_PATH));
                    String tv_overview = cursor.getString(cursor.getColumnIndex(DatabaseContract.TvShowEntry.TV_SHOW_OVERVIEW));
                    String tv_release_date = cursor.getString(cursor.getColumnIndex(DatabaseContract.TvShowEntry.TV_SHOW_RELEASE_DATE));
                    String tv_title = cursor.getString(cursor.getColumnIndex(DatabaseContract.TvShowEntry.TV_SHOW_TITLE));

                    TvShow tv = new TvShow();
                    tv.setId(tv_id);
                    tv.setPosterPath(tv_poster_path);
                    tv.setOverview(tv_overview);
                    tv.setFirstAirDate(tv_release_date);
                    tv.setName(tv_title);

                    // Adding movie to list
                    tvList.add(tv);
                } while (cursor.moveToNext());
            }
        }

        return tvList;
    }
}
