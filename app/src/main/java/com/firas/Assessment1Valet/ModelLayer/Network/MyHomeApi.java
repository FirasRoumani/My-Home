package com.firas.Assessment1Valet.ModelLayer.Network;

import com.android.volley.Response;
import com.firas.Assessment1Valet.ModelLayer.Models.Device;

import org.json.JSONException;

import java.util.List;

public interface MyHomeApi {

    void GetDevices(Response.Listener<String> responseListener,
                    Response.ErrorListener errorListener);


    List<Device> parseDevicesResponse(String jsonString) throws JSONException;

}
