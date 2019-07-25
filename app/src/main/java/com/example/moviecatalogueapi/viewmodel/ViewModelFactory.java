package com.example.moviecatalogueapi.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.moviecatalogueapi.di.Injection;
import com.example.moviecatalogueapi.data.source.MovieRepository;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private static volatile ViewModelFactory INSTANCE;

    private final MovieRepository mMovieRepository;

    private ViewModelFactory(MovieRepository movieRepository) {
        mMovieRepository = movieRepository;
    }

    public static ViewModelFactory getInstance(Application application) {
        if (INSTANCE == null) {
            synchronized (ViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ViewModelFactory(Injection.provideRepository(application));
                }
            }
        }
        return INSTANCE;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MovieViewModel.class)) {
            //noinspection unchecked
            return (T) new MovieViewModel(mMovieRepository);
        } else if (modelClass.isAssignableFrom(TvShowViewModel.class)) {
            //noinspection unchecked
            return (T) new TvShowViewModel(mMovieRepository);
        }
        else if (modelClass.isAssignableFrom(DetailViewModel.class)) {
            //noinspection unchecked
            return (T) new DetailViewModel(mMovieRepository);
        }
        else if (modelClass.isAssignableFrom(FavoriteViewModel.class)) {
            //noinspection unchecked
            return (T) new FavoriteViewModel(mMovieRepository);
        }

        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
