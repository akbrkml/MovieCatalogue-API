package com.example.moviecatalogueapi.ui.tv.presenter;

import android.content.Context;
import android.util.Log;

import com.example.moviecatalogueapi.db.AppDatabase;
import com.example.moviecatalogueapi.model.TvShow;
import com.example.moviecatalogueapi.ui.tv.view.DetailTvView;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DetailTvPresenter implements DetailTvPresenterIntr {

    private final DetailTvView view;
    private final AppDatabase database;

    private final CompositeDisposable disposable = new CompositeDisposable();

    public DetailTvPresenter(DetailTvView view, Context context) {
        this.view = view;
        this.database = AppDatabase.getAppDatabase(context);
    }

    @Override
    public void insertTv(TvShow tv) {
        Completable.fromAction(() ->
                database.tvDao().insertTvShow(tv))
                .subscribeOn(Schedulers.computation())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        view.onMessage("Berhasil disimpan");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("onError", e.getLocalizedMessage());
                    }
                });
    }

    @Override
    public void deleteTv(Integer tvId) {
        Completable.fromAction(() ->
                database.tvDao().deleteTvShows(tvId))
                .subscribeOn(Schedulers.computation())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        view.onMessage("Berhasil dihapus");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("onError", e.getLocalizedMessage());
                    }
                });
    }

    @Override
    public void getTv(Integer tvId) {
        disposable.add(database.tvDao().getTvShow(tvId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movie -> {
                    if (movie != null) view.onGetDataTvStatus(true);
                }));
    }

    @Override
    public void clear() {
        disposable.clear();
    }
}
