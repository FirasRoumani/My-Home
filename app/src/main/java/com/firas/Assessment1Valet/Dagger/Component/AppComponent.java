package com.firas.Assessment1Valet.Dagger.Component;


import com.firas.Assessment1Valet.Dagger.Module.AppModule;
import com.firas.Assessment1Valet.Dagger.Module.NetworkModule;
import com.firas.Assessment1Valet.Dagger.Module.UiModule;
import com.firas.Assessment1Valet.ModelLayer.Network.MyHomeApiImpl;
import com.firas.Assessment1Valet.UI.Activities.Device.DeviceDetails;
import com.firas.Assessment1Valet.UI.Activities.Device.DevicePresenterImpl;
import com.firas.Assessment1Valet.UI.Activities.Devices.DeviceListFragment;
import com.firas.Assessment1Valet.UI.Activities.Devices.DeviceListPresenterImpl;
import com.firas.Assessment1Valet.UI.Activities.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class, UiModule.class})
public interface AppComponent {
    void inject(MainActivity target);

    void inject(DeviceDetails target);

    void inject(DeviceListFragment target);

    void inject(MyHomeApiImpl target);

    void inject(DeviceListPresenterImpl target);

    void inject(DevicePresenterImpl target);
}
