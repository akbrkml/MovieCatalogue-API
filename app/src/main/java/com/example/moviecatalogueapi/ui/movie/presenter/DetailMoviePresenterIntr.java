package com.example.moviecatalogueapi.ui.movie.presenter;

import com.example.moviecatalogueapi.model.Movie;

interface DetailMoviePresenterIntr {
    void insertMovie(Movie movie);
    void deleteMovie(Integer movieId);
    void getMovie(Integer movieId);
    void clear();
}
