package com.example.moviecatalogueapi.di;

import android.app.Application;

import com.example.moviecatalogueapi.data.source.MovieRepository;
import com.example.moviecatalogueapi.data.source.local.LocalRepository;
import com.example.moviecatalogueapi.data.source.local.room.AppDatabase;
import com.example.moviecatalogueapi.data.source.remote.RemoteRepository;
import com.example.moviecatalogueapi.utils.RxSingleSchedulers;

public class Injection {

    public static MovieRepository provideRepository(Application application) {

        AppDatabase appDatabase = AppDatabase.getInstance(application);

        LocalRepository localRepository = LocalRepository.getInstance(appDatabase.movieDao(), appDatabase.tvDao());
        RemoteRepository remoteRepository = RemoteRepository.getInstance(RxSingleSchedulers.DEFAULT);

        return MovieRepository.getInstance(remoteRepository, localRepository);
    }
}
