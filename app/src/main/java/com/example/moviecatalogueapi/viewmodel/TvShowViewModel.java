package com.example.moviecatalogueapi.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.example.moviecatalogueapi.data.source.MovieRepository;
import com.example.moviecatalogueapi.data.source.local.entity.TvShowEntity;
import com.example.moviecatalogueapi.vo.Resource;


public class TvShowViewModel extends ViewModel {

    private MovieRepository movieRepository;

    public TvShowViewModel(MovieRepository mMovieRepository) {
        movieRepository = mMovieRepository;
    }

    public LiveData<Resource<PagedList<TvShowEntity>>> loadTvShows() {
        return movieRepository.getTvShows();
    }

    public void onCleared() {
        movieRepository.onCleared();
    }

}
