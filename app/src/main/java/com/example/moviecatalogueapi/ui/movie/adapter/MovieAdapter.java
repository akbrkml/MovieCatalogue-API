package com.example.moviecatalogueapi.ui.movie.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviecatalogueapi.model.Movie;
import com.example.moviecatalogueapi.ui.movie.view.MovieClickListener;
import com.example.moviecatalogueapi.R;
import com.example.moviecatalogueapi.utils.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private final Context context;

    private List<Movie> movies;
    private final MovieClickListener listener;

    public MovieAdapter(Context context, MovieClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.movies = new ArrayList<>();
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(movies.get(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivPoster)
        ImageView ivPoster;
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvDate)
        TextView tvDate;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(final Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvDate.setText(movie.getReleaseDate());
            Glide.with(context).load(Constant.POSTER_URL + movie.getPosterPath()).into(ivPoster);
            itemView.setOnClickListener(v -> listener.onItemClick(movie));
        }
    }
}
