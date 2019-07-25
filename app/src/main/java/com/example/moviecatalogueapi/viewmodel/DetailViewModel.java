package com.example.moviecatalogueapi.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.moviecatalogueapi.data.source.MovieRepository;
import com.example.moviecatalogueapi.data.source.local.entity.MovieEntity;
import com.example.moviecatalogueapi.data.source.local.entity.TvShowEntity;
import com.example.moviecatalogueapi.vo.Resource;

public class DetailViewModel extends ViewModel {

    private final MovieRepository movieRepository;

    private LiveData<Resource<MovieEntity>> movieResource;
    private LiveData<Resource<TvShowEntity>> tvShowResource;

    public DetailViewModel(MovieRepository repository) {
        movieRepository = repository;
    }

    public LiveData<Resource<MovieEntity>> getDetailMovie(int movieId) {
        movieResource = movieRepository.getDetailMovie(movieId);
        return movieResource;
    }

    public LiveData<Resource<TvShowEntity>> getDetailTv(int tvId) {
        tvShowResource = movieRepository.getDetailTvShow(tvId);
        return tvShowResource;
    }

    public void setMovieFavorite() {
        if (movieResource.getValue() != null) {
            MovieEntity movieEntity = movieResource.getValue().data;

            if (movieEntity != null) {
                boolean newState = !movieEntity.isFavorited();
                movieRepository.setMovieFavorite(movieEntity, newState);
            }
        }
    }

    public void setTvShowFavorite() {
        if (tvShowResource.getValue() != null) {
            TvShowEntity tvShowEntity = tvShowResource.getValue().data;

            if (tvShowEntity != null) {
                final boolean newState = !tvShowEntity.isFavorited();
                movieRepository.setTvShowFavorite(tvShowEntity, newState);
            }
        }
    }

    public void onCleared() {
        movieRepository.onCleared();
    }
}
