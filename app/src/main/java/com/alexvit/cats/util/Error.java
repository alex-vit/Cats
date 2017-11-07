package com.alexvit.cats.util;

import android.content.Context;

import com.alexvit.cats.R;

import java.net.UnknownHostException;

/**
 * Created by alexander.vitjukov on 07.11.2017.
 */

public final class Error {

    private Error() {
    }

    public static String messageIdForThrowable(Context context, Throwable throwable) {
        if (throwable instanceof UnknownHostException) {
            return context.getString(R.string.error_offline);
        } else {
            return throwable.getMessage();
        }
    }

}
