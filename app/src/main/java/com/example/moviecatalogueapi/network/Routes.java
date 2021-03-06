package com.example.moviecatalogueapi.network;

import com.example.moviecatalogueapi.model.MovieResponse;
import com.example.moviecatalogueapi.model.TvShowResponse;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Routes {

    @GET("movie")
    Observable<Response<MovieResponse>> getMovies(
            @Query("api_key") String API_KEY,
            @Query("language") String language
    );

    @GET("tv")
    Observable<Response<TvShowResponse>> getTvShows(
            @Query("api_key") String API_KEY,
            @Query("language") String language
    );
}
