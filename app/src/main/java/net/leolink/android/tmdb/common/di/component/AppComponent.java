package net.leolink.android.tmdb.common.di.component;

import android.app.Application;

import net.leolink.android.tmdb.TmdbApp;
import net.leolink.android.tmdb.common.di.module.ActivityBindingModule;
import net.leolink.android.tmdb.common.di.module.AppModule;
import net.leolink.android.tmdb.common.di.scope.AppScoped;
import net.leolink.android.tmdb.common.io.network.api.NetworkModule;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * @author Leo
 */
@AppScoped
@Component(modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class,
        ActivityBindingModule.class
})
public interface AppComponent extends AndroidInjector<TmdbApp> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        Builder networkModule(NetworkModule networkModule);

        AppComponent build();
    }

    void inject(TmdbApp app);
}
