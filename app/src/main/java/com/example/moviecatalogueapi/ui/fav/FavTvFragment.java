package com.example.moviecatalogueapi.ui.fav;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviecatalogueapi.R;
import com.example.moviecatalogueapi.db.AppDatabase;
import com.example.moviecatalogueapi.model.TvShow;
import com.example.moviecatalogueapi.ui.tv.DetailTvActivity;
import com.example.moviecatalogueapi.ui.tv.adapter.TvAdapter;
import com.example.moviecatalogueapi.ui.tv.view.TvClickListener;
import com.example.moviecatalogueapi.utils.Constant;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavTvFragment extends Fragment implements TvClickListener {

    private static final String TV_STATE_KEY = "TV_STATE";

    @BindView(R.id.rvTv)
    RecyclerView rvTv;
    @BindView(R.id.tvNoData)
    TextView tvNoData;

    private TvAdapter adapter;
    private final CompositeDisposable disposable = new CompositeDisposable();
    private AppDatabase database;

    private final ArrayList<TvShow> tvShows = new ArrayList<>();

    public FavTvFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fav_tv, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        database = AppDatabase.getAppDatabase(getActivity());
        showRecyclerView();

        if (savedInstanceState == null) {
            showData();
        } else {
            ArrayList<TvShow> stateList = savedInstanceState.getParcelableArrayList(TV_STATE_KEY);
            if (stateList != null) {
                tvShows.addAll(stateList);
                adapter.setTvShows(tvShows);
            }
        }
    }

    private void showRecyclerView() {
        adapter = new TvAdapter(getActivity(), this);
        rvTv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvTv.setHasFixedSize(true);
        rvTv.setAdapter(adapter);
    }

    private void showData() {
        disposable.add(
                database.tvDao().getAllTvShows()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(tvs -> {
                                    if (tvs.size() > 0) {
                                        this.tvShows.addAll(tvs);
                                        adapter.setTvShows(tvs);
                                        rvTv.setVisibility(View.VISIBLE);
                                        tvNoData.setVisibility(View.INVISIBLE);
                                    } else {
                                        rvTv.setVisibility(View.INVISIBLE);
                                        tvNoData.setVisibility(View.VISIBLE);
                                    }
                                }
                        )
        );
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle state) {
        // Save list state
        state.putParcelableArrayList(TV_STATE_KEY, tvShows);
        super.onSaveInstanceState(state);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onItemClick(TvShow tvShow) {
        Intent intent = new Intent(getActivity(), DetailTvActivity.class);
        intent.putExtra(Constant.EXTRA_CATALOG, tvShow);
        startActivity(intent);
    }
}
