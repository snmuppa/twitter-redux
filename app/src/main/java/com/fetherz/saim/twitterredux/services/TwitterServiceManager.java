package com.fetherz.saim.twitterredux.services;

import android.support.design.widget.Snackbar;
import android.view.View;

import com.fetherz.saim.twitterredux.utils.LogUtil;

import java.io.IOException;

/**
 * Created by sm032858 on 3/25/17.
 */

public class TwitterServiceManager {


    private static final String TAG = "TwitterServiceManager";

    private static final String NO_INTERNET_TEXT = "No internet.";

    public static boolean isOnline(View view){
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (InterruptedException | IOException e) {
            LogUtil.logE(TAG, e.getMessage(), e);
        }

        Snackbar.make(view, NO_INTERNET_TEXT, Snackbar.LENGTH_SHORT).setAction("Action", null).show();

        return false;
    }

    //
}
