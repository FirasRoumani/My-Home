package com.firas.Assessment1Valet.UI.Activities.Devices;

import com.firas.Assessment1Valet.ModelLayer.Models.Device;

import java.util.List;

public interface DeviceListPresenter {
    void setView(View view);

    void getDevices();

    interface View {
        void showLoading();

        void hideLoading();

        void showError(Exception exception);

        void showDevices(List<Device> Devices);
    }
}
