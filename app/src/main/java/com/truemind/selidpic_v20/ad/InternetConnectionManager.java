package com.truemind.selidpic_v20.ad;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


/**
 * Created by Hyunseok on 2017-08-30.
 *
 * InternetConnectionManager
 *  네트워크 가능여부를 체크하는 클래스
 */
public class InternetConnectionManager {
    public static boolean isNetworkAvailable(Context context){
        ConnectivityManager connectivityManager =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}