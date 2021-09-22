package com.firas.Assessment1Valet.UI.Activities.Device;

import android.content.Context;

import com.firas.Assessment1Valet.Application.MyHomeApp;
import com.firas.Assessment1Valet.ModelLayer.Models.Device;


public class DevicePresenterImpl implements DevicePresenter {

    private View mView;

    public DevicePresenterImpl(Context context) {
        ((MyHomeApp) context).getAppComponent().inject(this);
    }

    @Override
    public void setView(View view) {
        this.mView = view;
    }

    @Override
    public void getDevice(Device device) {

        mView.showDevice(device);
    }


}
