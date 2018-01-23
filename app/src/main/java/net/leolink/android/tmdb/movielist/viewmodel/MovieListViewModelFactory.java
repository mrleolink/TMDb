package net.leolink.android.tmdb.movielist.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import javax.inject.Inject;

/**
 * @author Leo
 */
public class MovieListViewModelFactory implements ViewModelProvider.Factory {
    @Inject
    MovieListViewModel mMovieListViewModel;

    @Inject
    MovieListViewModelFactory(){}

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MovieListViewModel.class)) {
            return (T) mMovieListViewModel;
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
