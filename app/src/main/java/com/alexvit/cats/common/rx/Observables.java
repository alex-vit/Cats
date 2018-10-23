package com.alexvit.cats.common.rx;

import androidx.annotation.Nullable;
import io.reactivex.Observable;

public class Observables {

    private Observables() {}

    public static <T> Observable<T> fromNullable(@Nullable T item) {
        if (item != null) {
            return Observable.just(item);
        } else {
            return Observable.error(new NullPointerException("item was null"));
        }
    }
}
