package com.example.moviecatalogueapi.ui;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.moviecatalogueapi.R;
import com.example.moviecatalogueapi.model.Movie;
import com.example.moviecatalogueapi.model.TvShow;
import com.example.moviecatalogueapi.utils.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.ivPoster)
    ImageView ivPoster;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvDate)
    TextView tvDate;
    @BindView(R.id.tvDescription)
    TextView tvDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        if (getIntent().getStringExtra(Constant.EXTRA_FROM).equals(Constant.MOVIE)) {
            Movie catalog = getIntent().getParcelableExtra(Constant.EXTRA_CATALOG);
            if (catalog != null) {
                tvTitle.setText(catalog.getTitle());
                tvDate.setText(catalog.getReleaseDate());
                if (!catalog.getOverview().equals("")) {
                    tvDescription.setText(catalog.getOverview());
                } else tvDescription.setText(getApplicationContext().getString(R.string.message_no_overview));
                Glide.with(this).load(Constant.POSTER_URL + catalog.getPosterPath()).into(ivPoster);
                getSupportActionBar().setTitle(catalog.getTitle());
            }
        } else {
            TvShow catalog = getIntent().getParcelableExtra(Constant.EXTRA_CATALOG);
            if (catalog != null) {
                tvTitle.setText(catalog.getName());
                tvDate.setText(catalog.getFirstAirDate());
                if (!catalog.getOverview().equals("")) {
                    tvDescription.setText(catalog.getOverview());
                } else tvDescription.setText(getApplicationContext().getString(R.string.message_no_overview));
                Glide.with(this).load(Constant.POSTER_URL + catalog.getPosterPath()).into(ivPoster);
                getSupportActionBar().setTitle(catalog.getName());
            }
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
