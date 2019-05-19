package com.example.moviecatalogueapi.ui.tv.presenter;

import com.example.moviecatalogueapi.model.TvShow;

interface DetailTvPresenterIntr {
    void insertTv(TvShow tv);
    void deleteTv(Integer tvId);
    void getTv(Integer tvId);
    void clear();
}
