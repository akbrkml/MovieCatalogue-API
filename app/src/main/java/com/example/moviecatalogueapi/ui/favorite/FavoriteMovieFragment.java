package com.example.moviecatalogueapi.ui.favorite;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviecatalogueapi.R;
import com.example.moviecatalogueapi.data.source.local.entity.MovieEntity;
import com.example.moviecatalogueapi.ui.adapter.MoviePagedAdapter;
import com.example.moviecatalogueapi.ui.callback.CallbackClickListener;
import com.example.moviecatalogueapi.ui.detail.DetailActivity;
import com.example.moviecatalogueapi.utils.CommonUtils;
import com.example.moviecatalogueapi.utils.Constant;
import com.example.moviecatalogueapi.viewmodel.FavoriteViewModel;
import com.example.moviecatalogueapi.viewmodel.ViewModelFactory;
import com.example.moviecatalogueapi.vo.Resource;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteMovieFragment extends Fragment implements CallbackClickListener {

    @BindView(R.id.rvFavMovie)
    RecyclerView rvFavMovie;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.tvNoDataMovie)
    TextView tvNoData;

    private MoviePagedAdapter adapter;
    private FavoriteViewModel viewModel;

    public FavoriteMovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite_movie, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() != null) {
            viewModel = obtainViewModel(getActivity());
            viewModel.loadFavoriteMovies().observe(this, movieObserver);
        }
    }

    private Observer<Resource<PagedList<MovieEntity>>> movieObserver = new Observer<Resource<PagedList<MovieEntity>>>() {
        @Override
        public void onChanged(Resource<PagedList<MovieEntity>> movies) {
            if (movies != null) {
                switch (movies.status) {
                    case LOADING:
                        CommonUtils.setLoading(progressBar,true);
                        break;
                    case SUCCESS:
                        CommonUtils.setLoading(progressBar,false);
                        if (movies.data != null) {
                            loadFavoriteMovies(movies.data);
                        }
                        break;
                    case ERROR:
                        CommonUtils.setLoading(progressBar,false);
                        Toast.makeText(getActivity(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }
    };

    @NonNull
    private static FavoriteViewModel obtainViewModel(FragmentActivity activity) {
        // Use a Factory to inject dependencies into the ViewModel
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(FavoriteViewModel.class);
    }

    private void loadFavoriteMovies(List<MovieEntity> data) {
        if (data.size() == 0) {
            tvNoData.setVisibility(View.VISIBLE);
            rvFavMovie.setVisibility(View.INVISIBLE);
        } else {
            adapter.setMovies(data);
            tvNoData.setVisibility(View.INVISIBLE);
            rvFavMovie.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemClick(int id) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(Constant.ID, id);
        intent.putExtra(Constant.EXTRA_FROM, Constant.MOVIE);
        startActivity(intent);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        adapter = new MoviePagedAdapter(getActivity(), this);
        if (getActivity() != null)
            rvFavMovie.setLayoutManager(new GridLayoutManager(getActivity(), CommonUtils.numberOfColumns(getActivity())));
        rvFavMovie.setHasFixedSize(true);
        rvFavMovie.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        viewModel.onCleared();
        super.onDestroyView();
    }
}
