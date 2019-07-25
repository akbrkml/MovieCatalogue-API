package com.example.moviecatalogueapi.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.paging.PagedList;

import com.example.moviecatalogueapi.data.source.MovieRepository;
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

public class TvShowViewModelTest {

    private TvShowViewModel viewModel;

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    MovieRepository repository;

    @Mock
    PagedList<TvShowEntity> pagedList;

    @Mock
    Observer<Resource<PagedList<TvShowEntity>>> observer;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        viewModel = new TvShowViewModel(repository);
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void testApiFetchDataSuccess() {
        MutableLiveData<Resource<PagedList<TvShowEntity>>> dummyTvShows = new MutableLiveData<>();
        dummyTvShows.setValue(Resource.success(pagedList));

        when(repository.getTvShows()).thenReturn(dummyTvShows);

        viewModel.loadTvShows().observeForever(observer);

        verify(repository).getTvShows();
    }
}