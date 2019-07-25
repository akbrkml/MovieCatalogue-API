package com.example.moviecatalogueapi.data.source.remote.paging;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.example.moviecatalogueapi.BuildConfig;
import com.example.moviecatalogueapi.data.source.remote.response.Movie;
import com.example.moviecatalogueapi.network.Routes;
import com.example.moviecatalogueapi.utils.Constant;
import com.example.moviecatalogueapi.utils.EspressoIdlingResources;
import com.example.moviecatalogueapi.vo.Resource;

import io.reactivex.disposables.CompositeDisposable;

public class MoviePagedKeyDataSource extends PageKeyedDataSource<Integer, Movie> {

    private static final int FIRST_PAGE = 1;

    private Routes routes;
    public MutableLiveData<Resource> networkState = new MutableLiveData<>();
    private CompositeDisposable compositeDisposable;

    public MoviePagedKeyDataSource(Routes routes, CompositeDisposable compositeDisposable) {
        this.routes = routes;
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Movie> callback) {
        networkState.postValue(Resource.loading(null));
        EspressoIdlingResources.increment();
        compositeDisposable.add(
                routes.getMovies(BuildConfig.API_KEY, Constant.EN, FIRST_PAGE)
                        .subscribe(
                                response -> {
                                    EspressoIdlingResources.decrement();
                                    networkState.postValue(Resource.success(null));
                                    callback.onResult(response.getResults(),null, FIRST_PAGE + 1);
                                },
                                error -> {
                                    EspressoIdlingResources.decrement();
                                    networkState.postValue(Resource.error(error.getMessage(), null));
                                }
                        ));
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Movie> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Movie> callback) {
        networkState.postValue(Resource.loading(null));
        EspressoIdlingResources.increment();
        compositeDisposable.add(
                routes.getMovies(BuildConfig.API_KEY, Constant.EN, params.key)
                        .subscribe(
                                response -> {
                                    EspressoIdlingResources.decrement();

                                    callback.onResult(response.getResults(), params.key + 1);
                                    networkState.postValue(Resource.success(null));
                                },
                                error -> {
                                    EspressoIdlingResources.decrement();
                                    networkState.postValue(Resource.error(error.getMessage(), null));
                                }
                        ));
    }
}
