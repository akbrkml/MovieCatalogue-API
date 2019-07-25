package com.example.moviecatalogueapi.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.paging.PagedList;

import com.example.moviecatalogueapi.data.source.MovieRepository;
import com.example.moviecatalogueapi.data.source.local.entity.MovieEntity;
import com.example.moviecatalogueapi.viewmodel.MovieViewModel;
import com.example.moviecatalogueapi.vo.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MovieViewModelTest {

    private MovieViewModel viewModel;

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    MovieRepository repository;

    @Mock
    PagedList<MovieEntity> pagedList;

    @Mock
    Observer<Resource<PagedList<MovieEntity>>> observer;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        viewModel = new MovieViewModel(repository);
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void testApiFetchDataSuccess() {
        // Mock API response
        MutableLiveData<Resource<PagedList<MovieEntity>>> dummyMovies = new MutableLiveData<>();
        dummyMovies.setValue(Resource.success(pagedList));

        when(repository.getMovies()).thenReturn(dummyMovies);

        viewModel.loadMovies().observeForever(observer);

        verify(repository).getMovies();
    }
}