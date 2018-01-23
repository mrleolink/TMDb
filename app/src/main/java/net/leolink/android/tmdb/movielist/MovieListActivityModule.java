package net.leolink.android.tmdb.movielist;

import net.leolink.android.tmdb.common.di.scope.FragmentScoped;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * @author Leo
 */
@Module
public abstract class MovieListActivityModule {
    @FragmentScoped
    @ContributesAndroidInjector(modules = MovieListFragmentModule.class)
    abstract MovieListFragment movieListFragment();
}
