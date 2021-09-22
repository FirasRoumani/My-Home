package com.firas.Assessment1Valet.ModelLayer.Network;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

public class CachingStringRequest extends StringRequest {
    private static final String CACHE_CONTROL = "Cache-Control";

    public CachingStringRequest(int method,
                                String url,
                                Response.Listener<String> listener,
                                Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {

        response.headers.remove(CACHE_CONTROL);

        return super.parseNetworkResponse(response);
    }
}
