package com.example.moviecatalogueapi.data.source.remote.response;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PagedList;

import com.example.moviecatalogueapi.data.source.remote.paging.MoviePagedKeyDataSource;
import com.example.moviecatalogueapi.vo.Resource;

public class MovieResult {

    public LiveData<PagedList<Movie>> data;
    public LiveData<Resource> resource;
    public MutableLiveData<MoviePagedKeyDataSource> sourceLiveData;

    public MovieResult(LiveData<PagedList<Movie>> data,
                            LiveData<Resource> resource,
                            MutableLiveData<MoviePagedKeyDataSource> sourceLiveData) {
        this.data = data;
        this.resource = resource;
        this.sourceLiveData = sourceLiveData;
    }
}
