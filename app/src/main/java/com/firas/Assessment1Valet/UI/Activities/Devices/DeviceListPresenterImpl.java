package com.firas.Assessment1Valet.UI.Activities.Devices;

import android.content.Context;

import androidx.annotation.NonNull;

import com.android.volley.Response;
import com.firas.Assessment1Valet.Application.MyHomeApp;
import com.firas.Assessment1Valet.ModelLayer.Network.MyHomeApi;

import org.json.JSONException;

import javax.inject.Inject;

public class DeviceListPresenterImpl implements DeviceListPresenter {

    @Inject
    MyHomeApi mApi;
    private View mView;

    public DeviceListPresenterImpl(Context context) {
        ((MyHomeApp) context).getAppComponent().inject(this);
    }

    @Override
    public void setView(View view) {
        this.mView = view;
    }

    @Override
    public void getDevices() {
        mView.showLoading();
        mApi.GetDevices(getResponseListener(), getErrorListener());

    }

    @NonNull
    private Response.Listener<String> getResponseListener() {
        return response -> {
            mView.hideLoading();

            try {
                mView.showDevices(mApi.parseDevicesResponse(response));
            } catch (JSONException error) {
                mView.showError(error);
            }
        };
    }

    @NonNull
    private Response.ErrorListener getErrorListener() {
        return error -> {
            mView.hideLoading();
            mView.showError(error);
        };
    }
}
