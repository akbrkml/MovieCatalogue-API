package com.example.moviecatalogueapi.ui.movie.presenter;

import android.content.Context;
import android.util.Log;

import com.example.moviecatalogueapi.db.AppDatabase;
import com.example.moviecatalogueapi.model.Movie;
import com.example.moviecatalogueapi.ui.movie.view.DetailMovieView;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DetailMoviePresenter implements DetailMoviePresenterIntr {

    private final DetailMovieView view;
    private final AppDatabase database;

    private final CompositeDisposable disposable = new CompositeDisposable();

    public DetailMoviePresenter(DetailMovieView view, Context context) {
        this.view = view;
        this.database = AppDatabase.getAppDatabase(context);
    }


    @Override
    public void insertMovie(Movie movie) {
        Completable.fromAction(() ->
                database.movieDao().insertMovie(movie))
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
    public void deleteMovie(Integer movieId) {
        Completable.fromAction(() ->
                database.movieDao().deleteMovie(movieId))
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
    public void getMovie(Integer movieId) {
        disposable.add(database.movieDao().getMovie(movieId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movie -> {
                    if (movie != null) view.onGetDataMovieStatus(true);
                }));
    }

    @Override
    public void clear() {
        disposable.clear();
    }
}
