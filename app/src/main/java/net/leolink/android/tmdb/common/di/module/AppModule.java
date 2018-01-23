package net.leolink.android.tmdb.common.di.module;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import net.leolink.android.tmdb.common.di.scope.AppScoped;
import net.leolink.android.tmdb.common.io.network.api.NetworkModule;

import dagger.Module;
import dagger.Provides;

/**
 * @author Leo
 */
@Module(includes = {
        NetworkModule.class
})
public class AppModule {
    @AppScoped
    @Provides
    Context provideContext(Application application) {
        return application.getApplicationContext();
    }

    @AppScoped
    @Provides
    Resources provideResources(Context context) {
        return context.getResources();
    }
}
