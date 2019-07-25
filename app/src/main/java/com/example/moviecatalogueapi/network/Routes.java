package com.example.moviecatalogueapi.network;

import com.example.moviecatalogueapi.data.source.remote.response.DetailMovie;
import com.example.moviecatalogueapi.data.source.remote.response.DetailTv;
import com.example.moviecatalogueapi.data.source.remote.response.MovieResponse;
import com.example.moviecatalogueapi.data.source.remote.response.TvShowResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Routes {

    @GET("3/discover/movie")
    Single<MovieResponse> getMovies(
            @Query("api_key") String API_KEY,
            @Query("language") String language,
            @Query("page") int pageNumber
    );

    @GET("3/discover/tv")
    Single<TvShowResponse> getTvShows(
            @Query("api_key") String API_KEY,
            @Query("language") String language,
            @Query("page") int pageNumber
    );

    @GET("3/movie/{movie_id}")
    Single<DetailMovie> getDetailMovie(
            @Path("movie_id")int movieId,
            @Query("api_key")String apiKey,
            @Query("language")String language
    );

    @GET("3/tv/{tv_id}")
    Single<DetailTv> getDetailTvShow(
            @Path("tv_id") int tvId,
            @Query("api_key")String apiKey,
            @Query("language")String language
    );
}
