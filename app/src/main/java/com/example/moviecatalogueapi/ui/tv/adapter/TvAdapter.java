package com.example.moviecatalogueapi.ui.tv.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviecatalogueapi.GlideApp;
import com.example.moviecatalogueapi.R;
import com.example.moviecatalogueapi.model.TvShow;
import com.example.moviecatalogueapi.ui.tv.view.TvClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TvAdapter extends RecyclerView.Adapter<TvAdapter.ViewHolder> {

    private final Context context;
    private List<TvShow> tvShows;
    private final TvClickListener listener;

    public TvAdapter(Context context, TvClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.tvShows = new ArrayList<>();
    }

    public void setTvShows(List<TvShow> tvShows) {
        this.tvShows = tvShows;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvAdapter.ViewHolder holder, int position) {
        holder.bind(tvShows.get(position));
    }

    @Override
    public int getItemCount() {
        return tvShows.size();
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

        void bind(final TvShow tvShow) {
            tvTitle.setText(tvShow.getName());
            tvDate.setText(tvShow.getFirstAirDate());
            GlideApp.with(context).load("https://image.tmdb.org/t/p/w500/"+tvShow.getPosterPath()).into(ivPoster);
            itemView.setOnClickListener(v -> listener.onItemClick(tvShow));
        }
    }
}
