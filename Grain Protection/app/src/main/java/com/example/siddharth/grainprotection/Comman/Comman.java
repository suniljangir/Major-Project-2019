package com.example.siddharth.grainprotection.Comman;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.siddharth.grainprotection.Model.User;

/**
 * Created by Siddharth on 14-12-2017.
 */

public class Comman {

    public static final String USER_KEY ="User";
    public static final String PWD_KEY ="Password";


    public static User currentUser;
    public static boolean isConnectedtoInternet(Context context)
    {
        ConnectivityManager connectivityManager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager!=null)
        {
            NetworkInfo[] info=connectivityManager.getAllNetworkInfo();
            if(info!=null)
            {
                for (int i=0;i<info.length;i++)
                {
                    if (info[i].getState()==NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
