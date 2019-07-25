package com.example.moviecatalogueapi.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.example.moviecatalogueapi.data.source.MovieRepository;
import com.example.moviecatalogueapi.data.source.local.entity.MovieEntity;
import com.example.moviecatalogueapi.data.source.local.entity.TvShowEntity;
import com.example.moviecatalogueapi.vo.Resource;


public class FavoriteViewModel extends ViewModel {

    private final MovieRepository movieRepository;

    public FavoriteViewModel(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public LiveData<Resource<PagedList<MovieEntity>>> loadFavoriteMovies() {
        return movieRepository.getFavoriteMovies();
    }

    public LiveData<Resource<PagedList<TvShowEntity>>> loadFavoriteTvShows() {
        return movieRepository.getFavoriteTvShows();
    }

    public void onCleared() {
        movieRepository.onCleared();
    }
}
