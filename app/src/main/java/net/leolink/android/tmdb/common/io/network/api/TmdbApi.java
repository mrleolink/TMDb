package net.leolink.android.tmdb.common.io.network.api;

import net.leolink.android.tmdb.common.io.network.api.model.DiscoverResponse;
import net.leolink.android.tmdb.common.io.network.api.param.DiscoverParams;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * All APIs should be declared here
 * @author Leo
 */
public interface TmdbApi {
    @GET("discover/movie")
    Observable<DiscoverResponse> listRepos(@QueryMap DiscoverParams params);
}
