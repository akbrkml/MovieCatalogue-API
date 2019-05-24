package com.example.moviecatalogueapi.ui.tv.presenter;

interface TvPresenterIntr {
    void getTvShows(String API_KEY, String language);
    void searchTvShows(String API_KEY, String language, String query);
}
