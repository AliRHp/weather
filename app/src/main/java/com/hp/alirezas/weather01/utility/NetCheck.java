package com.hp.alirezas.weather01.utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by alireza s on 6/25/2016.
 */
public class NetCheck {
    public static boolean checkNetworkIsOn(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = manager.getActiveNetworkInfo();
        if (netInfo == null) {
            return false;
        } else {
            return true;
        }
    }
}
