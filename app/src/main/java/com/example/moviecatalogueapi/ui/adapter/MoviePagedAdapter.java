package com.example.moviecatalogueapi.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.moviecatalogueapi.R;
import com.example.moviecatalogueapi.data.source.local.entity.MovieEntity;
import com.example.moviecatalogueapi.data.source.remote.response.Movie;
import com.example.moviecatalogueapi.ui.callback.CallbackClickListener;
import com.example.moviecatalogueapi.utils.Constant;
import com.example.moviecatalogueapi.vo.Resource;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviePagedAdapter extends PagedListAdapter<Movie, RecyclerView.ViewHolder> {

    private static final int ITEM_MOVIE_VIEW_TYPE = 1;
    private static final int ITEM_LOADING_VIEW_TYPE = 0;

    private Resource resource = null;

    private final Context context;

    private final CallbackClickListener listener;

    private static DiffUtil.ItemCallback<Movie> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Movie>() {
                @Override
                public boolean areItemsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
                    return oldItem.getId().equals(newItem.getId());
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
                    return oldItem.equals(newItem);
                }
            };

    public MoviePagedAdapter(Context context, CallbackClickListener listener) {
        super(DIFF_CALLBACK);
        this.context = context;
        this.listener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        if (isLoadingData() && position == getItemCount() - 1) {
            return R.layout.item_network_state;
        } else {
            return R.layout.item_movie;
        }
    }

    private boolean isLoadingData() {
        return resource != null && resource.status != Resource.Status.SUCCESS;
    }

    public void setNetworkState(Resource resource) {
        Resource prevState = this.resource;
        boolean wasLoading = isLoadingData();
        this.resource = resource;
        boolean willLoad = isLoadingData();
        if (wasLoading != willLoad) {
            if (wasLoading) notifyItemRemoved(getItemCount());
            else notifyItemInserted(getItemCount());
        } else if (willLoad && !prevState.equals(resource)) {
            notifyItemChanged(getItemCount() - 1);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        switch (viewType) {
            case R.layout.item_movie:
                view = inflater.inflate(R.layout.item_movie, parent, false);
                return new MovieViewHolder(view);
            case R.layout.item_network_state:
                view = inflater.inflate(R.layout.item_network_state, parent, false);
                return new ProgressViewHolder(view);
            default:
                throw new IllegalArgumentException("unknown view type " + viewType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case R.layout.item_movie:
                ((MovieViewHolder) holder).bind(getItem(position));
                break;
            case R.layout.item_network_state:
                ((ProgressViewHolder) holder).bind(resource);
                break;
            default:
                throw new IllegalArgumentException("unknown view type");
        }
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + (isLoadingData() ? ITEM_MOVIE_VIEW_TYPE : ITEM_LOADING_VIEW_TYPE);
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivPoster)
        ImageView ivPoster;

        MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(final Movie movie) {
            Glide.with(context)
                    .load(Constant.POSTER_URL + movie.getPosterPath())
                    .apply(
                            new RequestOptions()
                                    .placeholder(R.drawable.placeholder)
                                    .error(R.drawable.placeholder)
                    ).into(ivPoster);
            itemView.setOnClickListener(v -> listener.onItemClick(movie.getId()));
        }
    }

    class ProgressViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.progressBar)
        ProgressBar progressBar;

        ProgressViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Resource resource) {
            if (resource.status == Resource.Status.LOADING) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.INVISIBLE);
            }
        }
    }


}
