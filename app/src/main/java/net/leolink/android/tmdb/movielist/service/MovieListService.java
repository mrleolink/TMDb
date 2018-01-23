package net.leolink.android.tmdb.movielist.service;

import android.support.annotation.NonNull;

import net.leolink.android.tmdb.common.io.network.api.model.DiscoverResponse;

import io.reactivex.Observable;

/**
 * @author Leo
 */
public interface MovieListService {
    /** Get latest movie list by release date **/
    @NonNull
    Observable<DiscoverResponse> getMovieList(int page);

    /** Get movie list that are released in a certain year, sorted by release date **/
    @NonNull
    Observable<DiscoverResponse> getMovieListByYear(int page, int year);
}
