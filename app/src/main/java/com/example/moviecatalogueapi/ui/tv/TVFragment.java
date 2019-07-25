package com.example.moviecatalogueapi.ui.tv;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviecatalogueapi.R;
import com.example.moviecatalogueapi.data.source.local.entity.TvShowEntity;
import com.example.moviecatalogueapi.ui.adapter.TVPagedAdapter;
import com.example.moviecatalogueapi.ui.callback.CallbackClickListener;
import com.example.moviecatalogueapi.ui.detail.DetailActivity;
import com.example.moviecatalogueapi.utils.CommonUtils;
import com.example.moviecatalogueapi.utils.Constant;
import com.example.moviecatalogueapi.viewmodel.TvShowViewModel;
import com.example.moviecatalogueapi.viewmodel.ViewModelFactory;
import com.example.moviecatalogueapi.vo.Resource;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TVFragment extends Fragment implements CallbackClickListener {

    @BindView(R.id.rvTv)
    RecyclerView rvTv;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private TVPagedAdapter adapter;
    private TvShowViewModel viewModel;

    public TVFragment() {}


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tv, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        adapter = new TVPagedAdapter(getActivity(), this);
        if (getActivity() != null)
            rvTv.setLayoutManager(new GridLayoutManager(getActivity(), CommonUtils.numberOfColumns(getActivity())));
        rvTv.setHasFixedSize(true);
        rvTv.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() != null) {
            viewModel = obtainViewModel(getActivity());
            viewModel.loadTvShows().observe(this, tvShowObserver);
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
                        loadTvShowsData(tvShows.data);
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
    private static TvShowViewModel obtainViewModel(FragmentActivity activity) {
        // Use a Factory to inject dependencies into the ViewModel
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(TvShowViewModel.class);
    }

    private void loadTvShowsData(List<TvShowEntity> data) {
        adapter.setTvShows(data);
    }

    @Override
    public void onItemClick(int id) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(Constant.ID, id);
        intent.putExtra(Constant.EXTRA_FROM, Constant.TV);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        viewModel.onCleared();
        super.onDestroyView();
    }
}
