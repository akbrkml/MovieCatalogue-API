package com.example.moviecatalogueapi.data.source.remote;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.moviecatalogueapi.BuildConfig;
import com.example.moviecatalogueapi.data.source.remote.paging.MovieDataSourceFactory;
import com.example.moviecatalogueapi.data.source.remote.response.DetailMovie;
import com.example.moviecatalogueapi.data.source.remote.response.DetailTv;
import com.example.moviecatalogueapi.data.source.remote.response.Movie;
import com.example.moviecatalogueapi.data.source.remote.response.MovieResponse;
import com.example.moviecatalogueapi.data.source.remote.response.MovieResult;
import com.example.moviecatalogueapi.data.source.remote.response.TvShowResponse;
import com.example.moviecatalogueapi.network.Network;
import com.example.moviecatalogueapi.network.Routes;
import com.example.moviecatalogueapi.utils.Constant;
import com.example.moviecatalogueapi.utils.EspressoIdlingResources;
import com.example.moviecatalogueapi.utils.RxSingleSchedulers;
import com.example.moviecatalogueapi.vo.Resource;

import io.reactivex.disposables.CompositeDisposable;

public class RemoteRepository {

    private final Routes routes;
    private RxSingleSchedulers rxSingleSchedulers;
    private static RemoteRepository INSTANCE;
    private CompositeDisposable disposables = new CompositeDisposable();

    public static RemoteRepository getInstance(RxSingleSchedulers rxSingleSchedulers) {
        if (INSTANCE == null){
            INSTANCE = new RemoteRepository(rxSingleSchedulers);
        }
        return INSTANCE;
    }

    private RemoteRepository(RxSingleSchedulers rxSingleSchedulers) {
        this.rxSingleSchedulers = rxSingleSchedulers;
        this.routes = Network.builder().create(Routes.class);
    }

    public LiveData<ApiResponse<MovieResponse>> getMovies() {
        MutableLiveData<ApiResponse<MovieResponse>> movieList = new MutableLiveData<>();

        EspressoIdlingResources.increment();
        disposables.add(
                routes.getMovies(BuildConfig.API_KEY, Constant.EN, 1)
                        .compose(rxSingleSchedulers.applySchedulers())
                        .subscribe(
                                response -> {
                                    EspressoIdlingResources.decrement();
                                    movieList.postValue(ApiResponse.success(response));
                                },
                                error -> EspressoIdlingResources.decrement()
                        ));

        return movieList;
    }

    public LiveData<ApiResponse<TvShowResponse>> getTvShows() {
        MutableLiveData<ApiResponse<TvShowResponse>> tvList = new MutableLiveData<>();

        EspressoIdlingResources.increment();
        disposables.add(
                routes.getTvShows(BuildConfig.API_KEY, Constant.EN, 1)
                        .compose(rxSingleSchedulers.applySchedulers())
                        .subscribe(
                                response -> {
                                    EspressoIdlingResources.decrement();
                                    tvList.postValue(ApiResponse.success(response));
                                },
                                error -> EspressoIdlingResources.decrement()
                        ));

        return tvList;
    }

    public LiveData<ApiResponse<DetailMovie>> getDetailMovie(int movieId) {
        MutableLiveData<ApiResponse<DetailMovie>> detailMovie = new MutableLiveData<>();

        EspressoIdlingResources.increment();
        disposables.add(
                routes.getDetailMovie(movieId, BuildConfig.API_KEY, Constant.EN)
                        .compose(rxSingleSchedulers.applySchedulers())
                        .subscribe(
                                response -> {
                                    EspressoIdlingResources.decrement();
                                    detailMovie.postValue(ApiResponse.success(response));
                                },
                                error -> EspressoIdlingResources.decrement()
                        ));

        return detailMovie;
    }

    public LiveData<ApiResponse<DetailTv>> getDetailTv(int tvId) {
        MutableLiveData<ApiResponse<DetailTv>> detailMovie = new MutableLiveData<>();

        EspressoIdlingResources.increment();
        disposables.add(
                routes.getDetailTvShow(tvId, BuildConfig.API_KEY, Constant.EN)
                        .compose(rxSingleSchedulers.applySchedulers())
                        .subscribe(
                                response -> {
                                    EspressoIdlingResources.decrement();
                                    detailMovie.postValue(ApiResponse.success(response));
                                },
                                error -> EspressoIdlingResources.decrement()
                        ));

        return detailMovie;
    }

    public MovieResult getMovieResult() {
        MovieDataSourceFactory sourceFactory =
                new MovieDataSourceFactory(routes, disposables);

        // paging configuration
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(20)
                .build();

        // Get the paged list
        LiveData<PagedList<Movie>> moviesPagedList = new LivePagedListBuilder<>(sourceFactory, config)
                .build();

        LiveData<Resource> networkState = Transformations.switchMap(sourceFactory.movieDataSource, input ->
                input.networkState);

        // Get pagedList and network errors exposed to the viewmodel
        return new MovieResult(
                moviesPagedList,
                networkState,
                sourceFactory.movieDataSource
        );
    }

}
