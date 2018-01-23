package net.leolink.android.tmdb.movielist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.leolink.android.tmdb.R;
import net.leolink.android.tmdb.common.config.NetworkConfig;
import net.leolink.android.tmdb.common.io.network.api.model.DiscoverMovie;
import net.leolink.android.tmdb.common.io.network.imageloader.GlideApp;
import net.leolink.android.tmdb.movielist.viewmodel.MovieListViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

/**
 * @author Leo
 */
class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieViewHolder> {
    private MovieListViewModel mMovieListViewModel;
    private List<DiscoverMovie> mData;
    private OnMovieClickListener mCallback;

    MovieListAdapter(@NonNull MovieListViewModel movieListViewModel, @NonNull OnMovieClickListener callback) {
        this.mMovieListViewModel = movieListViewModel;
        this.mCallback = callback;
        setData(null);
    }

    public void setData(@Nullable List<DiscoverMovie> newData) {
        mData = newData == null ? new ArrayList<>() : newData;
        notifyDataSetChanged();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MovieViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_movie, parent, false));
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        DiscoverMovie movie = mData.get(position);
        // title
        holder.titleView.setText(String.format("%s (%s)", movie.getTitle(), movie.getReleaseDate()));
        // image
        String posterUrl = String.format(NetworkConfig.BASE_IMAGE_URL, movie.getPosterPath());
        GlideApp.with(holder.itemView)
                .load(posterUrl)
                .centerCrop()
                .error(R.drawable.ic_launcher_background)
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.posterView);
        // click listener
        holder.itemView.setOnClickListener(view -> {
            if (mCallback != null) mCallback.onMovieClick(movie);
        });
        // next page
        if (position > mData.size() - mMovieListViewModel.getLastPageItemCount() / 2) mMovieListViewModel.nextPage();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    /** View holder **/
    static class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView titleView;
        ImageView posterView;
        MovieViewHolder(View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.title);
            posterView = itemView.findViewById(R.id.poster);
        }
    }

    /** Item click callback **/
    public interface OnMovieClickListener {
        void onMovieClick(@NonNull DiscoverMovie movie);
    }
}
