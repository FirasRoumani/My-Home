package com.firas.Assessment1Valet.Dagger.Module;

import android.content.Context;

import com.firas.Assessment1Valet.UI.Activities.Device.DevicePresenter;
import com.firas.Assessment1Valet.UI.Activities.Device.DevicePresenterImpl;
import com.firas.Assessment1Valet.UI.Activities.Devices.DeviceListPresenter;
import com.firas.Assessment1Valet.UI.Activities.Devices.DeviceListPresenterImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class UiModule {
    @Provides
    @Singleton
    public DeviceListPresenter provideDevicesPresenter(Context context) {
        return new DeviceListPresenterImpl(context);
    }

    @Provides
    @Singleton
    public DevicePresenter provideDeviceDetailPresenter(Context context) {
        return new DevicePresenterImpl(context);
    }
}
