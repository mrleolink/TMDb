package net.leolink.android.tmdb.moviedetail.databinding;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import net.leolink.android.tmdb.R;
import net.leolink.android.tmdb.common.config.NetworkConfig;
import net.leolink.android.tmdb.common.io.network.imageloader.GlideApp;

public class MovieDetailBindingAdapter {
    @BindingAdapter("moviedetail:posterUrl")
    public static void loadPoster(ImageView view, String path) {
        GlideApp.with(view)
                .load(String.format(NetworkConfig.BASE_IMAGE_URL, path))
                .centerCrop()
                .error(R.drawable.ic_launcher_background)
                .placeholder(R.drawable.ic_launcher_background)
                .into(view);
    }
}