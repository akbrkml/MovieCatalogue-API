package com.example.moviecatalogueapi.db;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    public static final String CONTENT_AUTHORITY = "com.example.moviecatalogueapi";

    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);


    public static final class MoviesEntry implements BaseColumns {

        // table name
        public static final String TABLE_MOVIES = "TABLE_MOVIES";

        // columns
        public static final String ID = "id";
        public static final String MOVIE_ID = "movie_id";
        public static final String MOVIE_POSTER_PATH = "movie_poster_path";
        public static final String MOVIE_OVERVIEW = "movie_overview";
        public static final String MOVIE_RELEASE_DATE = "movie_release_date";
        public static final String MOVIE_TITLE = "movie_title";

        // create content uri
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_MOVIES).build();

        // create cursor of base type directory for multiple entries
        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_MOVIES;

        // create cursor of base type item for single entry
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE +"/" + CONTENT_AUTHORITY + "/" + TABLE_MOVIES;

        // for building URIs on insertion
        public static Uri buildUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class TvShowEntry implements BaseColumns {

        // table name
        public static final String TABLE_TV_SHOW = "TABLE_TV_SHOW";

        // columns
        public static final String ID = "id";
        public static final String TV_SHOW_ID = "tv_show_id";
        public static final String TV_SHOW_POSTER_PATH = "tv_show_poster_path";
        public static final String TV_SHOW_OVERVIEW = "tv_show_overview";
        public static final String TV_SHOW_RELEASE_DATE = "tv_show_release_date";
        public static final String TV_SHOW_TITLE = "tv_show_title";

        // create content uri
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_TV_SHOW).build();

        // create cursor of base type directory for multiple entries
        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_TV_SHOW;

        // create cursor of base type item for single entry
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE +"/" + CONTENT_AUTHORITY + "/" + TABLE_TV_SHOW;

        // for building URIs on insertion
        public static Uri buildUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
