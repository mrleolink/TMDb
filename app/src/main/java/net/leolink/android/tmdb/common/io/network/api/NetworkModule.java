package net.leolink.android.tmdb.common.io.network.api;

import android.content.Context;

import net.leolink.android.tmdb.BuildConfig;
import net.leolink.android.tmdb.common.config.NetworkConfig;
import net.leolink.android.tmdb.common.di.scope.AppScoped;

import java.io.File;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Leo
 */
@Module
public class NetworkModule {
    private String mBaseApiUrl;

    public NetworkModule(String baseApiUrl) {
        this.mBaseApiUrl = baseApiUrl;
    }

    /**
     * Only provide {@link retrofit2.Retrofit.Builder} here so we can customize converter and such for each service
     * later.
     */
    // non-singleton
    @Provides
    Retrofit.Builder provideRetrofitBuilder(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(mBaseApiUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient);
    }

    @AppScoped
    @Provides
    OkHttpClient provideOkHttpClient(Cache cache) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.cache(cache);

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logging);
        }

        return builder.build();
    }

    @AppScoped
    @Provides
    Cache provideOkHttpCache(Context context) {
        File cacheDir = new File(context.getCacheDir(), NetworkConfig.RETROFIT_CACHE_DIR_NAME);
        return new Cache(cacheDir, NetworkConfig.RETROFIT_CACHE_SIZE);
    }
}
