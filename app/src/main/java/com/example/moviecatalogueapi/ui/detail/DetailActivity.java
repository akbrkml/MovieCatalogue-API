package com.example.moviecatalogueapi.ui.detail;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.example.moviecatalogueapi.R;
import com.example.moviecatalogueapi.data.source.local.entity.MovieEntity;
import com.example.moviecatalogueapi.data.source.local.entity.TvShowEntity;
import com.example.moviecatalogueapi.utils.CommonUtils;
import com.example.moviecatalogueapi.utils.Constant;
import com.example.moviecatalogueapi.viewmodel.DetailViewModel;
import com.example.moviecatalogueapi.viewmodel.ViewModelFactory;
import com.example.moviecatalogueapi.vo.Resource;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.ivPosterDetail)
    ImageView ivPoster;
    @BindView(R.id.backdrop)
    ImageView ivBackdrop;
    @BindView(R.id.year)
    TextView tvYear;
    @BindView(R.id.rating)
    TextView tvRating;
    @BindView(R.id.tvDescription)
    TextView tvDescription;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btnFavorite)
    FloatingActionButton btnFavorite;

    private DetailViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(null);
        }

        String intentFrom = getIntent().getStringExtra(Constant.EXTRA_FROM);
        int id = getIntent().getIntExtra(Constant.ID, -1);

        viewModel = obtainViewModel(this);

        if (intentFrom.equals(Constant.MOVIE)) {
            viewModel.getDetailMovie(id).observe(this, movieObserver);
        } else {
            viewModel.getDetailTv(id).observe(this, tvShowObserver);
        }

    }

    private Observer<Resource<MovieEntity>> movieObserver = new Observer<Resource<MovieEntity>>() {
        @Override
        public void onChanged(Resource<MovieEntity> detailMovie) {
            if (detailMovie != null) {
                switch (detailMovie.status) {
                    case LOADING:
                        CommonUtils.setLoading(progressBar,true);
                        break;
                    case SUCCESS:
                        CommonUtils.setLoading(progressBar,false);
                        if (detailMovie.data != null) {
                            loadDetailMovieData(detailMovie.data);
                        }
                        break;
                    case ERROR:
                        CommonUtils.setLoading(progressBar,false);
                        Toast.makeText(getApplicationContext(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }
    };

    private Observer<Resource<TvShowEntity>> tvShowObserver = new Observer<Resource<TvShowEntity>>() {
        @Override
        public void onChanged(Resource<TvShowEntity> detailTv) {
            if (detailTv != null) {
                switch (detailTv.status) {
                    case LOADING:
                        CommonUtils.setLoading(progressBar,true);
                        break;
                    case SUCCESS:
                        CommonUtils.setLoading(progressBar,false);
                        if (detailTv.data != null) {
                            loadDetailTvData(detailTv.data);
                        }
                        break;
                    case ERROR:
                        CommonUtils.setLoading(progressBar,false);
                        Toast.makeText(getApplicationContext(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }
    };

    @NonNull
    private static DetailViewModel obtainViewModel(FragmentActivity activity) {
        // Use a Factory to inject dependencies into the ViewModel
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(DetailViewModel.class);
    }

    private void loadDetailMovieData(MovieEntity data) {
        populateDataDetail(
                data.getTitle(),
                data.getReleaseDate(),
                data.getOverview(),
                data.getPosterPath(),
                data.getBackdropPath(),
                data.getVoteAverage(),
                data.isFavorited()
        );

        btnFavorite.setOnClickListener(view -> viewModel.setMovieFavorite());
    }

    private void loadDetailTvData(TvShowEntity data) {
        populateDataDetail(
                data.getName(),
                data.getFirstAirDate(),
                data.getOverview(),
                data.getPosterPath(),
                data.getBackdropPath(),
                data.getVoteAverage(),
                data.isFavorited()
        );

        btnFavorite.setOnClickListener(view -> viewModel.setTvShowFavorite());
    }

    private void setFavoriteState(boolean state) {
        if (state) {
            btnFavorite.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.heart));
        } else {
            btnFavorite.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.heart_outline));
        }
    }

    private void populateDataDetail(String title, String releaseDate, String overview, String posterPath, String backdropPath, double rating, Boolean favorite) {
        if (getSupportActionBar() != null) getSupportActionBar().setTitle(title);
        tvYear.setText(CommonUtils.dateToString(releaseDate));
        tvDescription.setText(overview);
        tvRating.setText(String.format("%s/10", rating));
        Glide.with(this).load(Constant.POSTER_URL + posterPath).into(ivPoster);
        Glide.with(this).load(Constant.POSTER_URL + backdropPath).into(ivBackdrop);
        setFavoriteState(favorite);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onDestroy() {
        viewModel.onCleared();
        super.onDestroy();
    }
}
