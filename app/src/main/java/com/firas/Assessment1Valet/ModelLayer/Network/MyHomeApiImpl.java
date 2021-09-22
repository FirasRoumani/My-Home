package com.firas.Assessment1Valet.ModelLayer.Network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import com.android.volley.Cache;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.firas.Assessment1Valet.Application.MyHomeApp;
import com.firas.Assessment1Valet.Helpers.Constants;
import com.firas.Assessment1Valet.ModelLayer.Models.Device;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import javax.inject.Inject;

public class MyHomeApiImpl implements MyHomeApi {
    @Inject
    ConnectivityManager mConnectivityManager;
    @Inject
    RequestQueue requestQueue;


    public MyHomeApiImpl(Context context) {
        ((MyHomeApp) context).getAppComponent().inject(this);
    }


    @Override
    public void GetDevices(Response.Listener<String> responseListener, Response.ErrorListener errorListener) {
        Uri uri = buildDevicesUri();
        doQuery(responseListener, errorListener, uri);

    }


    @Override
    public List<Device> parseDevicesResponse(String jsonString) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonString);

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        List<Device> devices = gson.fromJson(jsonObject.getString("devices"), new TypeToken<List<Device>>() {
        }.getType());
        return devices;
    }


    private void doQuery(Response.Listener<String> responseListener,
                         Response.ErrorListener errorListener,
                         Uri uri) {
        if (isNetworkAvailable()) {
            doNetworkQuery(responseListener, errorListener, uri);
        } else {
            doCachedQuery(responseListener, errorListener, uri);
        }
    }

    private void doNetworkQuery(Response.Listener<String> responseListener,
                                Response.ErrorListener errorListener,
                                Uri uri) {
        requestQueue.add(new CachingStringRequest(
                Request.Method.GET,
                uri.toString(),
                responseListener,
                errorListener));
    }

    private void doCachedQuery(Response.Listener<String> responseListener,
                               Response.ErrorListener errorListener,
                               Uri uri) {
        if (!isResponseInCache(uri)) {
            errorListener.onErrorResponse(new NoConnectionError());
            return;
        }

        String cachedResponse = getResponseFromCache(uri);
        responseListener.onResponse(cachedResponse);
        errorListener.onErrorResponse(new NoConnectionError());
    }


    private boolean isNetworkAvailable() {
        NetworkInfo activeNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private boolean isResponseInCache(Uri uri) {
        Cache cache = requestQueue.getCache();
        return cache.get(uri.toString()) != null;
    }

    private String getResponseFromCache(Uri uri) {
        Cache cache = requestQueue.getCache();
        Cache.Entry entry = cache.get(uri.toString());
        return new String(entry.data);
    }


    private Uri buildDevicesUri() {
        return Constants.BASE_URI.buildUpon().appendPath(Constants.API_PATH_Devices).build();
    }


}

