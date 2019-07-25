package com.example.moviecatalogueapi.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.example.moviecatalogueapi.data.source.MovieRepository;
import com.example.moviecatalogueapi.data.source.local.entity.MovieEntity;
import com.example.moviecatalogueapi.data.source.remote.response.Movie;
import com.example.moviecatalogueapi.data.source.remote.response.MovieResult;
import com.example.moviecatalogueapi.vo.Resource;


public class MovieViewModel extends ViewModel {

    private LiveData<PagedList<Movie>> pagedList;

    private LiveData<Resource> networkState;

    private MutableLiveData<String> mLogin = new MutableLiveData<>();

    private final MovieRepository movieRepository;

    public MovieViewModel(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
        LiveData<MovieResult> moviesResult = Transformations.map(mLogin, input ->
                this.movieRepository.getMovieResult());

        pagedList = Transformations.switchMap(moviesResult,
                input -> input.data);

        networkState = Transformations.switchMap(moviesResult, input -> input.resource);
    }

    public LiveData<PagedList<Movie>> getPagedList() {
        return pagedList;
    }

    public LiveData<Resource> getNetworkState() {
        return networkState;
    }

    public LiveData<Resource<PagedList<MovieEntity>>> loadMovies() {
        return movieRepository.getMovies();
    }

    public void setUsername(String username) {
        mLogin.setValue(username);
    }

    public void onCleared() {
        movieRepository.onCleared();
    }
}
