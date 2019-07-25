package com.example.moviecatalogueapi.data.source.local;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;

import com.example.moviecatalogueapi.data.source.local.entity.MovieEntity;
import com.example.moviecatalogueapi.data.source.local.entity.TvShowEntity;
import com.example.moviecatalogueapi.data.source.local.room.dao.MovieDao;
import com.example.moviecatalogueapi.data.source.local.room.dao.TvDao;

import java.util.List;


public class LocalRepository {

    private MovieDao movieDao;
    private TvDao tvDao;

    private LocalRepository(MovieDao movieDao, TvDao tvDao) {
        this.movieDao = movieDao;
        this.tvDao = tvDao;
    }

    private static LocalRepository INSTANCE;

    public static LocalRepository getInstance(MovieDao movieDao, TvDao tvDao) {
        if (INSTANCE == null) {
            INSTANCE = new LocalRepository(movieDao, tvDao);
        }
        return INSTANCE;
    }

    public DataSource.Factory<Integer, MovieEntity> getMovies() {
        return movieDao.getAllMovies();
    }

    public DataSource.Factory<Integer, MovieEntity> getFavoriteMovies() {
        return movieDao.getFavoriteMovies();
    }

    public LiveData<MovieEntity> getMovie(Integer movieId) {
        return movieDao.getMovie(movieId);
    }

    public void insertMovies(List<MovieEntity> movieEntities) {
        movieDao.insertMovies(movieEntities);
    }

    public void setMovieFavorite(MovieEntity movieEntity, boolean newState) {
        movieEntity.setFavorited(newState);
        movieDao.updateMovie(movieEntity);
    }

    public DataSource.Factory<Integer, TvShowEntity> getTvShows() {
        return tvDao.getAllTvShows();
    }

    public DataSource.Factory<Integer, TvShowEntity> getFavoriteTvShows() {
        return tvDao.getFavoriteTvShows();
    }

    public LiveData<TvShowEntity> getTvShow(Integer tvId) {
        return tvDao.getTvShow(tvId);
    }

    public void insertTvShows(List<TvShowEntity> tvShowEntities) {
        tvDao.insertTvShows(tvShowEntities);
    }

    public void setTvShowFavorite(TvShowEntity tvShowEntity, boolean newState) {
        tvShowEntity.setFavorited(newState);
        tvDao.updateTvShow(tvShowEntity);
    }
}
