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

    public static String getDateFromString(String s) {
        String[] split = s.split("T");
        String date = split[0];
        return date;

    }

    public static String getTimeFromString(String s) {

        String[] split = s.split("T");
        String time = split[1].split("\\.")[0];
        return time;
    }

}
