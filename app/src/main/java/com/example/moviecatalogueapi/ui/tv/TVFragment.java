package com.example.moviecatalogueapi.ui.tv;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviecatalogueapi.BuildConfig;
import com.example.moviecatalogueapi.model.TvShow;
import com.example.moviecatalogueapi.ui.DetailActivity;
import com.example.moviecatalogueapi.R;
import com.example.moviecatalogueapi.ui.OnChangeLanguageListener;
import com.example.moviecatalogueapi.ui.tv.adapter.TvAdapter;
import com.example.moviecatalogueapi.ui.tv.presenter.TvPresenter;
import com.example.moviecatalogueapi.ui.tv.view.TvClickListener;
import com.example.moviecatalogueapi.ui.tv.view.TvView;
import com.example.moviecatalogueapi.utils.Constant;
import com.example.moviecatalogueapi.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class TVFragment extends Fragment implements TvView, TvClickListener, OnChangeLanguageListener {

    private static final String TV_STATE_KEY = "TV_STATE";

    @BindView(R.id.rvTv)
    RecyclerView rvTv;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private TvAdapter adapter;
    private TvPresenter presenter;
    private final ArrayList<TvShow> tvShows = new ArrayList<>();

    public TVFragment() {
        // Required empty public constructor
    }


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
        presenter = new TvPresenter(this);
        showRecyclerView();

        if (savedInstanceState == null) {
            getTvShows();
        } else {
            ArrayList<TvShow> stateList = savedInstanceState.getParcelableArrayList(TV_STATE_KEY);
            if (stateList != null) {
                tvShows.addAll(stateList);
                adapter.setTvShows(tvShows);
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    private void showRecyclerView() {
        adapter = new TvAdapter(getActivity(), this);
        rvTv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvTv.setHasFixedSize(true);
        rvTv.setAdapter(adapter);
    }

    private void getTvShows() {
        if (SessionManager.getString(getActivity(), Constant.LANGUAGE).equals(Constant.EN))
            presenter.getTvShows(BuildConfig.API_KEY, Constant.EN);
        else presenter.getTvShows(BuildConfig.API_KEY, Constant.ID);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle state) {
        // Save list state
        state.putParcelableArrayList(TV_STATE_KEY, tvShows);
        super.onSaveInstanceState(state);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void showTv(List<TvShow> tvShows) {
        this.tvShows.addAll(tvShows);
        adapter.setTvShows(tvShows);
    }

    @Override
    public void showLoading() {
        progressBar.setEnabled(true);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setEnabled(false);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onItemClick(TvShow tvShow) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(Constant.EXTRA_CATALOG, tvShow);
        intent.putExtra(Constant.EXTRA_FROM, Constant.TV);
        startActivity(intent);
    }

    @Override
    public void onClickLanguageSetting(String language) {
        presenter.getTvShows(BuildConfig.API_KEY, language);
    }
}
