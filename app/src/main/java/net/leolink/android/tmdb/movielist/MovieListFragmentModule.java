package net.leolink.android.tmdb.movielist;

import net.leolink.android.tmdb.common.di.scope.FragmentScoped;
import net.leolink.android.tmdb.movielist.service.MovieListService;
import net.leolink.android.tmdb.movielist.service.MovieListServiceNetwork;

import dagger.Binds;
import dagger.Module;

/**
 * @author Leo
 */
@Module
public abstract class MovieListFragmentModule {
    @FragmentScoped
    @Binds
    abstract MovieListService provideMovieListService(MovieListServiceNetwork network);
}
