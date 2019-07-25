package com.example.moviecatalogueapi.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.paging.PagedList;

import com.example.moviecatalogueapi.data.source.MovieRepository;
import com.example.moviecatalogueapi.data.source.local.entity.MovieEntity;
import com.example.moviecatalogueapi.data.source.local.entity.TvShowEntity;
import com.example.moviecatalogueapi.vo.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FavoriteViewModelTest {

    private FavoriteViewModel viewModel;

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    MovieRepository repository;

    @Mock
    PagedList<MovieEntity> pagedListMovie;

    @Mock
    PagedList<TvShowEntity> pagedListTvShow;

    @Mock
    Observer<Resource<PagedList<MovieEntity>>> observerMovie;

    @Mock
    Observer<Resource<PagedList<TvShowEntity>>> observerTv;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        viewModel = new FavoriteViewModel(repository);
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void testFetchFavoriteMovieDataSuccess() {
        MutableLiveData<Resource<PagedList<MovieEntity>>> dummyMovies = new MutableLiveData<>();
        dummyMovies.setValue(Resource.success(pagedListMovie));

        when(repository.getFavoriteMovies()).thenReturn(dummyMovies);

        viewModel.loadFavoriteMovies().observeForever(observerMovie);

        verify(repository).getFavoriteMovies();
    }

    @Test
    public void testFetchFavoriteTvDataSuccess() {
        MutableLiveData<Resource<PagedList<TvShowEntity>>> dummyTvShows = new MutableLiveData<>();
        dummyTvShows.setValue(Resource.success(pagedListTvShow));

        when(repository.getFavoriteTvShows()).thenReturn(dummyTvShows);

        viewModel.loadFavoriteTvShows().observeForever(observerTv);

        verify(repository).getFavoriteTvShows();
    }
}