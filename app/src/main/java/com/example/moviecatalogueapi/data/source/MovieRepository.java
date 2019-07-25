package com.example.moviecatalogueapi.data.source;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.moviecatalogueapi.data.source.local.LocalRepository;
import com.example.moviecatalogueapi.data.source.local.entity.MovieEntity;
import com.example.moviecatalogueapi.data.source.local.entity.TvShowEntity;
import com.example.moviecatalogueapi.data.source.remote.ApiResponse;
import com.example.moviecatalogueapi.data.source.remote.RemoteRepository;
import com.example.moviecatalogueapi.data.source.remote.response.DetailMovie;
import com.example.moviecatalogueapi.data.source.remote.response.DetailTv;
import com.example.moviecatalogueapi.data.source.remote.response.Movie;
import com.example.moviecatalogueapi.data.source.remote.response.MovieResponse;
import com.example.moviecatalogueapi.data.source.remote.response.MovieResult;
import com.example.moviecatalogueapi.data.source.remote.response.TvShow;
import com.example.moviecatalogueapi.data.source.remote.response.TvShowResponse;
import com.example.moviecatalogueapi.vo.NetworkBoundResource;
import com.example.moviecatalogueapi.vo.Resource;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MovieRepository implements MovieDataSource {

    private volatile static MovieRepository INSTANCE = null;

    private final RemoteRepository remoteRepository;
    private final LocalRepository localRepository;
    private CompositeDisposable disposables = new CompositeDisposable();

    private MovieRepository(@NonNull RemoteRepository remoteRepository, LocalRepository localRepository) {
        this.remoteRepository = remoteRepository;
        this.localRepository = localRepository;
    }

    public static MovieRepository getInstance(RemoteRepository remoteData, LocalRepository localRepository) {
        if (INSTANCE == null) {
            synchronized (MovieRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MovieRepository(remoteData, localRepository);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public LiveData<Resource<PagedList<MovieEntity>>> getMovies() {
        return new NetworkBoundResource<PagedList<MovieEntity>, MovieResponse>() {

            @Override
            protected LiveData<PagedList<MovieEntity>> loadFromDB() {
                return new LivePagedListBuilder<>(localRepository.getMovies(), 10).build();
            }

            @Override
            protected Boolean shouldFetch(PagedList<MovieEntity> data) {
                return (data == null) || (data.size() == 0);
            }

            @Override
            protected LiveData<ApiResponse<MovieResponse>> createCall() {
                return remoteRepository.getMovies();
            }

            @Override
            protected void saveCallResult(MovieResponse data) {
                List<MovieEntity> movieEntities = new ArrayList<>();

                for (Movie movie : data.getResults()) {
                    movieEntities.add(
                            new MovieEntity(movie.getId(),
                                    movie.getPosterPath(),
                                    movie.getOverview(),
                                    movie.getReleaseDate(),
                                    movie.getOriginalTitle(),
                                    movie.getTitle(),
                                    movie.getBackdropPath(),
                                    movie.getVoteAverage(),
                                    null)
                    );
                }
                localRepository.insertMovies(movieEntities);
            }
        }.asLiveData();
    }

    @Override
    public LiveData<Resource<PagedList<TvShowEntity>>> getTvShows() {
        return new NetworkBoundResource<PagedList<TvShowEntity>, TvShowResponse>() {

            @Override
            protected LiveData<PagedList<TvShowEntity>> loadFromDB() {
                return new LivePagedListBuilder<>(localRepository.getTvShows(), 10).build();
            }

            @Override
            protected Boolean shouldFetch(PagedList <TvShowEntity> data) {
                return (data == null) || (data.size() == 0);
            }

            @Override
            protected LiveData<ApiResponse<TvShowResponse>> createCall() {
                return remoteRepository.getTvShows();
            }

            @Override
            protected void saveCallResult(TvShowResponse data) {
                List<TvShowEntity> tvShowEntities = new ArrayList<>();

                for (TvShow tvShow : data.getResults()) {
                    tvShowEntities.add(
                            new TvShowEntity(tvShow.getId(),
                                    tvShow.getFirstAirDate(),
                                    tvShow.getOverview(),
                                    tvShow.getPosterPath(),
                                    tvShow.getBackdropPath(),
                                    tvShow.getOriginalName(),
                                    tvShow.getVoteAverage(),
                                    tvShow.getName(),
                                    null)
                    );
                }
                localRepository.insertTvShows(tvShowEntities);
            }
        }.asLiveData();
    }

    @Override
    public LiveData<Resource<PagedList<TvShowEntity>>> getFavoriteTvShows() {
        return new NetworkBoundResource<PagedList<TvShowEntity>, List<TvShowEntity>>() {

            @Override
            protected LiveData<PagedList<TvShowEntity>> loadFromDB() {
                return new LivePagedListBuilder<>(localRepository.getFavoriteTvShows(), 10).build();
            }

            @Override
            protected Boolean shouldFetch(PagedList<TvShowEntity> data) {
                return false;
            }

            @Override
            protected LiveData<ApiResponse<List<TvShowEntity>>> createCall() {
                return null;
            }

            @Override
            protected void saveCallResult(List<TvShowEntity> data) {

            }
        }.asLiveData();
    }

    @Override
    public LiveData<Resource<PagedList<MovieEntity>>> getFavoriteMovies() {
        return new NetworkBoundResource<PagedList<MovieEntity>, List<MovieEntity>>() {

            @Override
            protected LiveData<PagedList<MovieEntity>> loadFromDB() {
                return new LivePagedListBuilder<>(localRepository.getFavoriteMovies(), 10).build();
            }

            @Override
            protected Boolean shouldFetch(PagedList<MovieEntity> data) {
                return false;
            }

            @Override
            protected LiveData<ApiResponse<List<MovieEntity>>> createCall() {
                return null;
            }

            @Override
            protected void saveCallResult(List<MovieEntity> data) {

            }
        }.asLiveData();
    }

    @Override
    public LiveData<Resource<MovieEntity>> getDetailMovie(int movieId) {
        return new NetworkBoundResource<MovieEntity, DetailMovie>() {

            @Override
            protected LiveData<MovieEntity> loadFromDB() {
                return localRepository.getMovie(movieId);
            }

            @Override
            protected Boolean shouldFetch(MovieEntity data) {
                return data == null;
            }

            @Override
            protected LiveData<ApiResponse<DetailMovie>> createCall() {
                return remoteRepository.getDetailMovie(movieId);
            }

            @Override
            protected void saveCallResult(DetailMovie data) {

            }
        }.asLiveData();
    }

    @Override
    public LiveData<Resource<TvShowEntity>> getDetailTvShow(int tvId) {
        return new NetworkBoundResource<TvShowEntity, DetailTv>() {

            @Override
            protected LiveData<TvShowEntity> loadFromDB() {
                return localRepository.getTvShow(tvId);
            }

            @Override
            protected Boolean shouldFetch(TvShowEntity data) {
                return data == null;
            }

            @Override
            protected LiveData<ApiResponse<DetailTv>> createCall() {
                return remoteRepository.getDetailTv(tvId);
            }

            @Override
            protected void saveCallResult(DetailTv data) {

            }
        }.asLiveData();
    }

    @Override
    public void setMovieFavorite(MovieEntity movieEntity, boolean state) {
        Completable.fromAction(() ->
                localRepository.setMovieFavorite(movieEntity, state))
                .subscribeOn(Schedulers.computation())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Log.e("onComplete", "Berhasil disimpan");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("onError", e.getLocalizedMessage());
                    }
                });
    }

    @Override
    public void setTvShowFavorite(TvShowEntity tvShowEntity, boolean state) {
        Completable.fromAction(() ->
                localRepository.setTvShowFavorite(tvShowEntity, state))
                .subscribeOn(Schedulers.computation())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Log.e("onComplete", "Berhasil disimpan");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("onError", e.getLocalizedMessage());
                    }
                });
    }

    @Override
    public MovieResult getMovieResult() {
        return remoteRepository.getMovieResult();
    }

    public void onCleared() {
        disposables.clear();
    }
}
