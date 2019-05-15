package com.example.moviecatalogueapi.ui.tv.presenter;

import android.util.Log;

import com.example.moviecatalogueapi.model.TvShowResponse;
import com.example.moviecatalogueapi.network.Network;
import com.example.moviecatalogueapi.network.Routes;
import com.example.moviecatalogueapi.ui.tv.view.TvView;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class TvPresenter implements TvPresenterIntr {

    private static final String TAG = TvPresenter.class.getSimpleName();

    private final TvView view;
    private final Routes routes;

    public TvPresenter(TvView view) {
        this.view = view;
        routes = Network.builder().create(Routes.class);
    }

    @Override
    public void getTvShows(String API_KEY, String language) {
        view.showLoading();
        routes.getTvShows(API_KEY, language)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<TvShowResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<TvShowResponse> responseBodyResponse) {
                        TvShowResponse response = responseBodyResponse.body();
                        if (response != null) {
                            view.showTv(response.getResults());
                            Log.i(TAG, response.getResults().toString());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError", e);
                    }

                    @Override
                    public void onComplete() {
                        view.hideLoading();
                        Log.i(TAG, "onComplete");
                    }
                });
    }

}
