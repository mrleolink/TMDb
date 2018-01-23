package net.leolink.android.tmdb.movielist.service;

import android.support.annotation.NonNull;

import net.leolink.android.tmdb.common.io.network.api.TmdbApi;
import net.leolink.android.tmdb.common.io.network.api.model.DiscoverResponse;
import net.leolink.android.tmdb.common.io.network.api.param.DiscoverParams;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * @author Leo
 */
public class MovieListServiceNetwork implements MovieListService {
    @Inject Retrofit.Builder mRetrofitBuilder;

    @Inject
    public MovieListServiceNetwork(){}

    @Override
    @NonNull
    public Observable<DiscoverResponse> getMovieList(int page) {
        TmdbApi api = mRetrofitBuilder.build().create(TmdbApi.class);
        return api.listRepos(new DiscoverParams(page))
                // add more custom rx logic here if necessary
                .subscribeOn(Schedulers.io());
    }
}
