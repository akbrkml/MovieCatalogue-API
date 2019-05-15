package com.example.moviecatalogueapi.ui.movie.view;


import com.example.moviecatalogueapi.model.Movie;

import java.util.List;

public interface MovieView {
    void showList(List<Movie> movies);
    void showLoading();
    void hideLoading();
}
