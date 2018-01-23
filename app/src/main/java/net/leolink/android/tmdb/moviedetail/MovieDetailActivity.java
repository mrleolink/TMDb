package net.leolink.android.tmdb.moviedetail;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import net.leolink.android.tmdb.R;
import net.leolink.android.tmdb.common.base.BaseActivity;
import net.leolink.android.tmdb.common.io.network.api.model.DiscoverMovie;
import net.leolink.android.tmdb.databinding.ActivityMovieDetailBinding;

import io.reactivex.annotations.NonNull;

public class MovieDetailActivity extends BaseActivity {
    private static final String KEY_MOVIE = "movie";

    public static Intent newIntent(Context context, @NonNull DiscoverMovie movie) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(KEY_MOVIE, movie);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DiscoverMovie movie = getIntent().getParcelableExtra(KEY_MOVIE);
        if (movie != null) bindData(movie);
    }

    private void bindData(@NonNull DiscoverMovie movie) {
        setTitle(movie.getTitle());
        ActivityMovieDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail);
        binding.setMovie(movie);
    }
}
