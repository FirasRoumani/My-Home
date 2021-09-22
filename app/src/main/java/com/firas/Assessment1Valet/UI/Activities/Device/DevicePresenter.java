package com.firas.Assessment1Valet.UI.Activities.Device;

import com.firas.Assessment1Valet.ModelLayer.Models.Device;

public interface DevicePresenter {
    void setView(View view);

    void getDevice(Device device);

    interface View {

        void showDevice(Device device);
    }
}
