package com.alexvit.cats.data.source.remote;

import android.support.annotation.IntDef;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by alexander.vitjukov on 16.11.2017.
 */

public final class Contract {

    private Contract() {
    }

    @StringDef({SIZE_SMALL, SIZE_MEDIUM, SIZE_FULL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Size {
    }

    public static final String SIZE_SMALL = "small";
    public static final String SIZE_MEDIUM = "med";
    public static final String SIZE_FULL = "full";

    @IntDef({SCORE_LOVE, SCORE_HATE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Score {
    }

    public static final int SCORE_LOVE = 10;
    public static final int SCORE_HATE = 1;


}
