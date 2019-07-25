package com.example.moviecatalogueapi.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.moviecatalogueapi.R;
import com.example.moviecatalogueapi.data.source.local.entity.TvShowEntity;
import com.example.moviecatalogueapi.ui.callback.CallbackClickListener;
import com.example.moviecatalogueapi.utils.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TVPagedAdapter extends PagedListAdapter<TvShowEntity, TVPagedAdapter.TVViewHolder> {

    private final Context context;
    private List<TvShowEntity> tvShows;
    private final CallbackClickListener listener;

    private static DiffUtil.ItemCallback<TvShowEntity> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<TvShowEntity>() {
                @Override
                public boolean areItemsTheSame(@NonNull TvShowEntity oldItem, @NonNull TvShowEntity newItem) {
                    return oldItem.getId().equals(newItem.getId());
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(@NonNull TvShowEntity oldItem, @NonNull TvShowEntity newItem) {
                    return oldItem.equals(newItem);
                }
            };

    public TVPagedAdapter(Context context, CallbackClickListener listener) {
        super(DIFF_CALLBACK);
        this.context = context;
        this.listener = listener;
        this.tvShows = new ArrayList<>();
    }

    public void setTvShows(List<TvShowEntity> tvShows) {
        this.tvShows = tvShows;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TVPagedAdapter.TVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new TVViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TVPagedAdapter.TVViewHolder holder, int position) {
        holder.bind(tvShows.get(position));
    }

    @Override
    public int getItemCount() {
        return tvShows.size();
    }

    class TVViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivPoster)
        ImageView ivPoster;

        TVViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(final TvShowEntity tvShow) {
            Glide.with(context)
                    .load(Constant.POSTER_URL + tvShow.getPosterPath())
                    .apply(
                            new RequestOptions()
                                    .placeholder(R.drawable.placeholder)
                                    .error(R.drawable.placeholder)
                    ).into(ivPoster);
            itemView.setOnClickListener(v -> listener.onItemClick(tvShow.getId()));
        }
    }
}
