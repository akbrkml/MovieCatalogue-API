package com.example.moviecatalogueapi.ui.tv;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.moviecatalogueapi.GlideApp;
import com.example.moviecatalogueapi.R;
import com.example.moviecatalogueapi.model.TvShow;
import com.example.moviecatalogueapi.ui.tv.presenter.DetailTvPresenter;
import com.example.moviecatalogueapi.ui.tv.view.DetailTvView;
import com.example.moviecatalogueapi.utils.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailTvActivity extends AppCompatActivity implements DetailTvView {

    @BindView(R.id.ivPoster)
    ImageView ivPoster;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvDate)
    TextView tvDate;
    @BindView(R.id.tvDescription)
    TextView tvDescription;

    private DetailTvPresenter presenter;

    private int id;
    private String name, overview, firstAirDate, posterPath;
    private TvShow tv;
    private boolean isFavorite;
    private Menu menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv);
        ButterKnife.bind(this);

        presenter = new DetailTvPresenter(this, this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        TvShow catalog = getIntent().getParcelableExtra(Constant.EXTRA_CATALOG);
        if (catalog != null) {
            setData(catalog.getId(), catalog.getName(), catalog.getOverview(), catalog.getFirstAirDate(), catalog.getPosterPath());
            tv = setTv();
            tvTitle.setText(catalog.getName());
            tvDate.setText(catalog.getFirstAirDate());
            if (!catalog.getOverview().equals("")) {
                tvDescription.setText(catalog.getOverview());
            } else
                tvDescription.setText(getApplicationContext().getString(R.string.message_no_overview));
            GlideApp.with(this).load(Constant.POSTER_URL + catalog.getPosterPath()).into(ivPoster);
            getSupportActionBar().setTitle(catalog.getName());
        }

    }

    private void setData(int id, String name, String overview, String firstAirDate, String posterPath) {
        this.id = id;
        this.name = name;
        this.overview = overview;
        this.firstAirDate = firstAirDate;
        this.posterPath = posterPath;
    }

    private TvShow setTv() {
        TvShow tv = new TvShow();
        tv.setId(id);
        tv.setName(name);
        tv.setOverview(overview);
        tv.setFirstAirDate(firstAirDate);
        tv.setPosterPath(posterPath);
        return tv;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.fav_menu, menu);
        menuItem = menu;

        presenter.getTv(id);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_fav) {
            if (isFavorite) presenter.deleteTv(id); else presenter.insertTv(tv);

            isFavorite = !isFavorite;
            setFavorite();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setFavorite() {
        if (isFavorite)
            menuItem.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_added_to_favorites));
        else
            menuItem.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_add_to_favorites));
    }

    @Override
    public void onGetDataTvStatus(Boolean status) {
        isFavorite = status;
        setFavorite();
    }

    @Override
    public void onMessage(String message) {
        runOnUiThread(() -> Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show());
    }
}
