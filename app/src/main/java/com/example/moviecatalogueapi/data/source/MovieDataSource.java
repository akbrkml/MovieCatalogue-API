package com.example.moviecatalogueapi.data.source;

import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.example.moviecatalogueapi.data.source.local.entity.MovieEntity;
import com.example.moviecatalogueapi.data.source.local.entity.TvShowEntity;
import com.example.moviecatalogueapi.data.source.remote.response.MovieResult;
import com.example.moviecatalogueapi.vo.Resource;


public interface MovieDataSource {

    LiveData<Resource<PagedList<MovieEntity>>> getMovies();

    LiveData<Resource<PagedList<TvShowEntity>>> getTvShows();

    LiveData<Resource<PagedList<TvShowEntity>>> getFavoriteTvShows();

    LiveData<Resource<PagedList<MovieEntity>>> getFavoriteMovies();

    LiveData<Resource<MovieEntity>> getDetailMovie(int movieId);

    LiveData<Resource<TvShowEntity>> getDetailTvShow(int tvId);

    MovieResult getMovieResult();

    void setMovieFavorite(MovieEntity movieEntity, boolean state);

    void setTvShowFavorite(TvShowEntity tvShowEntity, boolean state);
}
