package com.example.moviecatalogueapi.data.source.local.room.dao;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.moviecatalogueapi.data.source.local.entity.TvShowEntity;

import java.util.List;

@Dao
public interface TvDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertTvShows(List<TvShowEntity> tvs);

    @Update
    int updateTvShow(TvShowEntity tvShow);

    @Query("SELECT * FROM tv_show WHERE id = :tvId")
    LiveData<TvShowEntity> getTvShow(Integer tvId);

    @Query("SELECT * FROM tv_show where favorited = 1")
    DataSource.Factory<Integer, TvShowEntity> getFavoriteTvShows();

    @Query("SELECT * FROM tv_show")
    DataSource.Factory<Integer, TvShowEntity> getAllTvShows();

}
