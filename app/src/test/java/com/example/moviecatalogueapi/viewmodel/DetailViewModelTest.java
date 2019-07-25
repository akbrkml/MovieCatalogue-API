package com.example.moviecatalogueapi.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.moviecatalogueapi.data.source.MovieRepository;
import com.example.moviecatalogueapi.data.source.local.entity.MovieEntity;
import com.example.moviecatalogueapi.data.source.local.entity.TvShowEntity;
import com.example.moviecatalogueapi.viewmodel.DetailViewModel;
import com.example.moviecatalogueapi.vo.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DetailViewModelTest {

    private DetailViewModel viewModel;

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    MovieRepository repository;

    @Mock
    MovieEntity movieEntity;

    @Mock
    TvShowEntity tvShowEntity;

    @Mock
    Observer<Resource<MovieEntity>> movieObserver;

    @Mock
    Observer<Resource<TvShowEntity>> tvObserver;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        viewModel = new DetailViewModel(repository);
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void testApiFetchDetailMovieDataSuccess() {
        MutableLiveData<Resource<MovieEntity>> movieState = new MutableLiveData<>();
        movieState.setValue(Resource.success(movieEntity));

        int movieId = 429617;
        when(repository.getDetailMovie(movieId)).thenReturn(movieState);

        viewModel.getDetailMovie(movieId).observeForever(movieObserver);

        verify(repository).getDetailMovie(movieId);
    }

    @Test
    public void testApiFetchDetailTvDataSuccess() {
        MutableLiveData<Resource<TvShowEntity>> tvState = new MutableLiveData<>();
        tvState.setValue(Resource.success(tvShowEntity));

        int tvId = 11634;
        when(repository.getDetailTvShow(tvId)).thenReturn(tvState);

        viewModel.getDetailTv(tvId).observeForever(tvObserver);

        verify(repository).getDetailTvShow(tvId);
    }
}