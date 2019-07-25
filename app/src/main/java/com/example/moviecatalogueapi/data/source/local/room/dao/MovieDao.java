package com.example.moviecatalogueapi.data.source.local.room.dao;


import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.moviecatalogueapi.data.source.local.entity.MovieEntity;

import java.util.List;


@Dao
public interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertMovies(List<MovieEntity> movies);

    @Update
    int updateMovie(MovieEntity movie);

    @Query("SELECT * FROM movie WHERE id = :movieId")
    LiveData<MovieEntity> getMovie(Integer movieId);

    @Query("SELECT * FROM movie where favorited = 1")
    DataSource.Factory<Integer, MovieEntity> getFavoriteMovies();

    @Query("SELECT * FROM movie")
    DataSource.Factory<Integer, MovieEntity> getAllMovies();

}
