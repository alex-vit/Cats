package com.alexvit.cats;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Pair;

import androidx.annotation.NonNull;

/**
 * Created by Aleksandrs Vitjukovs on 11/4/2017.
 */

@SuppressWarnings("WeakerAccess")
public final class Screen {

    private Screen() {
    }

    public static int columnCount(@NonNull Context context, int desiredColumnWidthDp) {
        Pair<Integer, Integer> dimensionsDp = screenDimensionsDp(context);
        return Math.round((float) dimensionsDp.first / desiredColumnWidthDp);
    }

    @NonNull
    private static Pair<Integer, Integer> screenDimensionsDp(@NonNull Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float width = displayMetrics.widthPixels / displayMetrics.density;
        float height = displayMetrics.heightPixels / displayMetrics.density;
        return new Pair<>(Math.round(width), Math.round(height));
    }
}
