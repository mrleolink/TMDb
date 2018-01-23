package net.leolink.android.tmdb.common;

import android.content.res.Resources;
import android.util.Log;
import android.util.TypedValue;

import net.leolink.android.tmdb.BuildConfig;

/**
 * For utility methods
 *
 * @author Leo
 */
public class Utils {
    public static final String LOG_TAG = "linhln";

    public static float dp2px(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }

    public static void loge(Object message) {
        if (BuildConfig.DEBUG) Log.e(LOG_TAG, String.valueOf(message));
    }
}
