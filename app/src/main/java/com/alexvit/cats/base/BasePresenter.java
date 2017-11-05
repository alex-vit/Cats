package com.alexvit.cats.base;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by Aleksandrs Vitjukovs on 11/4/2017.
 */

public class BasePresenter<View extends BaseView> {

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    protected View view;

    public void attach(View view) {
        this.view = view;
    }

    public void stop() {
        compositeDisposable.clear();
    }

    protected <T> void subscribe(Observable<T> observable, Consumer<? super T> onNext) {
        subscribe(observable, onNext, view::onError, () -> {
        });
    }

    protected <T> void subscribe(Observable<T> observable,
                                 Consumer<? super T> onNext,
                                 Consumer<? super Throwable> onError,
                                 Action onComplete) {
        Disposable disposable = observable.subscribe(onNext, onError, onComplete);
        compositeDisposable.add(disposable);
    }

}
