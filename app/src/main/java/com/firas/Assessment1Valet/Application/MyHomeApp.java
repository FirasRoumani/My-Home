package com.firas.Assessment1Valet.Application;

import android.app.Application;

import com.firas.Assessment1Valet.Dagger.Component.AppComponent;
import com.firas.Assessment1Valet.Dagger.Component.DaggerAppComponent;
import com.firas.Assessment1Valet.Dagger.Module.AppModule;

public class MyHomeApp extends Application {
    protected AppComponent appComponent;

    public AppComponent getAppComponent() {
        return appComponent;
    }

    protected AppComponent initDagger(MyHomeApp application) {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(application))
                .build();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = initDagger(this);
    }
}