package net.leolink.android.tmdb;

import net.leolink.android.tmdb.common.config.NetworkConfig;
import net.leolink.android.tmdb.common.di.component.DaggerAppComponent;
import net.leolink.android.tmdb.common.io.network.api.NetworkModule;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

/**
 * @author Leo
 */
public class TmdbApp extends DaggerApplication {
    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder()
                .networkModule(new NetworkModule(NetworkConfig.BASE_API_URL))
                .application(this)
                .build();
    }
}
