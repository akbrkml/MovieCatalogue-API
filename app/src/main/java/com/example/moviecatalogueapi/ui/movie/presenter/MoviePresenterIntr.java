package com.example.moviecatalogueapi.ui.movie.presenter;

interface MoviePresenterIntr {
    void getMovies(String API_KEY, String language);
    void searchMovies(String API_KEY, String language, String query);
}
