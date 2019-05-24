package com.example.moviecatalogueapi.ui.movie;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.moviecatalogueapi.GlideApp;
import com.example.moviecatalogueapi.R;
import com.example.moviecatalogueapi.model.Movie;
import com.example.moviecatalogueapi.ui.movie.presenter.DetailMoviePresenter;
import com.example.moviecatalogueapi.ui.movie.view.DetailMovieView;
import com.example.moviecatalogueapi.utils.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailMovieActivity extends AppCompatActivity implements DetailMovieView {

    @BindView(R.id.ivPoster)
    ImageView ivPoster;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvDate)
    TextView tvDate;
    @BindView(R.id.tvDescription)
    TextView tvDescription;

    private DetailMoviePresenter presenter;
    private boolean isFavorite = false;
    private String title, overview, releaseDate, posterPath;
    private int id;
    private Movie movie;
    private Menu menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        presenter = new DetailMoviePresenter(this, this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Movie catalog = getIntent().getParcelableExtra(Constant.EXTRA_CATALOG);
        if (catalog != null) {
            setData(catalog.getId(), catalog.getTitle(), catalog.getOverview(), catalog.getReleaseDate(), catalog.getPosterPath());
            movie = setMovie();
            tvTitle.setText(catalog.getTitle());
            tvDate.setText(catalog.getReleaseDate());
            if (!catalog.getOverview().equals("")) {
                tvDescription.setText(catalog.getOverview());
            } else
                tvDescription.setText(getApplicationContext().getString(R.string.message_no_overview));
            GlideApp.with(this).load(Constant.POSTER_URL + catalog.getPosterPath()).into(ivPoster);
            getSupportActionBar().setTitle(catalog.getTitle());
        }

    }

    private void setData(int id, String title, String overview, String releaseDate, String posterPath) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.posterPath = posterPath;
    }

    private Movie setMovie() {
        Movie movie = new Movie();
        movie.setId(id);
        movie.setTitle(title);
        movie.setOverview(overview);
        movie.setReleaseDate(releaseDate);
        movie.setPosterPath(posterPath);
        return movie;
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

        presenter.getMovie(id);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_fav) {
            if (isFavorite) presenter.deleteMovie(id); else presenter.insertMovie(movie);

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
    public void onGetDataMovieStatus(Boolean status) {
        isFavorite = status;
        setFavorite();
    }

    @Override
    public void onMessage(String message) {
        runOnUiThread(() -> Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show());
    }
}
