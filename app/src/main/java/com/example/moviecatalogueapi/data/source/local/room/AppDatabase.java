package com.example.moviecatalogueapi.data.source.local.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.moviecatalogueapi.data.source.local.entity.MovieEntity;
import com.example.moviecatalogueapi.data.source.local.entity.TvShowEntity;
import com.example.moviecatalogueapi.data.source.local.room.dao.MovieDao;
import com.example.moviecatalogueapi.data.source.local.room.dao.TvDao;

@Database(entities = {MovieEntity.class, TvShowEntity.class}, version = 1,  exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract MovieDao movieDao() ;
    public abstract TvDao tvDao() ;

    private static AppDatabase INSTANCE;

    private static final Object sLock = new Object();

    public static AppDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE =
                        Room.databaseBuilder(context.getApplicationContext(),
                                AppDatabase.class, "movie_db")
                                .build();
            }
            return INSTANCE;
        }
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

}
