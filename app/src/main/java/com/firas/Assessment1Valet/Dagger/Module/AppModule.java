package com.firas.Assessment1Valet.Dagger.Module;


import android.app.Application;
import android.app.SearchManager;
import android.content.Context;
import android.net.ConnectivityManager;

import com.firas.Assessment1Valet.Helpers.AppPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private final Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return application;
    }


    @Provides
    @Singleton
    public AppPreferences provideAppPreferences(Context context) {
        return new AppPreferences(context);
    }


    @Provides
    @Singleton
    public SearchManager provideSearchManager(Context context) {
        return (SearchManager) context.getSystemService(Context.SEARCH_SERVICE);
    }

    @Provides
    @Singleton
    public ConnectivityManager provideConnectivityManager(Context context) {
        return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }
}
