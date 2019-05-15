package com.example.moviecatalogueapi.ui.tv.view;

import com.example.moviecatalogueapi.model.TvShow;

import java.util.List;

public interface TvView {
    void showTv(List<TvShow> tvShows);
    void showLoading();
    void hideLoading();
}
