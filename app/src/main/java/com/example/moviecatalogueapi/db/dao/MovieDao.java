package com.example.moviecatalogueapi.db.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.moviecatalogueapi.model.Movie;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;

@Dao
public interface MovieDao {

    @Insert
    void insertMovie (Movie movie);

    @Query("SELECT * FROM Movie WHERE id = :movieId")
    Maybe<Movie> getMovie(Integer movieId);

    @Query("SELECT * FROM Movie")
    Flowable<List<Movie>> getAllMovies();

    @Query("DELETE FROM Movie WHERE id = :movieId")
    void deleteMovie (Integer movieId);
}
