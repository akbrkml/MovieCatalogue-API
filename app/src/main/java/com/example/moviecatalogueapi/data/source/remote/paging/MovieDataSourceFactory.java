package com.example.moviecatalogueapi.data.source.remote.paging;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.example.moviecatalogueapi.data.source.remote.RemoteRepository;
import com.example.moviecatalogueapi.data.source.remote.response.Movie;
import com.example.moviecatalogueapi.network.Routes;

import io.reactivex.disposables.CompositeDisposable;

public class MovieDataSourceFactory extends DataSource.Factory<Integer, Movie> {

    public MutableLiveData<MoviePagedKeyDataSource> movieDataSource;
    private Routes routes;
    private CompositeDisposable compositeDisposable;

    public MovieDataSourceFactory(Routes routes, CompositeDisposable compositeDisposable) {
        this.routes = routes;
        this.compositeDisposable = compositeDisposable;
        this.movieDataSource = new MutableLiveData<>();
    }

    @NonNull
    @Override
    public DataSource<Integer, Movie> create() {
        MoviePagedKeyDataSource movieDataSource = new MoviePagedKeyDataSource(routes, compositeDisposable);
        this.movieDataSource.postValue(movieDataSource);
        return movieDataSource;
    }
}
