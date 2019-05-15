package com.example.moviecatalogueapi.ui.movie.presenter;

import android.util.Log;

import com.example.moviecatalogueapi.model.MovieResponse;
import com.example.moviecatalogueapi.network.Network;
import com.example.moviecatalogueapi.network.Routes;
import com.example.moviecatalogueapi.ui.movie.view.MovieView;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class MoviePresenter implements MoviePresenterIntr {

    private static final String TAG = MoviePresenter.class.getSimpleName();

    private final MovieView movieView;
    private final Routes routes;

    public MoviePresenter(MovieView movieView) {
        this.movieView = movieView;
        routes = Network.builder().create(Routes.class);
    }

    @Override
    public void getMovies(String API_KEY, String language) {
        movieView.showLoading();
        routes.getMovies(API_KEY, language)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<MovieResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<MovieResponse> responseBodyResponse) {
                        MovieResponse response = responseBodyResponse.body();
                        if (response != null) {
                            movieView.showList(response.getResults());
                            Log.i(TAG, response.getResults().toString());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError", e);
                    }

                    @Override
                    public void onComplete() {
                        movieView.hideLoading();
                        Log.i(TAG, "onComplete");
                    }
                });
    }
}
