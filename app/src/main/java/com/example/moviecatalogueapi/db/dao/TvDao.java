package com.example.moviecatalogueapi.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.moviecatalogueapi.model.TvShow;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;

@Dao
public interface TvDao {

    @Insert
    void insertTvShow (TvShow tv);

    @Query("SELECT * FROM TvShow WHERE id = :tvId")
    Maybe<TvShow> getTvShow(Integer tvId);

    @Query("SELECT * FROM TvShow")
    Flowable<List<TvShow>> getAllTvShows();

    @Query("DELETE FROM TvShow WHERE id = :tvId")
    void deleteTvShows (Integer tvId);
}
