package com.alexvit.cats.common.traits;

import com.alexvit.cats.common.rx.LifecycleCompositeDisposable;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public interface SafeSubscriber extends ErrorHandler {

    @NonNull
    LifecycleCompositeDisposable getSubs();

    default <T> void subscribe(Observable<T> observable, Consumer<? super T> onNext,
                               Consumer<? super Throwable> onError, Action onComplete) {
        Disposable sub = observable.subscribe(onNext, onError, onComplete);
        getSubs().add(sub);
    }

    default <T> void subscribe(Observable<T> observable, Consumer<? super T> onNext,
                               Consumer<? super Throwable> onError) {
        subscribe(observable, onNext, onError, () -> {});
    }

    default <T> void subscribe(Observable<T> observable, Consumer<? super T> onNext) {
        subscribe(observable, onNext, this::onError, () -> {});
    }

    default <T> void subscribe(Observable<T> observable) {
        subscribe(observable, __ -> {}, this::onError, () -> {});
    }

}
