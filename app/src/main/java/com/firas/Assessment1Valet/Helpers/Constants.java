package com.firas.Assessment1Valet.Helpers;

import android.net.Uri;

public class Constants {

    public static final String MOCK_API_URL = "https://extendsclass.com/mock/rest/7663b0b661332852e5e5a457deeb01de";
    public static final String API_PATH_Devices = "GetDevices";
    public static final Uri BASE_URI = Uri.parse(MOCK_API_URL).buildUpon().build();
    public static final int ACTIVE = 1;
    public static final int INACTIVE = 0;


}
