package com.example.nathan.dalvik_debug_bug;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

public class BugActivity extends Activity {
    private static Context appContext;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appContext = getApplicationContext();
        Log.e(getClass().getSimpleName(), "" + getScreenDensity()); // run past this line
        Log.e(getClass().getSimpleName(), "" + getScreenDensity()); // breakpoint here, step in
    }

    private static Integer cachedScreenDensity;
    public static int getScreenDensity() {
        if (cachedScreenDensity != null) return cachedScreenDensity; // single step a few times, then crash
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) getAppContext().getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        if (metrics.densityDpi != 0) {
            return cachedScreenDensity = metrics.densityDpi;
        }

        // XXX no idea what's going on with the zero test above
        throwDebugException();
        return 240;
    }

    private static void throwDebugException() {
        // empty stub
    }

    private static Context getAppContext() {
        return appContext;
    }
}
