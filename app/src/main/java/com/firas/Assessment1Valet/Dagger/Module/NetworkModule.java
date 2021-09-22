package com.firas.Assessment1Valet.Dagger.Module;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.firas.Assessment1Valet.ModelLayer.Network.MyHomeApi;
import com.firas.Assessment1Valet.ModelLayer.Network.MyHomeApiImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class NetworkModule {
    @Provides
    @Singleton
    public RequestQueue provideRequestQueue(Context context) {
        return Volley.newRequestQueue(context);
    }

    @Provides
    @Singleton
    public MyHomeApi provideApi(Context context) {
        return new MyHomeApiImpl(context);
    }
}
