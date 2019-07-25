package com.example.moviecatalogueapi.ui.movie;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviecatalogueapi.R;
import com.example.moviecatalogueapi.ui.adapter.MoviePagedAdapter;
import com.example.moviecatalogueapi.ui.callback.CallbackClickListener;
import com.example.moviecatalogueapi.ui.detail.DetailActivity;
import com.example.moviecatalogueapi.utils.CommonUtils;
import com.example.moviecatalogueapi.utils.Constant;
import com.example.moviecatalogueapi.viewmodel.MovieViewModel;
import com.example.moviecatalogueapi.viewmodel.ViewModelFactory;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieFragment extends Fragment implements CallbackClickListener {


    @BindView(R.id.rvMovie)
    RecyclerView rvMovie;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private MoviePagedAdapter adapter;
    private MovieViewModel viewModel;

    public MovieFragment() {}


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        ButterKnife.bind(this, view);
        return view;
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
            rvMovie.setLayoutManager(new GridLayoutManager(getActivity(), CommonUtils.numberOfColumns(getActivity())));
        rvMovie.setHasFixedSize(true);
        rvMovie.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() != null) {

            viewModel = obtainViewModel(getActivity());

            viewModel.setUsername("Kamaludin");
            viewModel.getPagedList().observe(this, movies ->
                    adapter.submitList(movies));

            viewModel.getNetworkState().observe(this, resource ->
                    adapter.setNetworkState(resource));

        }
    }

    @NonNull
    private static MovieViewModel obtainViewModel(FragmentActivity activity) {
        // Use a Factory to inject dependencies into the ViewModel
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(MovieViewModel.class);
    }

    @Override
    public void onDestroyView() {
        viewModel.onCleared();
        super.onDestroyView();
    }
}
