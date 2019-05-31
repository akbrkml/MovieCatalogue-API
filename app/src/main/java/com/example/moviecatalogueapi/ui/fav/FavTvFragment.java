package com.example.moviecatalogueapi.ui.fav;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviecatalogueapi.R;
import com.example.moviecatalogueapi.db.DatabaseContract;
import com.example.moviecatalogueapi.db.DatabaseHelper;
import com.example.moviecatalogueapi.model.TvShow;
import com.example.moviecatalogueapi.ui.tv.DetailTvActivity;
import com.example.moviecatalogueapi.ui.tv.adapter.TvAdapter;
import com.example.moviecatalogueapi.ui.tv.view.TvClickListener;
import com.example.moviecatalogueapi.utils.Constant;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavTvFragment extends Fragment implements TvClickListener, LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TV_STATE_KEY = "TV_STATE";

    private static final int FAVORITE_TV_LOADER = 1;

    @BindView(R.id.rvTv)
    RecyclerView rvTv;
    @BindView(R.id.tvNoData)
    TextView tvNoData;

    private TvAdapter adapter;
    private DatabaseHelper database;

    private FavoriteActivity mActivity;

    private final ArrayList<TvShow> tvShows = new ArrayList<>();

    public FavTvFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity){
            mActivity = (FavoriteActivity) context;
        }
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

        database = new DatabaseHelper(mActivity);

        showRecyclerView();

        if (savedInstanceState == null) {
            LoaderManager.getInstance(mActivity).initLoader(FAVORITE_TV_LOADER, null, this);
        } else {
            ArrayList<TvShow> stateList = savedInstanceState.getParcelableArrayList(TV_STATE_KEY);
            if (stateList != null) {
                tvShows.addAll(stateList);
                adapter.setTvShows(tvShows);
            }
        }
    }

    private void showRecyclerView() {
        adapter = new TvAdapter(mActivity, this);
        rvTv.setLayoutManager(new LinearLayoutManager(mActivity));
        rvTv.setHasFixedSize(true);
        rvTv.setAdapter(adapter);
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
        Intent intent = new Intent(mActivity, DetailTvActivity.class);
        intent.putExtra(Constant.EXTRA_CATALOG, tvShow);
        startActivity(intent);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(mActivity,
                DatabaseContract.TvShowEntry.CONTENT_URI,
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        if (data.getCount() > 0){
            this.tvShows.clear();
            this.tvShows.addAll(database.getTvList(data));
            adapter.setTvShows(tvShows);
            rvTv.setVisibility(View.VISIBLE);
            tvNoData.setVisibility(View.INVISIBLE);
        } else {
            rvTv.setVisibility(View.INVISIBLE);
            tvNoData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}
