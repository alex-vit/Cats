package com.alexvit.cats.common.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Pair;

/**
 * Created by Aleksandrs Vitjukovs on 11/4/2017.
 */

@SuppressWarnings("WeakerAccess")
public final class Screen {

    private Screen() {
    }

    public static int columnCount(Context context, int desiredColumnWidth) {
        Pair<Integer, Integer> dimensionsDp = screenDimensionsDp(context);
        int width = dimensionsDp.first;
        return Math.round(width / desiredColumnWidth);
    }

    private static Pair<Integer, Integer> screenDimensionsDp(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float width = displayMetrics.widthPixels / displayMetrics.density;
        float height = displayMetrics.heightPixels / displayMetrics.density;
        return new Pair<>(Math.round(width), Math.round(height));
    }
}
