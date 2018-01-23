package net.leolink.android.tmdb.common.di.module;

import net.leolink.android.tmdb.common.di.scope.ActivityScoped;
import net.leolink.android.tmdb.movielist.MovieListActivity;
import net.leolink.android.tmdb.movielist.MovieListActivityModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * @author Leo
 */
@Module
public abstract class ActivityBindingModule {
    @ActivityScoped
    @ContributesAndroidInjector(modules = MovieListActivityModule.class)
    abstract MovieListActivity movieListActivity();
}
