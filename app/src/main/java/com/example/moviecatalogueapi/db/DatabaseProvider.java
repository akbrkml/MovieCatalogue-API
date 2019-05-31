package com.example.moviecatalogueapi.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DatabaseProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private DatabaseHelper mDatabaseHelper;

    private static final int MOVIE = 100;
    private static final int MOVIE_WITH_ID = 200;
    private static final int TV = 300;
    private static final int TV_WITH_ID = 400;

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = DatabaseContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, DatabaseContract.MoviesEntry.TABLE_MOVIES, MOVIE);
        matcher.addURI(authority, DatabaseContract.MoviesEntry.TABLE_MOVIES + "/#", MOVIE_WITH_ID);

        matcher.addURI(authority, DatabaseContract.TvShowEntry.TABLE_TV_SHOW, TV);
        matcher.addURI(authority, DatabaseContract.TvShowEntry.TABLE_TV_SHOW + "/#", TV_WITH_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mDatabaseHelper = new DatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            case MOVIE: {
                retCursor = mDatabaseHelper.getReadableDatabase().query(
                        DatabaseContract.MoviesEntry.TABLE_MOVIES,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                return retCursor;
            }
            case MOVIE_WITH_ID: {
                retCursor = mDatabaseHelper.getReadableDatabase().query(
                        DatabaseContract.MoviesEntry.TABLE_MOVIES,
                        projection,
                        DatabaseContract.MoviesEntry.MOVIE_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortOrder);
                return retCursor;
            }
            case TV: {
                retCursor = mDatabaseHelper.getReadableDatabase().query(
                        DatabaseContract.TvShowEntry.TABLE_TV_SHOW,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                return retCursor;
            }
            case TV_WITH_ID: {
                retCursor = mDatabaseHelper.getReadableDatabase().query(
                        DatabaseContract.TvShowEntry.TABLE_TV_SHOW,
                        projection,
                        DatabaseContract.MoviesEntry.MOVIE_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortOrder);
                return retCursor;
            }
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case MOVIE: {
                return DatabaseContract.MoviesEntry.CONTENT_DIR_TYPE;
            }
            case MOVIE_WITH_ID: {
                return DatabaseContract.MoviesEntry.CONTENT_ITEM_TYPE;
            }
            case TV: {
                return DatabaseContract.TvShowEntry.CONTENT_DIR_TYPE;
            }
            case TV_WITH_ID: {
                return DatabaseContract.TvShowEntry.CONTENT_ITEM_TYPE;
            }
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        Uri returnUri;
        switch (sUriMatcher.match(uri)) {
            case MOVIE: {
                long id = db.insert(DatabaseContract.MoviesEntry.TABLE_MOVIES, null, values);
                Log.d("ID", String.valueOf(id));
                if (id > 0) {
                    returnUri = DatabaseContract.MoviesEntry.buildUri(id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into: " + uri);
                }
                break;
            }
            case TV: {
                long id = db.insert(DatabaseContract.TvShowEntry.TABLE_TV_SHOW, null, values);
                Log.d("ID", String.valueOf(id));
                if (id > 0) {
                    returnUri = DatabaseContract.TvShowEntry.buildUri(id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into: " + uri);
                }
                break;
            }

            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);

            }
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int numDeleted;
        switch (match) {
            case MOVIE:
                numDeleted = db.delete(
                        DatabaseContract.MoviesEntry.TABLE_MOVIES, selection, selectionArgs);

                db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + DatabaseContract.MoviesEntry.TABLE_MOVIES + "'");
                break;
            case MOVIE_WITH_ID:
                numDeleted = db.delete(DatabaseContract.MoviesEntry.TABLE_MOVIES, DatabaseContract.MoviesEntry.MOVIE_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});

                db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + DatabaseContract.MoviesEntry.TABLE_MOVIES + "'");

                break;
            case TV:
                numDeleted = db.delete(
                        DatabaseContract.TvShowEntry.TABLE_TV_SHOW, selection, selectionArgs);

                db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + DatabaseContract.TvShowEntry.TABLE_TV_SHOW + "'");
                break;
            case TV_WITH_ID:
                numDeleted = db.delete(DatabaseContract.TvShowEntry.TABLE_TV_SHOW, DatabaseContract.TvShowEntry.TV_SHOW_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});

                db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + DatabaseContract.TvShowEntry.TABLE_TV_SHOW + "'");

                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        return numDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        int numUpdated;

        if (values == null) {
            throw new IllegalArgumentException("Cannot have null content values");
        }

        switch (sUriMatcher.match(uri)) {
            case MOVIE: {
                numUpdated = db.update(DatabaseContract.MoviesEntry.TABLE_MOVIES,
                        values,
                        selection,
                        selectionArgs);
                break;
            }
            case MOVIE_WITH_ID: {
                numUpdated = db.update(DatabaseContract.MoviesEntry.TABLE_MOVIES,
                        values,
                        DatabaseContract.MoviesEntry.MOVIE_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            }
            case TV: {
                numUpdated = db.update(DatabaseContract.TvShowEntry.TABLE_TV_SHOW,
                        values,
                        selection,
                        selectionArgs);
                break;
            }
            case TV_WITH_ID: {
                numUpdated = db.update(DatabaseContract.TvShowEntry.TABLE_TV_SHOW,
                        values,
                        DatabaseContract.TvShowEntry.TV_SHOW_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            }
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }

        if (numUpdated > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numUpdated;
    }
}
