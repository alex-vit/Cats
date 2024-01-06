package com.alexvit.cats;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * @noinspection unused
 */
public interface SafeSubscriber {

    @NonNull
    CompositeDisposable subs = new CompositeDisposable();

    default <T> void subscribe(Observable<T> observable, Consumer<? super T> onNext,
                               Consumer<? super Throwable> onError, Action onComplete) {
        Disposable sub = observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError, onComplete);
        subs.add(sub);
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

    void onError(Throwable throwable);
}
