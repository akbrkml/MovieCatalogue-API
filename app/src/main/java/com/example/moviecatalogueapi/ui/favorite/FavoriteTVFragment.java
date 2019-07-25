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
import com.example.moviecatalogueapi.data.source.local.entity.TvShowEntity;
import com.example.moviecatalogueapi.ui.adapter.TVPagedAdapter;
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
public class FavoriteTVFragment extends Fragment implements CallbackClickListener {

    @BindView(R.id.rvFavTv)
    RecyclerView rvFavTv;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.tvNoDataTV)
    TextView tvNoData;

    private TVPagedAdapter adapter;
    private FavoriteViewModel viewModel;

    public FavoriteTVFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite_tv, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() != null) {
            viewModel = obtainViewModel(getActivity());
            viewModel.loadFavoriteTvShows().observe(this, tvShowObserver);
        }
    }

    private Observer<Resource<PagedList<TvShowEntity>>> tvShowObserver = new Observer<Resource<PagedList<TvShowEntity>>>() {
        @Override
        public void onChanged(Resource<PagedList<TvShowEntity>> tvShows) {
            if (tvShows != null) {
                switch (tvShows.status) {
                    case LOADING:
                        CommonUtils.setLoading(progressBar,true);
                        break;
                    case SUCCESS:
                        CommonUtils.setLoading(progressBar,false);
                        if (tvShows.data != null) {
                            loadFavoriteTvShows(tvShows.data);
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

    private void loadFavoriteTvShows(List<TvShowEntity> data) {
        if (data.size() == 0) {
            tvNoData.setVisibility(View.VISIBLE);
            rvFavTv.setVisibility(View.INVISIBLE);
        } else {
            adapter.setTvShows(data);
            tvNoData.setVisibility(View.INVISIBLE);
            rvFavTv.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemClick(int id) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(Constant.ID, id);
        intent.putExtra(Constant.EXTRA_FROM, Constant.TV);
        startActivity(intent);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        adapter = new TVPagedAdapter(getActivity(), this);
        if (getActivity() != null)
            rvFavTv.setLayoutManager(new GridLayoutManager(getActivity(), CommonUtils.numberOfColumns(getActivity())));
        rvFavTv.setHasFixedSize(true);
        rvFavTv.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        viewModel.onCleared();
        super.onDestroyView();
    }
}
