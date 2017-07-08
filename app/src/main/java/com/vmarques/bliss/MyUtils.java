package com.vmarques.bliss;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class MyUtils {


    // Check if online
    public static boolean isOnline(Context context) {

        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }

}
