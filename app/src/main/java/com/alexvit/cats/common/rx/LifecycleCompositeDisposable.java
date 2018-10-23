package com.alexvit.cats.common.rx;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import static com.alexvit.cats.common.rx.LifecycleCompositeDisposable.UnsubscribeOn.PAUSE;
import static com.alexvit.cats.common.rx.LifecycleCompositeDisposable.UnsubscribeOn.STOP;

public class LifecycleCompositeDisposable implements LifecycleObserver {

    private final CompositeDisposable subs = new CompositeDisposable();
    private final UnsubscribeOn unsubscribeOn;

    public LifecycleCompositeDisposable(Lifecycle lifecycle, UnsubscribeOn unsubscribeOn) {
        lifecycle.addObserver(this);
        this.unsubscribeOn = unsubscribeOn;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        if (unsubscribeOn == PAUSE) {
            subs.clear();
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        if (unsubscribeOn == STOP) {
            subs.clear();
        }
    }

    public boolean add(Disposable d) {
        return subs.add(d);
    }

    public boolean addAll(Disposable... ds) {
        return subs.addAll(ds);
    }

    public enum UnsubscribeOn {
        PAUSE, STOP
    }
}
