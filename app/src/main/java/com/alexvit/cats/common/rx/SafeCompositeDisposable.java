package com.alexvit.cats.common.rx;

import com.google.errorprone.annotations.CanIgnoreReturnValue;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public interface SafeCompositeDisposable {

    CompositeDisposable subs = new CompositeDisposable();

    @CanIgnoreReturnValue
    default boolean add(Disposable d) {
        return subs.add(d);
    }

    @CanIgnoreReturnValue
    default boolean addAll(Disposable... ds) {
        return subs.addAll(ds);
    }
}
