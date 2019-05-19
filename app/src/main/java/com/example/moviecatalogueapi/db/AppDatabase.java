package com.example.moviecatalogueapi.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.moviecatalogueapi.db.dao.MovieDao;
import com.example.moviecatalogueapi.db.dao.TvDao;
import com.example.moviecatalogueapi.model.Movie;
import com.example.moviecatalogueapi.model.TvShow;
import com.example.moviecatalogueapi.utils.Constant;

@Database(entities = {Movie.class, TvShow.class}, version = 1,  exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract MovieDao movieDao() ;
    public abstract TvDao tvDao() ;

    private static AppDatabase INSTANCE;

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, Constant.DATABASE_NAME)
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

}
